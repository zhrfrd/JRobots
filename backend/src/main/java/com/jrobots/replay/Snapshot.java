package com.jrobots.replay;

import java.util.List;

/**
 * Snapshot represents the world state at a given tick.
 * Note: For now it contains only robots. Later it will include bullets, explosions, etc.
 */
public record Snapshot(
        int tick,
        List<RobotSnapshot> robots,
        List<BulletSnapshot> bullets,
        List<MatchEvent> events
) {}