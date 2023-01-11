package zhrfrd.entities;

import java.awt.Color;
import java.io.IOException;

import javax.swing.ImageIcon;

public class Missile extends Entity {
    private static final long serialVersionUID = -8959188412014341019L;
    Robot robot;
    public ImageIcon iconMissile;

    public Missile(Robot robot, int direction) throws IOException {
	super(ENTITY_ICON.MISSILE);
	this.rotateIcon = true;
	this.robot = robot;
	this.direction = direction;
    }

    /**
     * Set missile's starting position by getting the current robot's position.
     */
    protected final void setStartingPosition() {
	this.posX = this.robot.getPosX();
	this.posY = this.robot.getPosY();
    }
    
    /**
     * Update missile information in the game.
     */
    public void update() {
	this.move();
	this.draw();
    }

    protected void move() {
	double radians = Math.toRadians(this.direction);
	double x = Math.cos(radians) * 0.1 * this.speed;
	double y = Math.sin(radians) * 0.1 * this.speed;

	// Adjust direction of the robot
	// TODO: if the movement is limited by x then y should be limited as well but at
	// the moment this is not taken into consideration
	double newPosX = posX + x;
	double newPosY = posY + y;

	this.posX = Math.max(0, Math.min(100, newPosX));
	this.posY = Math.max(0, Math.min(100, newPosY));
    }

    /**
     * Retrieve the specific color of the particles.
     * 
     * @return The RGB color code of the particle.
     */
    protected Color getParticleColor() {
	Color color = new Color(65, 50, 30);
	return color;
    }

    @Override
    public void begin() {
	this.setStartingPosition();

	this.speed = 3;
	this.life = 100;
    }
}