package com.jrobots.engine;

/**
 * RobotState represents the physical state of a robot inside the simulation.
 *
 * <p>
 *     <strong>Note: </strong> The fields are `protected` rather than `private` to increase performances by avoiding
 *     getter methods calls overhead. Also, RobotState is not exposed to controllers directly but instead via {@link RobotView}.
 * </p>
 */
public class RobotState {
    public final int id;
    protected double x;
    protected double y;
    /** Direction the robot body is facing in degrees. */
    protected double bodyAngleDeg;
    protected double energy = 100.0;
    /** How much distance the robot still needs to move. */
    protected double pendingMove;
    /** How many degrees the robot still needs to turn. */
    protected double pendingTurn;

    public RobotState(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.bodyAngleDeg = 0; // default facing right
    }
}