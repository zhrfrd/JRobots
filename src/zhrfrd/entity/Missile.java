package zhrfrd.entity;

import java.util.Random;

import zhrfrd.graphics.Screen;
import zhrfrd.graphics.Sprite;

public class Missile extends Entity{
	protected int xOrigin, yOrigin;
	protected double x, y;
	protected double angle;
	protected double dirX, dirY;
	protected double distance;
	public double speed, range, damage;
	protected Sprite sprite;
	protected final Random random = new Random ();
	
	// Constructor
	public Missile(int x, int y, double angle) {
		this.xOrigin = x;
		this.yOrigin = y;
		this.angle = angle;
		this.x = x;
		this.y = y;
		range = 200;
		speed = 4;
		damage = 20;
		sprite = Sprite.missile;
		dirX = speed * Math.cos(angle);   // Calculate sin and cosin of the angle in order to find the direction to shoot the missile and multiply it by the speed
		dirY = speed * Math.sin(angle);   // 
	}
	
	// Methods
	
	/*
	 * Get missile sprite
	 */
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
	
	@Override
	public void update () {
		// Add collision detection here 
		// ...
		
		move();
	}
	
	/*
	 * Move missile towards the direction nx,ny
	 */
	protected void move() {
		x += dirX;
		y += dirY;
		if (distance() > range)
			remove();
	}
	
	/*
	 * Calculate missile distance from origin to destination through Pitagora's hypotenuse theorem
	 */
	private double distance () {
		double distance = 0;
		distance = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x)) + (yOrigin - y) * (yOrigin - y));
		return distance;
	}
	
	/*
	 * Render missile on the screen specifying the origin of x and y of the missile
	 */
	public void render (Screen screen) {
		screen.renderMissile((int)x, (int)y, this);   // Recalibrate to center origin of the missile to the robot
	}
}
