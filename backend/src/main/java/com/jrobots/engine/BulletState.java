package com.jrobots.engine;

/**
 * BulletState represents a physical state of a projectile inside the simulation.
 *
 * <p>
 *     <strong>Note: </strong> The fields are `protected` rather than `private` to increase performances by avoiding
 *     getter methods calls overhead. Also, BulletState is not exposed to controllers.
 * </p>
 */
class BulletState {
    protected final int id;
    protected final int ownerId;
    protected final double power;
    protected double x;
    protected double y;
    /** X coordinate of the velocity vector (pixels per tick). */
    protected double vx;
    /** Y coordinate of the velocity vector (pixels per tick). */
    protected double vy;
    boolean alive = true;

    protected BulletState(int id, int ownerId, double x, double y, double vx, double vy, double power) {
        this.id = id;
        this.ownerId = ownerId;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.power = power;
    }
}