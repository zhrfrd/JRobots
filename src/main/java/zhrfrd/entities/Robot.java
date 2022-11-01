package zhrfrd.entities;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class Robot extends JLabel implements Runnable {
    private static final long serialVersionUID = -2377133046121834448L;

    /**
     * Current status of the Robot
     */
    protected int life, direction, speed;

    /**
     * Current Robot position in percentage to the width and height of the battle
     * field
     */
    protected double posX, posY;

    /*
     * Keep it public in order for the reflection of the class fields to work in the
     * class JRobots
     */
    public Thread threadRobot;

    private Dimension size;
    public static String title = "JRobots";
    protected ImageIcon imageIcon;
    protected File fileIconMissile;
    protected BufferedImage bufferedImage;
    protected Missile missile;
    JPanel panelBattlefield;

    /**
     * Determine if the move action has been done in the current turn
     */
    private boolean hasMoved = false;

    // Constructor
    public Robot() {
	this.threadRobot = new Thread(this, "Robot thread");
	try {
	    this.imageIcon = this.getIconMissile();
	    this.size = new Dimension(this.imageIcon.getIconWidth(), this.imageIcon.getIconHeight());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /*
     * Get ImageIcon of the missile through its path
     */
    private ImageIcon getIconMissile() throws IOException {
	InputStream missileStream = getClass().getClassLoader().getResourceAsStream("missile.png");
	return new ImageIcon(ImageIO.read(missileStream));
    }

    protected void getPanel() {
    }

    /*
     * Set robot position
     */
    public void setStartingPosition() {
	Random random = new Random();
	this.posX = random.nextDouble(100);
	this.posY = random.nextDouble(100);

//	this.add(missile);
    }

    /*
     * Move the robot
     */
    public void move(int direction, int speed) {
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
	    this.inflictWallsDamage();
	    this.speed = 0;
	}

	this.draw();
    }

    /**
     * Inflict walls damage depending on the current speed of the robot
     */
    private void inflictWallsDamage() {
	// Math.max to make sure life does not go below 0
	this.life = Math.max(0, this.life - this.speed);
    }

    /**
     * Draws the robot in the battle field in its current position
     */
    public void draw() {
	this.setBounds(this.getAbsolutePosX(), this.getAbsolutePosY(), this.size.width, this.size.height);
    }

    /*
     * Get the life status of the robot
     */
    public int getLife() {
	return this.life;
    }

    /*
     * Get the width of the battlefield
     */
    private int getBattleFieldWidth() {
	return this.getParent().getWidth();
    }

    /*
     * Get the height of the battlefield
     */
    private int getBattleFieldHeight() {
	return this.getParent().getHeight();
    }

    /*
     * Get the X position of the robot in percentage to the field width
     */
    public double getPosX() {
	return this.posX;
    }

    /*
     * Get the Y position of the robot in percentage to the field height
     */
    public double getPosY() {
	return this.posY;
    }

    /**
     * Get the X position of the robot
     */
    private int getAbsolutePosX() {
	return (int) (this.posX * (this.getBattleFieldWidth() - this.size.width) / 100);
    }

    /**
     * Get the Y position of the robot
     */
    private int getAbsolutePosY() {
	return (int) (this.posY * (this.getBattleFieldHeight() - this.size.height) / 100);
    }

    /*
     * Check if the robot is still alive
     */
    public boolean isAlive() {
	if (this.life <= 0)
	    return false;

	return true;
    }

    /*
     * Scan the battlefield towards a single line direction
     */
    public int scan(int direction) {
//	double x = getPosX();
//	double y = getPosY();
//
//	if (enemyFound())
//	    return direction;

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

    /*
     * Starting method of the robot
     */
    public void start() {
	this.draw();
	this.speed = 0;
	this.life = 100;
    }

    /*
     * Shoot a missile towards the direction specified that will land in the range
     * specified
     */
    public void shoot(int direction, int range) {
	missile.setIcon(imageIcon);
    }

    /*
     * Check if there is an enemy along the direction your robot is pointing
     */
    public boolean enemyFound() {
	// If yes return true, else return false
	return false;
    }

    // TEST
    public void boom() {
	System.out.println("BOOM BOOM!!");
    }

    // The run method contains the game loop responsible for the movements and
    // animation in the battlefield
    final public void run() {
	this.setStartingPosition();
	this.start();

	while (this.isAlive()) {
	    this.hasMoved = false;
	    this.runTurn();
	    this.move(this.direction, this.speed);
	    try {
		Thread.sleep(10);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    abstract protected void runTurn();
}