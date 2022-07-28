package zhrfrd.jrobots;

import java.awt.Dimension;
import java.util.Random;

import javax.swing.JLabel;

public class Robot extends JLabel implements Runnable{
	protected static final long serialVersionUID = 1L;
	protected int screenWidth, screenHeight;
	protected int life, direction, speed, posX, posY;
	protected final String UP = "up";
	protected final String DOWN = "down";
	protected final String LEFT = "left";
	protected final String RIGHT = "right";
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
	
	// Move the robot
	public void move(String direction) {
		switch (direction) {
			case UP:
				posY = this.getY() - 1;
				this.setBounds(posX, posY, size.width, size.height);
				System.out.println("x: " + posX + " y: " + posY);
				
				if (posY <= 0) {
					posY ++;
					this.setBounds(posX, posY, size.width, size.height);
				}
				
				break;
				
			case DOWN:
				posY = this.getY() + 1;
				this.setBounds(posX, posY, size.width, size.height);
				System.out.println("x: " + posX + " y: " + posY);
				
				if (posY >= screenWidth - this.getWidth()) {
					posY --;
					this.setBounds(posX, posY, size.width, size.height);
				}
				
				break;
				
			case LEFT:
				posX = this.getX() - 1;
				this.setBounds(posX, posY, size.width, size.height);
				System.out.println("x: " + posX + " y: " + posY);
				
				if (posX <= 0) {
					posX ++;
					this.setBounds(posX, posY, size.width, size.height);
				}
				
				break;
				
			case RIGHT:
				posX = this.getX() + 1;
				this.setBounds(posX, posY, size.width, size.height);
				System.out.println("x: " + posX + " y: " + posY);
				
				if (posX >= screenWidth - this.getWidth()) {
					posX --;
					this.setBounds(posX, posY, size.width, size.height);
				}
				
				break;
		}
//		if (direction == 1) {
//			posX = this.getX() + 1;
//			this.setBounds(posX, posY, size.width, size.height);
//			System.out.println("x: " + posX + " y: " + posY);
//			if (posX >= screenWidth - this.getWidth()) {
//				direction = 2;
//			}
//		}
//		
//		if (direction == 2) {
//			posY = this.getY() + 1;
//		}
	}
	
	protected void getWindowSize(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	// Update the animation 
	private void update () {
		move(LEFT);
	}

	// Get the x position of the robot
	public int getPosX() {
		return this.posX;
	}

	// Get the y position of the robot
	public int getPosY() {
		return this.posY;
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
		
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;   // How many frames per second
		int updates = 0;   // How many updates per second (it should be always 60)
		
		// Game loop
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while (delta >= 1) {
//				update();   // Update 60 times per second
				start();
				updates++;
				delta--;
			}
			
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {   // Each second
				timer += 1000;   // Increase by 10000 each time in order to keep the condition above
				System.out.println(title + "  |  " + updates + "ups " + frames + "fps");
				updates = 0;
				frames = 0;
			}
		}
	}
}