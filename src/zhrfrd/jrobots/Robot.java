package zhrfrd.jrobots;

import java.awt.Dimension;
import java.util.Random;

import javax.swing.JLabel;

public class Robot extends JLabel implements Runnable{
	protected static final long serialVersionUID = 1L;
	protected int life, direction, speed, posX, posY;
	protected Thread threadRobot;
	private Random random;
	private Dimension size;

	// Constructor
	Robot() {
		this.life = 100;
		threadRobot = new Thread(this, "My thread");
	}
	
	// Methods
	//Set robot position
	public void setPosition() {
		random = new Random();
		posX = random.nextInt(500);
		posY = random.nextInt(500);
		size = this.getPreferredSize();
		this.setBounds(posX, posY, size.width, size.height);
		
		System.out.println(posX);
	}
	
	// Scan the battlefield and, if your robot finds another robot, return the direction
	public int scan(int direction) {
		if (enemyFound())
			return direction;

		return 0;
	}
	
	//TEST METHOD
	public void boom() {
		System.out.println("BOOM BOOM!!");
	}

	// Shoot the enemy
	public void shoot(int direction) {
		// Shoot bullet toward the direction
	}

	// Move the robot
	public void move(int direction, int speed) {
		// Move the robot around the map in the specific direction with the specific speed
	}

	// Get the x position of the robot
	public int posX() {
		return this.posX;
	}

	// Get the y position of the robot
	public int posY() {
		return this.posY;
	}

	// Check if there is an enemy along the direction your robot is pointing 
	public boolean enemyFound() {
		// If yes return true, else return false
		return false;
	}

	@Override
	public void run () {
		System.out.println(threadRobot.getName());
		setPosition();
		
	}
}