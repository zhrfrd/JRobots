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

import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Missile extends Entity {
    private static final long serialVersionUID = -8959188412014341019L;
    Robot robot;
    public ImageIcon iconMissile;
    
    public Missile(Robot robot) throws IOException {
	this.robot = robot;
	setStartingPosition();
	
	panelBattlefield = robot.jrobots.panelBattleField;
	
	if (iconMissile == null) {
	    iconMissile = this.initializeIcon();
	}
	
	this.setIcon(iconMissile);
	size = new Dimension(iconMissile.getIconWidth(), iconMissile.getIconHeight());
    }
    
    /**
     * Set missile's starting position by getting the current robot's position.
     */
    @Override
    public void setStartingPosition() {
	posX = robot.getPosX();
	posY = robot.getPosY();
    }
    
    /**
     * Get the icon of the missile from the res folder.
     * 
     * @return new ImageIcon(bufferedImage) The icon of the missile.
     * @throws IOException 
     */
    @Override
    public ImageIcon initializeIcon() throws IOException {
	InputStream stream = getClass().getClassLoader().getResourceAsStream("missile.png");
	return new ImageIcon(ImageIO.read(stream));
    }
}
