package zhrfrd.jrobots;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Robot extends JLabel implements Runnable{
	protected static final long serialVersionUID = 1L;
	protected int life, direction, speed, posX, posY;
	protected Thread threadRobot;
	private Random random;
	private Dimension size;
	private File fileRobot;
	private BufferedImage bufferedImage;
	private ImageIcon imageIcon;

	// Constructor
	public Robot() {
		this.life = 100;
		
		threadRobot = new Thread(this, "Robot thread");
		setIconRobot();
	}
	
	// Methods
	// Generate robot icon
	public void setIconRobot() {
		fileRobot = new File("/Users/faridzouheir/eclipse-workspace/JRobots/res/robot.png");
		try {
			bufferedImage = ImageIO.read(fileRobot);
		} catch (IOException e) {
			e.printStackTrace();
		}
		imageIcon = new ImageIcon(bufferedImage);
		setIcon(imageIcon);
	}
	
	//Set robot position
	public void setPosition() {
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
		setPosition();
		
	}
}