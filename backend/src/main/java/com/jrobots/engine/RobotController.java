package com.jrobots.engine;

/**
 * RobotController represents the "brain" of a robot.
 * It is called once per simulation tick.
 *
 * <ul>
 *     <li>Called once per tick.</li>
 *     <li>Observes the world through {@link RobotView}.</li>
 *     <li>Requests actions through {@link RobotActions}. (Doesn't apply actions).</li>
 * </ul>
 */
public interface RobotController {
    /**
     * Called by MatchEngine once per tick.
     * @param view Read-only information the robot is allowed to know.
     * @param actions The only way the robot can request actions.
     */
    void onTick(RobotView view, RobotActions actions);
}