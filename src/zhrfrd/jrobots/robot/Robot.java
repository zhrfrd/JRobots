package zhrfrd.jrobots.robot;

public class Robot {
	public int life, direction, speed, posX, posY;
	
	// Constructor
	Robot() {
		this.life = 100;
	}
	
	// Methods
	// Scan the battlefield and, if your robot finds another robot, return the direction
	public int scan(int direction) {
		if (enemyFound()) {
			return direction;
		}
		
		return 0;
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
}
