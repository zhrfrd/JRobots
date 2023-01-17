package zhrfrd.entities;

import java.awt.Color;

public class Particle extends Entity {
    private static final long serialVersionUID = 7596968145301880427L;
    Color color;
    int size;

<<<<<<< HEAD
    public Particle(double startX, double startY, Color color) {
=======
    public Particle(double startX, double startY, Color color, int directionParticle) {
>>>>>>> d9215388247c8214d9b1637491408cac8726ba29
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
