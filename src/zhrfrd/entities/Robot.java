package zhrfrd.entities;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Robot extends JLabel implements Runnable {
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

    private Random random;
    private Dimension size;
    public static String title = "JRobots";
    protected ImageIcon imageIcon;
    protected File fileIconMissile;
    protected BufferedImage bufferedImage;
    protected Missile missile;
    JPanel panelBattlefield;

    // Constructor
    public Robot() {
	this.life = 100;
	threadRobot = new Thread(this, "Robot thread");
	missile = new Missile(this);
	imageIcon = getIconMissile();
	this.size = new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight());
    }

    // Methods

    /*
     * Get ImageIcon of the missile through its path
     */
    private ImageIcon getIconMissile() {
	fileIconMissile = new File("res/missile.png");

	try {
	    bufferedImage = ImageIO.read(fileIconMissile);
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return new ImageIcon(bufferedImage);
    }

    protected void getPanel() {
    }

    /*
     * Set robot position
     */
    public void setStartingPosition() {
	random = new Random();
	this.posX = random.nextDouble(100);
	this.posY = random.nextDouble(100);

	this.add(missile);
    }

    /*
     * Move the robot
     */
    public void move(int direction, int speed) {
	this.direction = direction;
	direction -= 90;

	// Stop the movement when the robot hits the walls
	if (this.posX <= 0 || this.posX >= 100 || this.posY <= 0 || this.posY >= 100)
	    this.speed = 0;
	else
	    this.speed = speed;

	double radians = Math.toRadians(direction);
	double x = Math.cos(radians) * 0.1 * this.speed;
	double y = Math.sin(radians) * 0.1 * this.speed;

	// Adjust direction of the robot
	// TODO: if the movement is limited by x then y should be limited as well but at
	// the moment this is not taken into consideration
	this.posX = Math.max(0, Math.min(100, posX + x));
	this.posY = Math.max(0, Math.min(100, posY + y));

	this.draw();

	try {
	    Thread.sleep(10);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
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
	if (life <= 0)
	    return false;

	return true;
    }

    /*
     * Scan the battlefield towards a single line direction
     */
    public int scan(int direction) {
	double x = getPosX();
	double y = getPosY();

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

    /*
     * Starting method of the robot
     */
    public void start() {
	// Leave empty
	this.draw();
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

    @Override
    // The run method contains the game loop responsible for the movements and
    // animation in the battlefield
    public void run() {
	setStartingPosition();

	start();
    }
}