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

public class Robot extends JLabel implements Runnable{
	private static final long serialVersionUID = -2377133046121834448L;
	protected int battlefieldWidth, battlefieldHeight;
	protected int life, direction, speed;
	protected double posX, posY;
//	protected final String UP = "up";
//	protected final String DOWN = "down";
//	protected final String LEFT = "left";
//	protected final String RIGHT = "right";
	public Thread threadRobot;   // Keep it public in order for the reflection of the class fields to work in the class JRobots
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
	}
	
	// Methods	
	
	/*
	 * Get ImageIcon of the missile through its path
	 */
	private ImageIcon getIconMissile () {
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
		posX = random.nextInt(500);
		posY = random.nextInt(500);
		size = this.getPreferredSize();
		
		this.setBounds((int)posX, (int)posY, size.width, size.height);
		
		this.add(missile);
	}
	
	/*
	 * Move the robot
	 */
	public void move(int direction, int speed) {
		this.direction = direction;
		this.speed = speed;
		posX = this.getPosX();
		posY = this.getPosY();
		
		double radians = Math.toRadians(direction);
		double x = Math.cos(radians) * speed;
		double y = Math.sin(radians) * speed;
		
		System.out.println(Math.cos(radians) + "   " + Math.sin(radians));
		
		if (direction == 0 || direction == 180) {
			posX += x;
			posY += y;
		}
		
		if (direction == 90 || direction == 270) {
			posX -= x;
			posY -= y;
		}
		
		if ((direction < 90 && direction > 0) || (direction < 270 && direction > 180)) {
			posX += x;
			posY -= y;
		}
		
		if ((direction < 180 && direction > 90) || (direction > 270 && direction != 0)) {
			posX += x;
			posY -= y;
		}
		
		this.setBounds((int)posX, (int)posY, size.width, size.height);
		
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Get the life status of the robot
	 */
	public int getLife() {
		return this.life;
	}
	
	/*
	 * Get the size of the battlefield
	 */
	public void getWindowSize(int battlefieldWidth, int battlefieldHeight) {
		this.battlefieldWidth = battlefieldWidth;
		this.battlefieldHeight = battlefieldHeight;
	}

	/*
	 * Get the x position of the robot
	 */
	public double getPosX() {
		return this.posX;
	}

	/*
	 * Get the y position of the robot
	 */
	public double getPosY() {
		return this.posY;
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
	}

	/*
	 * Shoot a missile towards the direction specified that will land in the range specified
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
	// The run method contains the game loop responsible for the movements and animation in the battlefield
	public void run() {
		setStartingPosition();
		
		start();
	}
}