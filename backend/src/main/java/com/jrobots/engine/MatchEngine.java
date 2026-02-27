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

    /**
     * Manage the match between multiple robots through controllers. TODO: This method is messy. Needs to be cleaned.
     * @param controller1 Brain of robot 1.
     * @param controller2 Brain of robot 2.
     * @param maxTicks Max number of simulation ticks.
     * @return List of snapshots (replay).
     */
    public MatchResult runMatch(RobotController controller1, RobotController controller2, int maxTicks) {
        RobotState r1 = new RobotState(1, 100, 300);
        RobotState r2 = new RobotState(2, 700, 300);
        RobotView r1View = new RobotView(r1);
        RobotView r2View = new RobotView(r2);
        RobotActions r1Actions = new RobotActions();
        RobotActions r2Actions = new RobotActions();
        List<Snapshot> replay = new ArrayList<>();
        int executedTicks = 0;
        int winnerId = -1;
        List<BulletState> bullets = new ArrayList<>();
        int nextBulletId = 1;

        for (int tick = 0; tick < maxTicks; tick ++) {
            List<MatchEvent> events = new ArrayList<>();

            // Reset action requests
            r1Actions.resetForTick();
            r2Actions.resetForTick();

            // Controllers decide actions
            controller1.onTick(r1View, r1Actions);
            controller2.onTick(r2View, r2Actions);

            // --- Bullets spawn

            // Spawn bullet for robot 1 TODO: Temporary code. Refactor and make it more general
            if (r1Actions.isFireRequested() && r1.energy > FIRE_ENERGY_COST) {
                r1.energy -= FIRE_ENERGY_COST;

                double rad = Math.toRadians(r1.bodyAngleDeg);
                double bx = r1.x + Math.cos(rad) * ROBOT_RADIUS;
                double by = r1.y + Math.sin(rad) * ROBOT_RADIUS;
                double vx = Math.cos(rad) * BULLET_SPEED;
                double vy = Math.sin(rad) * BULLET_SPEED;
                int bulletId = nextBulletId ++;

                bullets.add(new BulletState(bulletId, r1.id, bx, by, vx, vy, BULLET_POWER));
                events.add(new FireEvent(r1.id, bulletId));
            }

            // Spawn bullet for robot 2 TODO: Temporary code. Refactor and make it more general
            if (r2Actions.isFireRequested() && r2.energy > FIRE_ENERGY_COST) {
                r2.energy -= FIRE_ENERGY_COST;

                double rad = Math.toRadians(r2.bodyAngleDeg);
                double bx = r2.x + Math.cos(rad) * ROBOT_RADIUS;
                double by = r2.y + Math.sin(rad) * ROBOT_RADIUS;
                double vx = Math.cos(rad) * BULLET_SPEED;
                double vy = Math.sin(rad) * BULLET_SPEED;
                int bulletId = nextBulletId++;

                bullets.add(new BulletState(bulletId, r2.id, bx, by, vx, vy, BULLET_POWER));
                events.add(new FireEvent(r2.id, bulletId));
            }

            // Engine reads the requested actions and applies them as "pending" intent.
            // IMPORTANT: We apply requests to pendingMove/pendingTurn here, not in RobotActions.
            r1.pendingMove += r1Actions.getRequestedMove();
            r1.pendingTurn += r1Actions.getRequestedTurn();
            r2.pendingMove += r2Actions.getRequestedMove();
            r2.pendingTurn += r2Actions.getRequestedTurn();

            // --- Robot movement
            applyMovement(r1);
            applyMovement(r2);

            // --- Bullet movement
            for (BulletState b : bullets) {
                if (!b.alive) {
                    continue;
                }

                b.x += b.vx;
                b.y += b.vy;

                // Collision with robot 1
                if (b.ownerId != r1.id) {
                    double dx = r1.x - b.x;
                    double dy = r1.y - b.y;

                    if (dx * dx + dy * dy <= ROBOT_RADIUS * ROBOT_RADIUS) {
                        r1.energy -= b.power;
                        b.alive = false;

                        events.add(new HitEvent(b.id, b.ownerId, r1.id, b.power));

                        continue; // Prevent double-hit
                    }
                }

                // Collision with robot 2
                if (b.ownerId != r2.id) {
                    double dx = r2.x - b.x;
                    double dy = r2.y - b.y;

                    if (dx * dx + dy * dy <= ROBOT_RADIUS * ROBOT_RADIUS) {
                        r2.energy -= b.power;
                        b.alive = false;

                        events.add(new HitEvent(b.id, b.ownerId, r2.id, b.power));

                        continue; // Prevent double-hit
                    }
                }

                // Remove bullet if outside arena
                if (b.x < 0 || b.x > WIDTH || b.y < 0 || b.y > HEIGHT) {
                    b.alive = false;
                }
            }

            // Remove dead bullets safely
            bullets.removeIf(b -> !b.alive);

            // --- Check winner after physics & collisions

            if (r1.energy <= 0 && r2.energy <= 0) {
                winnerId = -1; // draw
                executedTicks++;
                replay.add(createSnapshot(tick, r1, r2, bullets, events));

                break;
            }

            if (r1.energy <= 0) {
                winnerId = r2.id;
                executedTicks ++;
                replay.add(createSnapshot(tick, r1, r2, bullets, events));
                break;
            }

            if (r2.energy <= 0) {
                winnerId = r1.id;
                executedTicks ++;
                replay.add(createSnapshot(tick, r1, r2, bullets, events));
                break;
            }

            // --- Record snapshot
            replay.add(createSnapshot(tick, r1, r2, bullets, events));
            executedTicks++;
        }

        return new MatchResult(WIDTH, HEIGHT, executedTicks, winnerId, replay);
    }

    private Snapshot createSnapshot(int tick, RobotState r1, RobotState r2, List<BulletState> bullets, List<MatchEvent> events) {
        return new Snapshot(
                tick,
                List.of(
                        new RobotSnapshot(r1.id, r1.x, r1.y, r1.energy, r1.bodyAngleDeg),
                        new RobotSnapshot(r2.id, r2.x, r2.y, r2.energy, r2.bodyAngleDeg)
                ),
                bullets.stream()
                        .map(b -> new BulletSnapshot(b.id, b.ownerId, b.x, b.y))
                        .toList(),
                List.copyOf(events)
        );
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