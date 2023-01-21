package zhrfrd.entities;

import java.awt.Color;
import java.io.IOException;

import zhrfrd.graphics.Screen;
import zhrfrd.graphics.Sprite;

public class Missile extends Entity {
    private static final long serialVersionUID = -8959188412014341019L;
    private Robot robot;

    public Missile(Robot robot, int direction) {
	this.rotateIcon = true;
	this.robot = robot;
	this.direction = direction;
    }

    /**
     * Set missile's starting position by getting the current robot's position.
     */
    protected final void setStartingPosition() {
	posX = this.robot.getPosX();
	posY = this.robot.getPosY();
    }

    protected void move() {
	double radians = Math.toRadians(direction);
	double x = Math.cos(radians) * 0.1 * speed;
	double y = Math.sin(radians) * 0.1 * speed;

	// Adjust direction of the robot
	// TODO: if the movement is limited by x then y should be limited as well but at
	// the moment this is not taken into consideration
	double newPosX = posX + x;
	double newPosY = posY + y;

	this.posX = Math.max(0, Math.min(400, newPosX));
	this.posY = Math.max(0, Math.min(400, newPosY));
    }
    
    /**
     * Starting method of the missile which set its default values.
     */
    public void begin() {
	this.setStartingPosition();

	this.speed = 10 * BOOST;
	this.life = 100;
    }

    /**
     * Retrieve the specific color of the particles.
     * @return The RGB color code of the particle.
     */
    protected Color getParticleColor() {
	Color color = new Color(65, 50, 30);
	return color;
    }
    
    /**
     * Update missile information in the game.
     */
    @Override
    public void update() {
	move();
    }
    
    /**
     * Render the missile to the battlefield.
     * @param screen Screen that handles the rendering of the missile.
     */
    @Override
    public void render(Screen screen) {
	screen.renderEntity((int)posX, (int)posY,  Sprite.missile);
    }
}