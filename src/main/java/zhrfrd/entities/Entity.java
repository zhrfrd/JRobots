package zhrfrd.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class Entity extends JLabel {
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
    
    protected int maxLife;

    /**
     * The icon will rotate depending on the current entity direction
     */
    protected boolean rotateIcon;

    protected Image icon;
    protected Dimension size;

    public Entity(ENTITY_ICON icon) throws IOException {
	this.initializeEntityIcon(icon);
    }
    
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
     * Get the X position of the entity.
     */
    private int getAbsolutePosX() {
	return (int) (this.posX * (this.getBattleFieldWidth() - this.size.width) / 100);
    }

    /**
     * Get the Y position of the entity.
     */
    private int getAbsolutePosY() {
	return (int) (this.posY * (this.getBattleFieldHeight() - this.size.height) / 100);
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
     * Get the current entity life.
     */
    public int getLife() {
	return this.life;
    }

    /**
     * Get the current entity direction.
     */
    public int getDirection() {
	return this.direction;
    }

    /**
     * Get the current entity speed.
     */
    public int getSpeed() {
	return this.speed;
    }

    /**
     * Set the entity's starting position.
     */
    protected abstract void setStartingPosition();

    /**
     * Load the main entity icon.
     * 
     * @param icon The type of icon to load.
     * @throws IOException When the icon is not found.
     */
    protected void initializeEntityIcon(ENTITY_ICON icon) throws IOException {
	switch (icon) {
	case ROBOT:
	    this.size = new Dimension(70, 70);
	    break;
	case MISSILE:
	    this.size = new Dimension(40, 40);
	    break;
	}

	String iconString = icon.name().toLowerCase() + ".png";

	this.icon = this.loadIcon(iconString);
//	this.icon = this.scaleImageToSize(this.icon);
	this.icon = this.icon.getScaledInstance((int) this.size.getWidth(), (int) this.size.getHeight(), Image.SCALE_SMOOTH);

	this.setIcon(new ImageIcon(this.icon));
    }

    /**
     * Loads an icon to be used by the entity.
     * 
     * @param icon The icon to load.
     * @return The icon file as ImageIcon.
     * @throws IOException Then the file cannot be loaded properly.
     */
    private BufferedImage loadIcon(String icon) throws IOException {
	InputStream stream = getClass().getClassLoader().getResourceAsStream(icon);
	return ImageIO.read(stream);
    }

    /**
     * Handle drawing of the entity to the battlefield.
     */
    protected final void draw() {
//	if (this.rotateIcon) {
//	    ImageIcon newIcon = new ImageIcon(this.rotateImageByDegrees(this.icon, (double) this.direction));
//	    this.setIcon(newIcon);
//	}
	this.setBounds(this.getAbsolutePosX(), this.getAbsolutePosY(), this.size.width, this.size.height);
    }

    private BufferedImage scaleImageToSize(BufferedImage image) {
	int width = image.getWidth();
	int height = image.getHeight();
	BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	AffineTransform transform = new AffineTransform();
	transform.scale(this.size.getWidth() / width, this.size.getHeight() / height);
	AffineTransformOp scaleOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	result = scaleOp.filter(image, result);

	return result;
    }

    private BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {
	double rads = Math.toRadians(angle);
	double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
	int w = img.getWidth();
	int h = img.getHeight();
	int newWidth = (int) Math.floor(w * cos + h * sin);
	int newHeight = (int) Math.floor(h * cos + w * sin);

	BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2d = rotated.createGraphics();
	AffineTransform at = new AffineTransform();
	at.translate((newWidth - w) / 2, (newHeight - h) / 2);

	int x = w / 2;
	int y = h / 2;

	at.rotate(rads, x, y);
	g2d.setTransform(at);
	g2d.drawImage(img, 0, 0, this);
	g2d.setColor(Color.RED);
	g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
	g2d.dispose();

	return rotated;
    }

    /**
     * Starting method of the entity and setting of its default values.
     */
    public void begin() {
	this.setStartingPosition();
	this.draw();
	this.speed = 0;
	this.life = 100;
    }

    /**
     * Kills the entity instantly.
     */
    public void die() {
	this.life = 0;
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
