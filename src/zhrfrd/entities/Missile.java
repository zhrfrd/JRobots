package zhrfrd.entities;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Missile extends Entity {
    private static final long serialVersionUID = -8959188412014341019L;
    Robot robot;
    public ImageIcon iconMissile;
    
    public Missile(Robot robot) {
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
     */
    @Override
    public ImageIcon initializeIcon() {
	File fileIconRobot = new File("res/missile.png");

	try {
	    bufferedImage = ImageIO.read(fileIconRobot);
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return new ImageIcon(bufferedImage);
    }
}
