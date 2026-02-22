package com.jrobots.engine;

/**
 * RobotView is a READ-ONLY wrapper around RobotState.
 * Controllers receive this object to inspect their own robot state.
 *
 * <ul>
 *     <li>This class exposes only allowed information.</li>
 *     <li>It prevents controllers from modifying RobotState directly.</li>
 * </ul>
 */
public class RobotView {
    private final RobotState self;

    public RobotView(RobotState self) {
        this.self = self;
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