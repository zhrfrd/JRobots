package com.jrobots.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * MatchEngine runs a deterministic match simulation. It mutates directly {@link RobotState} through
 * {@link RobotController} actions.
 */
public class MatchEngine {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    // Max movement and turning we allow the physics system to apply per tick.
    // (Robot can request more; it becomes "pending" and is applied over time.)
    private static final double MAX_MOVE_PER_TICK = 4.0;
    private static final double MAX_TURN_PER_TICK = 6.0;

    /**
     * Very simple replay snapshot for now. Needs to be updated.
     */
    public record Snapshot(int tick, double r1x, double r1y, double r1energy, double r2x, double r2y, double r2energy) {}

    /**
     * Runs a match between two controllers.
     * @param controller1 Brain of robot 1.
     * @param controller2 Brain of robot 2.
     * @param maxTicks Max number of simulation ticks.
     * @return List of snapshots (replay).
     */
    public List<Snapshot> runMatch(RobotController controller1, RobotController controller2, int maxTicks) {
        RobotState r1 = new RobotState(1, 100, 300);
        RobotState r2 = new RobotState(2, 700, 300);
        RobotView r1View = new RobotView(r1);
        RobotView r2View = new RobotView(r2);
        RobotActions r1Actions = new RobotActions();
        RobotActions r2Actions = new RobotActions();
        List<Snapshot> replay = new ArrayList<>();

        for (int tick = 0; tick < maxTicks; tick ++) {
            if (r1.energy <= 0 || r2.energy <= 0) {
                break;
            }

            r1Actions.resetForTick();
            r2Actions.resetForTick();
            controller1.onTick(r1View, r1Actions);
            controller2.onTick(r2View, r2Actions);

            // Engine reads the requested actions and applies them as "pending" intent.
            // IMPORTANT: We apply requests to pendingMove/pendingTurn HERE, not in RobotActions.
            r1.pendingMove += r1Actions.getRequestedMove();
            r1.pendingTurn += r1Actions.getRequestedTurn();
            r2.pendingMove += r2Actions.getRequestedMove();
            r2.pendingTurn += r2Actions.getRequestedTurn();

            applyMovement(r1);
            applyMovement(r2);

            replay.add(new Snapshot(tick, r1.x, r1.y, r1.energy, r2.x, r2.y, r2.energy));
        }

        return replay;
    }

    /**
     * Apply movement and turning for 1 tick, using the robot's pending intent.
     * This function enforces maximum movement rates per tick
     * @param robotState The current robot state.
     */
    private void applyMovement(RobotState robotState) {
        // Turn: apply only up to MAX_TURN_PER_TICK each tick
        double turn = clamp(robotState.pendingTurn, -MAX_TURN_PER_TICK, MAX_TURN_PER_TICK);
        robotState.bodyAngleDeg += turn;
        robotState.pendingTurn -= turn;

        // Move: apply only up to MAX_MOVE_PER_TICK each tick
        double move = clamp(robotState.pendingMove, -MAX_MOVE_PER_TICK, MAX_MOVE_PER_TICK);

        // Convert angle to direction vector using cos/sin
        double rad = Math.toRadians(robotState.bodyAngleDeg);
        robotState.x += Math.cos(rad) * move;
        robotState.y += Math.sin(rad) * move;

        robotState.pendingMove -= move;

        // Keep robot inside the arena boundaries (simple clamp for now)
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
        return Math.max(min, Math.min(max, value));
    }
}