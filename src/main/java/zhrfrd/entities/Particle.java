package zhrfrd.entities;

import java.awt.Color;

public class Particle extends Entity {
    private static final long serialVersionUID = 7596968145301880427L;
    Color color;
    int size;

    public Particle(double startX, double startY, Color color, int directionParticle) {
	this.color = color;
    }
    
    public void move(double posX, double posY) {
	this.posX = posX;
	this.posY = posY;
    }
    
    @Override
    protected void setStartingPosition() {
	// TODO Auto-generated method stub
	
    }
}
