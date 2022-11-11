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

    public Missile(Robot robot) throws IOException {
	super(ENTITY_ICON.MISSILE);
	this.robot = robot;
	this.direction = robot.getDirection();
    }

    /**
     * Set missile's starting position by getting the current robot's position.
     */
    protected final void setStartingPosition() {
	posX = robot.getPosX();
	posY = robot.getPosY();
    }

    @Override
    public void run() {
    }
}
