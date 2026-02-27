package com.jrobots.replay;

/**
 * Base type for replay events (things that happened during a tick).
 * The interface is sealed so only known event types can exist: {@link FireEvent}, {@link HitEvent}, {@link DeathEvent}, {@link EndEvent}.
 */
public sealed interface MatchEvent permits FireEvent, HitEvent, DeathEvent, EndEvent {
    String type();
}