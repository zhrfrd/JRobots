package zhrfrd.jrobots;

import java.awt.Dimension;
import javax.swing.JLabel;

public class Missile extends JLabel{
	private static final long serialVersionUID = 9015341697123990586L;
	private Robot robot;
	private int xStart, yStart;
	
	// Constructor
	public Missile(Robot robot) {
		this.robot = robot;
	}
	
	// Methods
	
	/*
	 * Set staring point from where the missile is shot
	 */
	public void setStartingPosition() {
		xStart = robot.getX();
		yStart = robot.getY();
		Dimension size = this.getPreferredSize();
		
		this.setBounds(xStart, yStart, size.width, size.height);
	}
	
	/*
	 * Move missile towards the direction stated
	 */
	public void move(int direction) {
		
	}
}
