package com.jrobots.engine;

/**
 * RobotController represents the "brain" of a robot.
 * It is called once per simulation tick.
 *
 * <ul>
 *     <li>Observes the world through RobotView.</li>
 *     <li>Requests actions through RobotActions.</li>
 * </ul>
 */
public interface RobotController {
    void onTick(RobotView view, RobotActions actions);
}