package zhrfrd.jrobots;

import java.awt.Dimension;
import java.util.Random;

import javax.swing.JLabel;

public class Robot extends JLabel implements Runnable{
	protected static final long serialVersionUID = 1L;
	protected int life, direction, speed, posX, posY;
	public Thread threadRobot;   // Keep it public in order for the reflection of the class fields to work in the class JRobots
	private Random random;
	private Dimension size;
	public static String title = "JRobots";
	private boolean running = false;

	// Constructor
	public Robot() {
		this.life = 100;
		running = true;
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
		
		System.out.println("x: " + posX + " y: " + posY);
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

	// Move the robot
	public void move(int direction, int speed) {
		if (direction == 1) {
			posX = this.getX() + 1;
			this.setBounds(posX, posY, size.width, size.height);
			System.out.println("x: " + posX + " y: " + posY);
		}
		// Move the robot around the map in the specific direction with the specific speed
	}

	// Get the x position of the robot
	public int getPosX() {
		return this.posX;
	}

	// Get the y position of the robot
	public int getPosY() {
		return this.posY;
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
	public void run () {
		setStartingPosition();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0; // How many frames per second
		int updates = 0; // How many updates per second (it should be always 60)
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
//				update(); // Update 60 times per second
				updates++;
				delta--;
			}
//			render();
			
			frames++;
			if (System.currentTimeMillis() - timer > 1000) { // Each second
				move(1, 3);
				timer += 1000; // Increase by 10000 each time in order to keep the upper condition
				System.out.println(title + "  |  " + updates + "ups " + frames + "fps");
				updates = 0;
				frames = 0;
			}
		}
	}
}