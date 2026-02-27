package com.jrobots.replay;

/**
 * End event: the match is ended.
 * @param type Type of event: "END".
 * @param result Result of the end of the match: "WIN", "DRAW", "TIMEOUT".
 * @param winnerId Id of the winner robot.
 */
public record EndEvent(String type, String result, int winnerId) implements MatchEvent {
    public EndEvent(String result, int winnerId) {
        this("END", result, winnerId);
    }
}