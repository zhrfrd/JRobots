package com.jrobots.engine;

/**
 * Vec2 represents a 2D vector. It's used for: Positions(x, y), Directions(dx, dy), Velocities(vx, vy).
 * @param x Coordinate in the x-axis.
 * @param y Coordinate in the y-axis.
 */
public record Vec2(double x, double y) {
    /**
     * Add the current vector to another vector.
     * @return The new vector originated from the sum of the two vectors.
     */
    public Vec2 add(Vec2 other) {
        return new Vec2(x + other.x, y + other.y);
    }

    /**
     * Multiply the current vector by a scale for scaling velocity, scaling direction by speed.
     * @return The new vector originated by the product of the current vector and the scalar.
     */
    public Vec2 mul(double scale) {
        return new Vec2(x * scale, y * scale);
    }
}