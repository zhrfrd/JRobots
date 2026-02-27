package com.jrobots.replay;

/**
 * Fire event: a robot fired a bullet.
 */

/**
 * Fire event: a robot fired a bullet.
 * @param type Type of event: "FIRE".
 * @param robotId Id of the robot that shot the bullet.
 * @param bulletId Id of the bullet.
 */
public record FireEvent(String type, int robotId,int bulletId) implements MatchEvent {
    public FireEvent(int robotId, int bulletId) {
        this("FIRE", robotId, bulletId);
    }
}