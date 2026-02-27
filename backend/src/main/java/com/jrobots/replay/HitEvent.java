package com.jrobots.replay;

/**
 * Hit event: a bullet hit a robot.
 */

/**
 * Hit event: a bullet hit a robot.
 * @param type Type of event: "HIT".
 * @param bulletId Id of the bullet.
 * @param ownerId Id of the robot that shot the bullet.
 * @param targetRobotId Id of the robot that has being hit.
 * @param damage Value of the damage inflicted.
 */
public record HitEvent(String type, int bulletId, int ownerId, int targetRobotId, double damage) implements MatchEvent {
    public HitEvent(int bulletId, int ownerId, int targetRobotId, double damage) {
        this("HIT", bulletId, ownerId, targetRobotId, damage);
    }
}