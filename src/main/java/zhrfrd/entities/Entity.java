package zhrfrd.entities;

import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class Entity extends JLabel implements Runnable {
    private static final long serialVersionUID = -2678484620304652158L;

    public static enum ENTITY_ICON {
	ROBOT, MISSILE
    };

    /**
     * Current entity position
     */
    protected double posX, posY;

    /**
     * Current entity status
     */
    protected int life, direction, speed;

    protected ImageIcon icon;
    protected Dimension size;

    public Entity(ENTITY_ICON icon) throws IOException {
	this.initializeEntityIcon(icon);
	this.size = new Dimension(this.icon.getIconWidth(), this.icon.getIconHeight());
    }

    /**
     * getters and setters
     */

    /**
     * Get the width of the battlefield.
     */
    private int getBattleFieldWidth() {
	return this.getParent().getWidth();
    }

    /**
     * Get the height of the battlefield.
     */
    private int getBattleFieldHeight() {
	return this.getParent().getHeight();
    }

    /**
     * Get the X position of the entity
     */
    private int getAbsolutePosX() {
	return (int) (this.posX * (this.getBattleFieldWidth() - this.size.width) / 100);
    }

    /**
     * Get the Y position of the entity
     */
    private int getAbsolutePosY() {
	return (int) (this.posY * (this.getBattleFieldHeight() - this.size.height - 1) / 100);
    }

    /**
     * Get the X position of the entity in percentage to the field width.
     */
    public double getPosX() {
	return this.posX;
    }

    /**
     * Get the Y position of the entity in percentage to the field height.
     */
    public double getPosY() {
	return this.posY;
    }

    /**
     * Get the current entity life
     */
    public int getLife() {
	return this.life;
    }

    /**
     * Get the current entity direction
     */
    public int getDirection() {
	return this.direction;
    }

    /**
     * Get the current entity speed
     */
    public int getSpeed() {
	return this.speed;
    }

    /**
     * Methods
     */

    /**
     * Set the entity's starting position.
     */
    protected abstract void setStartingPosition();

    /**
     * Load the main entity icon.
     * 
     * @param icon The type of icon to load.
     * 
     * @throws IOException When the icon is not found.
     */
    protected void initializeEntityIcon(ENTITY_ICON icon) throws IOException {
	String iconString = icon.name().toLowerCase() + ".png";
	this.icon = this.loadIcon(iconString);
	this.setIcon(this.icon);
    }

    /**
     * Loads an icon to be used by the entity.
     * 
     * @param icon The icon to load.
     * 
     * @return The icon file as ImageIcon.
     * 
     * @throws IOException Then the file cannot be loaded properly.
     */
    private ImageIcon loadIcon(String icon) throws IOException {
	InputStream stream = getClass().getClassLoader().getResourceAsStream(icon);
	return new ImageIcon(ImageIO.read(stream));
    }

    /**
     * Handle drawing of the entity to the battlefield.
     */
    protected final void draw() {
	this.setBounds(this.getAbsolutePosX(), this.getAbsolutePosY(), this.size.width, this.size.height);
    }

    /**
     * Starting method of the robot and setting of the default values of the robot.
     */
    public void start() {
	this.setStartingPosition();
	this.draw();
	this.speed = 0;
	this.life = 100;
    }

    /**
     * Check if the entity is alive.
     * 
     * @return boolean
     */
    public final boolean isAlive() {
	if (this.life <= 0)
	    return false;

	return true;
    }
}
