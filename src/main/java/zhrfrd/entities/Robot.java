package zhrfrd.entities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import zhrfrd.jrobots.JRobots;

public abstract class Robot extends Entity {
    private static final long serialVersionUID = -2377133046121834448L;

    /**
     * Determine if the move action has been done in the current turn.
     */
    private boolean hasMoved = false;
    protected ImageIcon imageIcon;
    protected File fileIconMissile;
    protected BufferedImage bufferedImage;
    public static String title = "JRobots";
    public ImageIcon iconRobot;
    public Missile missile;
    public ArrayList<Missile> missileList;
    private boolean isRobotStarted = false;
    protected boolean isMissileShot = false;
    int missileLifeCounter = 0;
    int missileLifeSpan = 120;

    public Robot() throws IOException {
	super(ENTITY_ICON.ROBOT);
    }

    /**
     * Set robot starting position.
     */
    protected final void setStartingPosition() {
	Random random = new Random();
	this.posX = random.nextDouble(100);
	this.posY = random.nextDouble(100);
    }

    /**
     * Move the robot.
     * 
     * @param direction The direction in degrees where the robot is going to move.
     * @param speed     The speed at which the robot travels.
     */
    public void move(int direction, int speed) {
	if (this.hasMoved)
	    return;

	this.hasMoved = true;
	speed = Math.max(0, Math.min(5, speed));
	this.direction = direction;
	this.speed = speed;
	double radians = Math.toRadians(direction);
	double x = Math.cos(radians) * 0.1 * this.speed;
	double y = Math.sin(radians) * 0.1 * this.speed;

	// Adjust direction of the robot
	// TODO: if the movement is limited by x then y should be limited as well but at
	// the moment this is not taken into consideration
	double newPosX = posX + x;
	double newPosY = posY + y;

	this.posX = Math.max(0, Math.min(100, newPosX));
	this.posY = Math.max(0, Math.min(100, newPosY));

	if (newPosX < 0 || newPosX > 100 || newPosY < 0 || newPosY > 100) {
	    this.inflictWallsDamage();
	    this.speed = 0;
	}
    }
    
    /**
     * Update robot status
     */
    public void update() {
	if (!isRobotStarted) {
	    this.setStartingPosition();
	    isRobotStarted = true;
	}
	
	this.hasMoved = false;
	this.runTurn();
    	this.move(this.direction, this.speed);
    	this.draw();
    	
    	if (missileList != null && missileList.size() > 0) {
    	    this.missileLifeCounter ++;
    	    this.missile.update();
    	    
    	    if (missileLifeCounter >= missileLifeSpan) {
    		this.missile.explode();
    	    	this.cleanMissiles();
    	    	isMissileShot = false;
    	    }
    	}
    }
    
    /*
     * Shoot a missile towards the direction specified that will land in the range
     * specified. Only one missileList can be shot at one time per robot.
     */
    public final void shoot(int direction) throws IOException {
	if (missileList != null && missileList.size() > 0)
	    return;
	
//	if (isMissileShot)
//	    return;
	
	if (!isMissileShot) {
	    isMissileShot = true;
	
            missileList = new ArrayList<Missile>();
            missile = new Missile(this, direction);
        	
            missileList.add(missile);
            missile.begin();
        	
            this.getParent().add(missile);
//            isMissileShot = false;
	}

//	if (missileList == null)
//	    missileList = new ArrayList<Missile>();

//	missileList.add(missile);
    }

    /**
     * Inflict walls damage depending on the current speed of the robot.
     */
    private void inflictWallsDamage() {
	this.inflictDamage(this.speed);
    }

    /**
     * Inflicts the amount of damage specified to the robot
     * 
     * @param value the amount of damage to inflict. Absolute value is used
     */
    private void inflictDamage(int value) {
	this.life = Math.max(0, Math.min(100, this.life - Math.abs(value)));
    }

    /*
     * Scan the battlefield towards a single line direction
     */
    public final int scan(int direction) {
	if (enemyFound())
	    return direction;

	return 0;
    }

    /*
     * Scan the battleground towards the specified direction +/- the resolution
     */
    public final int scan(int direction, int resolution) {
	if (enemyFound())
	    return direction;

	return 0;
    }

    /**
     * Check if there is an enemy along the direction your robot is pointing.
     */
    public boolean enemyFound() {
	// If yes return true, else return false
	return false;
    }

    abstract protected void runTurn();

    /**
     * Start the robot in the battlefield.
     */
//    public final void start() {
//	super.start();
//    }

    /**
     * Checks if some missile is dead and removes it from the battlefield.
     */
    private void cleanMissiles() {
	if (this.missileList == null || this.missileList.size() == 0)
	    return;

	int i = 0;

	while (i < this.missileList.size()) {
	    Missile missile = this.missileList.get(i);
//	    Thread missileThread = this.missilesThreads.get(i);

	    if (missile != null) {
		this.getParent().remove(missile);
		this.getParent().validate();
		this.getParent().repaint();
		this.missileList.remove(i);
//		this.missilesThreads.remove(i);
	    } else
		i++;
	}
    }
    
    // The run method contains the game loop responsible for the movements and
    // animation in the battlefield
//    @Override
//    public final void run() {
//	this.start();
//
//	while (this.isAlive()) {
//	    this.update();
////	    this.hasMoved = false;
////	    this.runTurn();
////	    this.move(this.direction, this.speed);
////	    this.draw();
////
////	    this.cleanMissiles();
//
////	    try {
////		Thread.sleep(10);
////	    } catch (InterruptedException e) {
////		// TODO Auto-generated catch block
////		e.printStackTrace();
////	    }
//	}
//    }
}