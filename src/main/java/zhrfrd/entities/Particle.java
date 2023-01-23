package zhrfrd.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import zhrfrd.graphics.Screen;
import zhrfrd.graphics.Sprite;

public class Particle extends Entity {
    private static final long serialVersionUID = 7596968145301880427L;
    Color color;
    int size = 30;
    int directionX;
    int directionY;
    public List<Particle> particlesList = new ArrayList<>();
    public Sprite sprite;
    private int life;
    protected double xx, yy, xa, ya;
    Random random = new Random();
    
    public Particle(int xOrigin, int yOrigin, int life) {
	sprite = Sprite.particle_explosion;
	this.posX = xOrigin;
	this.posY = yOrigin;
	this.xx = xOrigin;
	this.yy = yOrigin;
	this.life = life;
	sprite = Sprite.particle_explosion;
	
	this.xa = random.nextGaussian();
	this.ya = random.nextGaussian();
    }
    
    public Particle(int xOrigin, int yOrigin, int life, int amount) {
	this(xOrigin, yOrigin, life);
	
	for (int i = 0; i < amount - 1; i ++) 
	    particlesList.add(new Particle(xOrigin, yOrigin, life));
	
	particlesList.add(this);
    }

//    public Particle(double startX, double startY, Color color, int directionX, int directionY, int maxLife) {
//	this.color = color;
//	this.directionX = directionX;
//	this.directionY = directionY;
//	this.life = maxLife;
//    }
    
    public void update(Screen screen) {
	life --;
	
	if (life > 0)
	    move(directionX, directionY);
    }
    
    
    
    public void move(int posX, int posY) {
	this.posX = posX;
	this.posY = posY;
    }
	
    @Override
    protected void setStartingPosition() {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void update() {
	this.xx += xa;
	this.yy += ya;
    }
    
    @Override
    public void render(Screen screen) {
	screen.renderSprite((int)xx, (int)yy, Sprite.particle_explosion);
    }
}
