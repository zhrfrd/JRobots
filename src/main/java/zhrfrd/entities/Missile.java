//package zhrfrd.entities;
//
//import java.awt.Dimension;
//
//import javax.swing.JLabel;
//
//public class Missile extends JLabel {
//    private static final long serialVersionUID = 9015341697123990586L;
//    private Robot robot;
//    private int xStart, yStart;
//
//    // Constructor
//    public Missile(Robot robot) {
//	this.robot = robot;
//    }
//
//    // Methods
//
//    /*
//     * Set staring point from where the missile is shot
//     */
//    public void setStartingPosition() {
//	xStart = robot.getX();
//	yStart = robot.getY();
//	Dimension size = this.getPreferredSize();
//
//	this.setBounds(xStart, yStart, size.width, size.height);
//    }
//
//    /*
//     * Move missile towards the direction stated
//     */
//    public void move(int direction) {
//
//    }
//
//    public void draw() {
//	// TODO Auto-generated method stub
//	
//    }
//}

package zhrfrd.entities;

import java.io.IOException;

import javax.swing.ImageIcon;

public class Missile extends Entity {
    private static final long serialVersionUID = -8959188412014341019L;
    Robot robot;
    public ImageIcon iconMissile;
    private long startingTimestamp;

    public Missile(Robot robot, int direction) throws IOException {
	super(ENTITY_ICON.MISSILE);
	this.rotateIcon = true;
	this.robot = robot;
	this.direction = direction;
    }

    /**
     * Set missile's starting position by getting the current robot's position.
     */
    protected final void setStartingPosition() {
	this.posX = this.robot.getPosX();
	this.posY = this.robot.getPosY();
    }

    protected void move() {
	double radians = Math.toRadians(this.direction);
	double x = Math.cos(radians) * 0.1 * this.speed;
	double y = Math.sin(radians) * 0.1 * this.speed;

	// Adjust direction of the robot
	// TODO: if the movement is limited by x then y should be limited as well but at
	// the moment this is not taken into consideration
	double newPosX = posX + x;
	double newPosY = posY + y;

	this.posX = Math.max(0, Math.min(100, newPosX));
	this.posY = Math.max(0, Math.min(100, newPosY));

	if (newPosX < 0 || newPosX > 100 || newPosY < 0 || newPosY > 100) {
	    this.explode();
	}
    }

    private void explode() {
	System.out.println("Missile exploded!");

//	try {
//	    Thread.sleep(10);
//	} catch (InterruptedException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}

	this.commitSuicide();
	Thread.currentThread().interrupt();
    }

    @Override
    public void start() {
	this.setStartingPosition();
	this.draw();

	this.speed = 6;
	this.life = 100;
	this.startingTimestamp = System.currentTimeMillis();
    }

    @Override
    public void run() {
	this.start();

	while (this.robot.isAlive() && this.isAlive() && !Thread.interrupted()) {
	    this.move();
	    this.draw();

	    if (this.startingTimestamp < System.currentTimeMillis() - 1200) {
		this.explode();
	    }

	    if (!Thread.interrupted()) {
		try {
		    Thread.sleep(10);
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}
    }
}
