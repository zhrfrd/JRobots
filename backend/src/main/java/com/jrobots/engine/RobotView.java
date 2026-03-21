package com.jrobots.engine;

import java.util.List;

/**
 * RobotView is a READ-ONLY wrapper around RobotState. It's a "window" into the state.
 * Controllers receive this object to inspect their own robot state.
 *
 * <ul>
 *     <li>This class exposes only allowed information.</li>
 *     <li>It prevents controllers from modifying RobotState directly.</li>
 * </ul>
 */
public class RobotView {
    private final RobotState self;
    private List<RobotState> robots;

    public RobotView(RobotState self) {
        this.self = self;
    }

    /**
     * Scan battlefield (see enemies, detect direction, measure distance).
     * @param angleDeg Double value of the scan view angle.
     * @return The distance of the closest target robot.
     */
    public double scan(double angleDeg) {
        if (robots == null) {
            return -1;
        }

        double rad = Math.toRadians(angleDeg);
        double dx = Math.cos(rad);
        double dy = Math.sin(rad);
        double bestDistance = Double.MAX_VALUE;

        for (RobotState robot : robots) {
            if (robot.id == self.id) {
                continue;
            }

            double rx = robot.x - self.x;
            double ry = robot.y - self.y;
            double dist = Math.sqrt(rx * rx + ry * ry);

            if (dist == 0) {
                continue;
            }

            double dot = (rx * dx + ry * dy) / dist;

            // Clamp to avoid floating point errors (i.e.: 1.0000001 NaN)
            if (dot > 1) {
                dot = 1;
            } else if (dot < -1) {
                dot = -1;
            }

            double angle = Math.acos(dot);

            if (angle < Math.toRadians(5)) {
                if (dist < bestDistance) {
                    bestDistance = dist;
                }
            }
        }

        return bestDistance == Double.MAX_VALUE ? -1 : bestDistance;
    }

    protected void updateArenaState(List<RobotState> robots) {
        this.robots = robots;
    }

    public double getX() {
        return self.x;
    }

    public double getY() {
        return self.y;
    }

    public double getEnergy() {
        return self.energy;
    }

    public double getBodyAngleDeg() {
        return self.bodyAngleDeg;
    }

    public int getArenaWidth() {
        return MatchEngine.WIDTH;
    }

    public int getArenaHeight() {
        return MatchEngine.HEIGHT;
    }
}