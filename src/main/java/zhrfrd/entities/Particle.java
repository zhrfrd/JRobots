package zhrfrd.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import zhrfrd.graphics.Screen;
import zhrfrd.graphics.Sprite;

public class Particle extends Entity {
    private static final long serialVersionUID = 7596968145301880427L;
    public List<Particle> particlesList = new ArrayList<>();
    public Sprite sprite = Sprite.particle_explosion;
    public int life;
    private double  directionX, directionY;
    private Random random = new Random();
    
    /**
     * Construct a basic Particle object.
     * @param xOrigin X position from where the particle is generated.
     * @param yOrigin Y position from where the particle is generated.
     * @param life Life span of the particle.
     */
    public Particle(double xOrigin, double yOrigin, int life) {
	posX = xOrigin;
	posY = yOrigin;
	this.life = life;
	
	directionX = random.nextGaussian();
	directionY = random.nextGaussian();
    }
    
    /**
     * Construct a Particle object by specifying also the amount of particles to be added to the particles array list.
     * @param xOrigin X position from where the particle is generated.
     * @param yOrigin Y position from where the particle is generated.
     * @param life Life span of the particle.
     * @param amount Number of particles that are going to be generated.
     */
    public Particle(double xOrigin, double yOrigin, int life, int amount) {
	this(xOrigin, yOrigin, life);
	
	for (int i = 0; i < amount - 1; i ++) {
	    particlesList.add(new Particle(xOrigin, yOrigin, life));
	}
    }
    
    /**
     * Update particle information in the game such as position in the battlefield.
     * @param screen The object Screen needs to be passed for future reference to the render method.
     */
    public void update(Screen screen) {
	posX += directionX;
	posY += directionY;
	life --;
	
//	render(screen);
    }
    
    /**
     * Render the particle to the screen.
     * @param screen The Screen object that will handle the rendering of the particle's sprite.
     */
    @Override
    public void render(Screen screen) {
	screen.renderSprite((int)posX, (int)posY, sprite);
    }

    @Override
    protected void setStartingPosition() {
    }

    @Override
    public void update() {
    }
}
