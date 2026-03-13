package com.jrobots.engine;

import com.jrobots.replay.*;

import java.util.ArrayList;
import java.util.List;

/**
 * MatchEngine runs a deterministic match simulation. It mutates directly {@link RobotState} through
 * {@link RobotController} actions.
 */
public class MatchEngine {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static final double MAX_MOVE_PER_TICK = 4.0;
    private static final double MAX_TURN_PER_TICK = 6.0;
    private static final double ROBOT_RADIUS = 20.0;
    private static final double BULLET_SPEED = MAX_MOVE_PER_TICK * 2;
    private static final double BULLET_POWER = 10.0;
    private static final double FIRE_ENERGY_COST = 5.0;
    private int nextBulletId = 1;

    /**
     * Manage the match between multiple robots through controllers. TODO: This method is messy. Needs to be cleaned.
     * @param controller1 Brain of robot 1.
     * @param controller2 Brain of robot 2.
     * @param maxTicks Max number of simulation ticks.
     * @return List of snapshots (replay).
     */
    public MatchResult runMatch(RobotController controller1, RobotController controller2, int maxTicks) {
        List<RobotInstance> robots = new ArrayList<>();

        robots.add(new RobotInstance(new RobotState(1, 100, 300), controller1));
        robots.add(new RobotInstance(new RobotState(2, 700, 300), controller2));

        List<BulletState> bullets = new ArrayList<>();
        List<Snapshot> replay = new ArrayList<>();
        int executedTicks = 0;
        int winnerId = -1;

        for (int tick = 0; tick < maxTicks; tick++) {
            List<MatchEvent> events = new ArrayList<>();
            List<RobotState> robotStates = robots
                    .stream()
                    .map(r -> r.state)
                    .toList();

            // Update robot perception
            for (RobotInstance robot : robots) {
                robot.view.updateArenaState(robotStates);
            }

            processControllers(robots);
            spawnBullets(robots, bullets, events);
            applyRobotActions(robots);

            for (RobotInstance robot : robots) {
                applyMovement(robot.state);
            }

            updateBullets(bullets, robots, events);
            List<RobotInstance> alive = robots.stream().filter(r -> r.state.energy > 0).toList();

            if (alive.size() <= 1) {
                for (RobotInstance robot : robots) {
                    if (robot.state.energy <= 0) {
                        events.add(new DeathEvent(robot.state.id));
                    }
                }

                if (alive.isEmpty()) {
                    winnerId = -1;
                    events.add(new EndEvent("DRAW", -1));
                } else {
                    winnerId = alive.get(0).state.id;
                    events.add(new EndEvent("WIN", winnerId));
                }

                replay.add(createSnapshot(tick, robots, bullets, events));
                executedTicks ++;
                break;
            }

            replay.add(createSnapshot(tick, robots, bullets, events));

            executedTicks ++;
        }

        return new MatchResult(WIDTH, HEIGHT, executedTicks, winnerId, replay);
    }

    private void processControllers(List<RobotInstance> robots) {
        for (RobotInstance robot : robots) {
            robot.actions.resetForTick();
            robot.controller.onTick(robot.view, robot.actions);
        }
    }

    // Engine reads the requested actions and applies them as "pending" intent.
    // IMPORTANT: We apply requests to pendingMove/pendingTurn here, not in RobotActions.
    private void applyRobotActions(List<RobotInstance> robots) {
        for (RobotInstance robot : robots) {
            RobotState r = robot.state;
            r.pendingMove += robot.actions.getRequestedMove();
            r.pendingTurn += robot.actions.getRequestedTurn();
        }
    }

    private void spawnBullets(List<RobotInstance> robots, List<BulletState> bullets, List<MatchEvent> events) {
        for (RobotInstance robot : robots) {
            RobotState r = robot.state;

            if (robot.actions.isFireRequested() && r.energy > FIRE_ENERGY_COST) {
                r.energy -= FIRE_ENERGY_COST;
                double rad = Math.toRadians(r.bodyAngleDeg);
                double bx = r.x + Math.cos(rad) * ROBOT_RADIUS;
                double by = r.y + Math.sin(rad) * ROBOT_RADIUS;
                double vx = Math.cos(rad) * BULLET_SPEED;
                double vy = Math.sin(rad) * BULLET_SPEED;
                int bulletId = nextBulletId++;

                bullets.add(new BulletState(bulletId, r.id, bx, by, vx, vy, BULLET_POWER));
                events.add(new FireEvent(r.id, bulletId));
            }
        }
    }

    private void updateBullets(List<BulletState> bullets, List<RobotInstance> robots, List<MatchEvent> events) {
        // --- Move bullets
        for (BulletState b : bullets) {
            if (!b.alive) {
                continue;
            }

            b.x += b.vx;
            b.y += b.vy;
        }

        // --- Detect collisions
        for (BulletState b : bullets) {
            if (!b.alive) {
                continue;
            }

            for (RobotInstance robot : robots) {
                RobotState r = robot.state;

                if (b.ownerId == r.id) {
                    continue;
                }

                double dx = r.x - b.x;
                double dy = r.y - b.y;

                if (dx * dx + dy * dy <= ROBOT_RADIUS * ROBOT_RADIUS) {
                    r.energy -= b.power;
                    b.alive = false;
                    events.add(new HitEvent(b.id, b.ownerId, r.id, b.power));

                    break;
                }
            }

            if (!b.alive) {
                continue;
            }

            if (b.x < 0 || b.x > WIDTH || b.y < 0 || b.y > HEIGHT) {
                b.alive = false;
            }
        }

        // --- Remove dead bullets
        bullets.removeIf(b -> !b.alive);
    }

    private Snapshot createSnapshot(int tick, List<RobotInstance> robots, List<BulletState> bullets, List<MatchEvent> events) {
        List<RobotSnapshot> robotSnapshots = robots
                .stream()
                .map(r -> new RobotSnapshot(r.state.id, r.state.x, r.state.y, r.state.energy, r.state.bodyAngleDeg))
                .toList();

        List<BulletSnapshot> bulletSnapshots = bullets
                .stream()
                .map(b -> new BulletSnapshot(b.id, b.ownerId, b.x, b.y))
                .toList();

        return new Snapshot(tick, robotSnapshots, bulletSnapshots, List.copyOf(events));
    }

    /**
     * Apply movement and turning for 1 tick, using the robot's pending intent.
     * This function enforces maximum movement rates per tick
     * @param robotState The current robot state.
     */
    private void applyMovement(RobotState robotState) {
        double turn = clamp(robotState.pendingTurn, -MAX_TURN_PER_TICK, MAX_TURN_PER_TICK);
        robotState.bodyAngleDeg += turn;
        robotState.pendingTurn -= turn;   // Avoid forever turn

        double move = clamp(robotState.pendingMove, -MAX_MOVE_PER_TICK, MAX_MOVE_PER_TICK);
        double rad = Math.toRadians(robotState.bodyAngleDeg);
        robotState.x += Math.cos(rad) * move;
        robotState.y += Math.sin(rad) * move;
        robotState.pendingMove -= move;   // Avoid forever move
        robotState.x = clamp(robotState.x, 0, WIDTH);
        robotState.y = clamp(robotState.y, 0, HEIGHT);
    }

    /**
     * Clamp a value between min and max to avoid going out of limits.
     * @param value Current value (i.e. movement, turn...).
     * @param min Minimum value.
     * @param max Maximum value.
     * @return The pending value or, if it exceeded the boundaries, the limit.
     */
    private double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }

        return value;
    }
}