package com.jrobots.replay;

/**
 * BulletSnapshot represents a projectile at a specific tick.
 */
public record BulletSnapshot(
        int id,
        int ownerId,
        double x,
        double y
) {}