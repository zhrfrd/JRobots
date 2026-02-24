package com.jrobots.engine;

/**
 * RobotActions is the only way a controller can influence the robot.
 * It does NOT directly change {@link RobotState} but it only stores requested actions for the current tick.
 *
 * <p>
 *     Then {@link MatchEngine}:
 *     <ol>
 *         <li>Reads these requests.</li>
 *         <li>Validate these rules.</li>
 *         <li>Applies them to {@link RobotState}</li>
 *     </ol>
 * </p>
 */
public class RobotActions {
    private double requestedMove;
    private double requestedTurn;

    /**
     * Update the requested movement and save it for later to be used by {@link MatchEngine}. This does NOT move immediately.
     * @param distance Distance to update the movement.
     */
    public void move(double distance) {
        requestedMove += distance; // accumulate requests within the tick
    }

    /**
     * Update the requested turning and save it for later to be used by {@link MatchEngine}. This does NOT turn immediately.
     * @param degrees Degrees to update the turn.
     */
    public void turn(double degrees) {
        requestedTurn += degrees; // accumulate requests within the tick
    }

    /**
     * Called by {@link MatchEngine} at the start of every tick to clear previous requests.
     */
    protected void resetForTick() {
        requestedMove = 0;
        requestedTurn = 0;
    }

    protected double getRequestedMove() {
        return requestedMove;
    }

    protected double getRequestedTurn() {
        return requestedTurn;
    }
}