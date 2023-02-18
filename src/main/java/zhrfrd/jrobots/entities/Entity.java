package zhrfrd.jrobots.entities;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JLabel;

import zhrfrd.jrobots.graphics.Screen;

public abstract class Entity extends JLabel {
    private static final long serialVersionUID = -2678484620304652158L;
    /**
     * Current entity position
     */
    protected double posX, posY;
    protected final int BOOST = 3;
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

    private boolean removed = false;

    public Entity() {
    }
    
    public void remove() {
	removed = true;
    }
    
    public boolean isRemoved() {
	return removed;
    }

    /**
     * Get the X position of the entity in percentage to the field width.
     */
    public double getPosX() {
	return posX;
    }

    /**
     * Get the Y position of the entity in percentage to the field height.
     */
    public double getPosY() {
	return posY;
    }

    /**
     * Get the current entity life.
     */
    public int getLife() {
	return life;
    }

    /**
     * Get the current entity direction.
     */
    public int getDirection() {
	return direction;
    }

    /**
     * Get the current entity speed.
     */
    public int getSpeed() {
	return speed;
    }

//    private BufferedImage scaleImageToSize(BufferedImage image) {
//	int width = image.getWidth();
//	int height = image.getHeight();
//	BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//	AffineTransform transform = new AffineTransform();
//	transform.scale(this.size.getWidth() / width, this.size.getHeight() / height);
//	AffineTransformOp scaleOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
//	result = scaleOp.filter(image, result);
//
//	return result;
//    }
//
//    private BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {
//	double rads = Math.toRadians(angle);
//	double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
//	int w = img.getWidth();
//	int h = img.getHeight();
//	int newWidth = (int) Math.floor(w * cos + h * sin);
//	int newHeight = (int) Math.floor(h * cos + w * sin);
//
//	BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
//	Graphics2D g2d = rotated.createGraphics();
//	AffineTransform at = new AffineTransform();
//	at.translate((newWidth - w) / 2, (newHeight - h) / 2);
//
//	int x = w / 2;
//	int y = h / 2;
//
//	at.rotate(rads, x, y);
//	g2d.setTransform(at);
//	g2d.drawImage(img, 0, 0, this);
//	g2d.setColor(Color.RED);
//	g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
//	g2d.dispose();
//
//	return rotated;
//    }

    /**
     * Kills the entity instantly.
     */
    public void die() {
	this.life = 0;
    }

    /**
     * Check if the entity is alive.
     * @return boolean
     */
    public final boolean isAlive() {
	if (this.life <= 0) {
	    return false;
	}

	return true;
    }
    
    public abstract void update();
    
    public abstract void render(Screen screen);
    
    protected abstract void setStartingPosition();
}
