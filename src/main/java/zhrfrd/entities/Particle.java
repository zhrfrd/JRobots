package zhrfrd.entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class Particle extends Entity {
    private static final long serialVersionUID = 7596968145301880427L;
    Color color;
    int size = 30;
    int directionX;
    int directionY;
    int life;

    public Particle(double startX, double startY, Color color, int directionX, int directionY, int maxLife) {
	this.color = color;
	this.directionX = directionX;
	this.directionY = directionY;
	this.life = maxLife;
    }
    
    public void move(int posX, int posY) {
	this.posX = posX;
	this.posY = posY;
    }
    
    public void update() {
	life --;
	
	if (life > 0)
	    move(directionX, directionY);
    }
    
    /**
     * Draw the particle on the map.
     */
    public void draw(Graphics2D g2) {
	g2.setColor(color);
	g2.fillRect((int)this.posX, (int)this.posY, size, size);
    }
	
    @Override
    protected void setStartingPosition() {
	// TODO Auto-generated method stub
	
    }
}
