package zhrfrd.entities;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import zhrfrd.jrobots.JRobots;

public abstract class Robot extends Entity implements Runnable {
    private static final long serialVersionUID = -2377133046121834448L;
    
    /**
     * Determine if the move action has been done in the current turn
     */
    private boolean hasMoved = false;

    /**
     * Current status of the Robot
     */
    protected int life, direction, speed;
    
    public Thread threadRobot;   // Keep it public in order for the reflection of the class fields to work in the class JRobots
    private Random random;
    protected JRobots jrobots;
    protected Missile missile;
    public static String title = "JRobots";
    public ImageIcon iconRobot;
    
    // Constructor
    public Robot(JRobots jrobots) {
	this.jrobots = jrobots;
	threadRobot = new Thread(this, "Robot thread");
	
	panelBattlefield = jrobots.panelBattleField;
	
	if (iconRobot == null) {
	    iconRobot = this.initializeIcon();
	}
	
	this.setIcon(iconRobot);
	size = new Dimension(iconRobot.getIconWidth(), iconRobot.getIconHeight());
    }
    
    /**
     * Move the robot.
     * 
     * @param direction The direction in degrees where the robot is going to move.
     * @param speed The speed at which the robot travels.
     */
    public void move(int direction, int speed) {
	System.out.println(getPosX() + "; " + getPosY());
	
	if (this.hasMoved)
	    return;

	this.hasMoved = true;

	speed = Math.max(0, Math.min(5, speed));

	this.direction = direction;
//	direction -= 90;

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
	    wallHit = true;
	    this.inflictWallsDamage();
	    this.speed = 0;
	}

	this.draw();
    }

    /**
     * Inflict walls damage depending on the current speed of the robot.
     */
    private void inflictWallsDamage() {
	// Math.max to make sure life does not go below 0
	this.life = Math.max(0, this.life - this.speed);
    }

    /**
     * Get the life status of the robot.
     */
    public int getLife() {
	return this.life;
    }

    /**
     * Check if the robot is still alive.
     * 
     * @return True if the robot is alive, false otherwise.
     */
    public boolean isAlive() {
	if (life <= 0)
	    return false;

	return true;
    }

    /*
     * Scan the battlefield towards a single line direction
     */
    public int scan(int direction) {
	if (enemyFound())
	    return direction;

	return 0;
    }

    /*
     * Scan the battleground towards the specified direction +/- the resolution
     */
    public int scan(int direction, int resolution) {
	if (enemyFound())
	    return direction;

	return 0;
    }

    /**
     * Starting method of the robot and setting of the default values of the robot.
     */
    public void start() {
	this.setStartingPosition();
	this.draw();
	this.speed = 1;
	this.life = 100;
	
    }

    /*
     * Shoot a missile towards the direction specified that will land in the range specified.
     */
    public void shoot(int direction, int range) {
	missile = new Missile(this);
	missile.setStartingPosition();
	missile.draw();
	
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
     * Handle drawing of the robot to the battlefield in its current position.
     */
    public void draw() {
	this.panelBattlefield.add(this);
	this.setBounds(this.getAbsolutePosX(), this.getAbsolutePosY(), this.size.width, this.size.height);
    }
    
    /**
     * Get the icon of the robot from the res folder.
     * 
     * @return new ImageIcon(bufferedImage) The icon of the robot.
     */
    @Override
    public ImageIcon initializeIcon() {
	File fileIconRobot = new File("res/robot.png");

	try {
	    bufferedImage = ImageIO.read(fileIconRobot);
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return new ImageIcon(bufferedImage);
    }
    
    /**
     * Set robot starting position.
     */
    @Override
    public void setStartingPosition() {
	random = new Random();
	// this.posX = random.nextDouble(100);
	// this.posY = random.nextDouble(100);
	this.posX = 75;
	this.posY = 50;
    }
    
    // The run method contains the game loop responsible for the movements and
    // animation in the battlefield
    @Override
    final public void run() {
	this.start();

	while (this.isAlive()) {
	    this.hasMoved = false;
	    this.runTurn();
	    
	    try {
		Thread.sleep(10);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }
}