package zhrfrd.jrobots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Robot extends JLabel implements Runnable{
	public int life, direction, speed, posX, posY;
	JLabel label;
	File robotFile;
	BufferedImage bufferedImage;
	ImageIcon imageIcon;
	Thread t;
	public Random random;

	// Constructor
	Robot() {
		
		
		random = new Random();
//		this.setPreferredSize(new Dimension(300, 300));
		this.life = 100;
		t = new Thread(this, "Thread");
		
	}
	
	public void setPosition() {
		random = new Random();
		posX = random.nextInt(500);
		posY = random.nextInt(500);
		Dimension size = this.getPreferredSize();
		this.setBounds(posX, posY, size.width, size.height);
		
		System.out.println(posX);
//		this.setLocation(2, 8);
	}
	
	// Methods
	// Scan the battlefield and, if your robot finds another robot, return the direction
	public int scan(int direction) {
		if (enemyFound()) {
			return direction;
		}

		return 0;
	}
	
//	public void paint(Graphics g) {
//		posX = random.nextInt(200);
//		posY = random.nextInt(200);
////		System.out.println(posX + " - " + posY);
//		System.out.println(this.getX() + " - " + this.getY());
////		this.setLocation(posX, posY);
////		g.setColor(Color.red);
////		g.fillRect(posX, posY, 30, 30);
//	}

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
		System.out.println(t.getName());
//		
//		label = new JLabel();
//		robotFile = new	File("/Users/faridzouheir/eclipse-workspace/JRobots/res/robot.png");
//		
//		try {
//			bufferedImage = ImageIO.read(robotFile);
//		} catch (IOException e) {
//			System.out.println("IOException");
//			e.printStackTrace();
//		}
//		imageIcon = new ImageIcon(bufferedImage);
//		setIcon(imageIcon);
		
		setPosition();
		
	}
}



/*
package zhrfrd.jrobots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class Robot extends JPanel implements Runnable{
	public int life, direction, speed, posX, posY;
	Thread t;
	public Random random;

	// Constructor
	Robot() {
		random = new Random();
		this.setPreferredSize(new Dimension(300, 300));
		this.life = 100;
		t = new Thread(this, "Thread");
	}

	// Methods
	// Scan the battlefield and, if your robot finds another robot, return the direction
	public int scan(int direction) {
		if (enemyFound()) {
			return direction;
		}

		return 0;
	}
	
	public void paint(Graphics g) {
		posX = random.nextInt(200);
		posY = random.nextInt(200);
		System.out.println(posX + " - " + posY);
		g.setColor(Color.red);
		g.fillRect(posX, posY, 30, 30);
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
		
		
	}
}

*/