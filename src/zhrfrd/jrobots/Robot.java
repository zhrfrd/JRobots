package zhrfrd.jrobots;

import java.awt.Dimension;
import java.util.Random;

import javax.swing.JLabel;

public class Robot extends JLabel implements Runnable{
	private static final long serialVersionUID = -2377133046121834448L;
	protected int battlefieldWidth, battlefieldHeight;
	protected int life, direction, speed, posX, posY;
	protected final String UP = "up";
	protected final String DOWN = "down";
	protected final String LEFT = "left";
	protected final String RIGHT = "right";
	public Thread threadRobot;   // Keep it public in order for the reflection of the class fields to work in the class JRobots
	private Random random;
	private Dimension size;
	public static String title = "JRobots";

	// Constructor
	public Robot() {
		this.life = 100;
		threadRobot = new Thread(this, "Robot thread");
	}
	
	// Methods	
	// Set robot position
	public void setStartingPosition() {
		random = new Random();
		posX = random.nextInt(500);
		posY = random.nextInt(500);
		size = this.getPreferredSize();
		
		this.setBounds(posX, posY, size.width, size.height);
	}
	
	// Move the robot
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
				if (posY <= 0)
					life = 0;
				
				break;
				
			case DOWN:
				posY = this.getY() + 1;
				this.setBounds(posX, posY, size.width, size.height);
				System.out.println("x: " + posX + " y: " + posY);
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				break;
				
			case LEFT:
				posX = this.getX() - 1;
				this.setBounds(posX, posY, size.width, size.height);
				System.out.println("x: " + posX + " y: " + posY);
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				break;
				
			case RIGHT:
				posX = this.getX() + 1;
				this.setBounds(posX, posY, size.width, size.height);
				System.out.println("x: " + posX + " y: " + posY);
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// TEST
				if (posX >= battlefieldWidth - size.width)
					life = 0;
				
				break;
		}
	}
	
	protected void getWindowSize(int battlefieldWidth, int battlefieldHeight) {
		this.battlefieldWidth = battlefieldWidth;
		this.battlefieldHeight = battlefieldHeight;
	}

	// Get the x position of the robot
	public int getPosX() {
		return this.posX;
	}

	// Get the y position of the robot
	public int getPosY() {
		return this.posY;
	}
	
	// Check if the robot is still alive
	public boolean isAlive() {
		if (life <= 0)
			return false;
		
		return true;
	}
	
	// Scan the battlefield and, if your robot finds another robot, return the direction
	public int scan(int direction) {
		if (enemyFound())
			return direction;

		return 0;
	}
	
	// Starting method of the robot
	public void start() {
		// Leave empty
	}

	// Shoot the enemy
	public void shoot(int direction) {
		// Shoot bullet toward the direction
	}

	// Check if there is an enemy along the direction your robot is pointing 
	public boolean enemyFound() {
		// If yes return true, else return false
		return false;
	}
	
	//TEST METHOD
	public void boom() {
		System.out.println("BOOM BOOM!!");
	}

	@Override
	// The run method contains the game loop responsible for the movements and animation in the battlefield
	public void run () {
		setStartingPosition();
		
		start();
	}
}