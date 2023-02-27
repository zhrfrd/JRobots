package zhrfrd.jrobots.entities;

import java.awt.Rectangle;

import zhrfrd.jrobots.graphics.Screen;
import zhrfrd.jrobots.graphics.Sprite;

public class Missile extends Entity {
    private static final long serialVersionUID = -8959188412014341019L;
    private Robot robot;
    public Rectangle solidArea = new Rectangle((int)posX, (int)posY, 16, 16);   // Default solid area for the collision detection

    /**
     * Construct a missile.
     * @param robot Robot that generates the missile.
     * @param direction direction where the missile is shot.
     */
    public Missile(Robot robot, int direction) {
	rotateIcon = true;
	this.robot = robot;
	this.direction = direction;
    }

    /**
     * Set missile's starting position by getting the current robot's position.
     */
    protected final void setStartingPosition() {
	posX = robot.getPosX();
	posY = robot.getPosY();
    }
    
    /**
     * Starting method of the missile which set its default values.
     */
    public void begin() {
	setStartingPosition();

	speed = 10 * BOOST;
	life = 100;
    }
    
    /**
     * Update missile information in the game such as position in the battlefield.
     */
    @Override
    public void update() {
	double radians = Math.toRadians(direction);
	double x = Math.cos(radians) * 0.1 * speed;
	double y = Math.sin(radians) * 0.1 * speed;

	// Adjust direction of the robot
	// TODO: if the movement is limited by x then y should be limited as well but at
	// the moment this is not taken into consideration
	double newPosX = posX + x;
	double newPosY = posY + y;

	posX = Math.max(0, Math.min(400, newPosX));
	posY = Math.max(0, Math.min(400, newPosY));
	
	solidArea.x = (int)posX;
    	solidArea.y = (int)posY;
    	
    	
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