package com.jrobots.replay;

/**
 * RobotSnapshot is a read-only record containing the minimal info needed to render a robot.
 */
public record RobotSnapshot(
        int id,
        double x,
        double y,
        double energy,
        double bodyAngleDeg
) {}