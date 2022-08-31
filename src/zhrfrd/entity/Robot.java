package zhrfrd.entity;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import zhrfrd.battle.Battle;

public class Robot extends Entity implements Runnable{
	private static final long serialVersionUID = -2377133046121834448L;
	protected int battlefieldWidth, battlefieldHeight;
	protected int life, direction, speed, posX, posY;
	protected final String UP = "up";
	protected final String DOWN = "down";
	protected final String LEFT = "left";
	protected final String RIGHT = "right";
	public Thread threadRobot;   // Keep it public in order for the reflection of the class fields to work in the class JRobots
	private Random random;
	protected Dimension size;
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
	
	public void setPanel(JPanel panelBattlefield) {
		this.panelBattlefield = panelBattlefield;
	}

	/*
	 * Set robot position
	 */
	public void setStartingPosition() {
		random = new Random();
		posX = random.nextInt(500);
		posY = random.nextInt(500);
		size = this.getPreferredSize();
		
		this.setBounds(posX, posY, size.width, size.height);
		
		this.add(missile);
	}
	
	/*
	 * Move the robot
	 */
	public void move(String direction) {
		switch (direction) {
			case UP:
				posY = this.getY() - 1;
				this.setBounds(posX, posY, size.width, size.height);
//				System.out.println("x: " + posX + " y: " + posY);
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// TEST 
				if (posY <= 10)
					this.life = 0;
				
				break;
				
			case DOWN:
				posY = this.getY() + 1;
				this.setBounds(posX, posY, size.width, size.height);
//				System.out.println("x: " + posX + " y: " + posY);
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				break;
				
			case LEFT:
				posX = this.getX() - 1;
				this.setBounds(posX, posY, size.width, size.height);
//				System.out.println("x: " + posX + " y: " + posY);
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				break;
				
			case RIGHT:
				posX = this.getX() + 1;
				this.setBounds(posX, posY, size.width, size.height);
//				System.out.println("x: " + posX + " y: " + posY);
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// TEST
				if (posX >= battlefieldWidth - size.width)
					this.life = 30;
				
				break;
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
	public int getPosX() {
		return this.posX;
	}

	/*
	 * Get the y position of the robot
	 */
	public int getPosY() {
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
		int x = getPosX();
		int y = getPosY();
		
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
		panelBattlefield.add(missile);
//		System.out.println(missile.getX());
	}

	/*
	 * Check if there is an enemy along the direction your robot is pointing 
	 */
	public boolean enemyFound() {
		// If yes return true, else return false
		return false;
	}
	
	public void init (Battle battle) {
		this.battle = battle;
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