package com.jrobots.replay;

/**
 * Death event: the robot's life has reached 0 or below.
 * @param type Type of event: "DEATH".
 * @param robotId Id of the dead robot.
 */
public record DeathEvent(String type, int robotId) implements MatchEvent {
    public DeathEvent(int robotId) {
        this("DEATH", robotId);
    }
}