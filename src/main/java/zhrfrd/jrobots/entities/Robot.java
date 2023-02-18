package zhrfrd.jrobots.entities;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import zhrfrd.jrobots.graphics.Screen;
import zhrfrd.jrobots.graphics.Sprite;

public abstract class Robot extends Entity {
    private static final long serialVersionUID = -2377133046121834448L;
    /**
     * Determine if the move action has been done in the current turn.
     */
    private boolean hasMoved = false;
//    private int life = 100;
    private Missile missile; 
    public ArrayList<Missile> missileList;
    public Particle particle;
    private boolean isRobotStarted = false;
    private boolean isMissileShot = false;
    private int missileLifeCounter = 0;
    private int missileLifeSpan = 120;
    public boolean isMissileExploded = false;
    public boolean isParticleGenerated = false;
    public Rectangle solidArea = new Rectangle((int)posX, (int)posY, 16, 16);;   // Default solid area for the collision detection
    
    /**
     * Set robot starting position.
     */
    protected final void setStartingPosition() {
	Random random = new Random();
	posX = random.nextInt(400);
//	posY = random.nextInt(100);
	posY = 5;
	
	life = 100;
    }

    /**
     * Move the robot.
     * 
     * @param direction The direction in degrees where the robot is going to move.
     * @param speed     The speed at which the robot travels.
     */
    public void move(int direction, int speed) {
	if (hasMoved) {
	    return;
	}

	hasMoved = true;
	this.direction = direction;
	this.speed = Math.max(0, Math.min(5, speed)) * BOOST;
	int radians = (int)Math.toRadians(direction);
	int x = (int)(Math.cos(radians) * 0.1 * this.speed);
	int y = (int)(Math.sin(radians) * 0.1 * this.speed);

	// Adjust direction of the robot
	// TODO: if the movement is limited by x then y should be limited as well but at
	// the moment this is not taken into consideration
	int newPosX = (int)posX + x;
	int newPosY = (int)posY + y;

	posX = (int)Math.max(0, Math.min(400, newPosX));
	posY = (int)Math.max(0, Math.min(400, newPosY));

	if (newPosX < 0 || newPosX > 400 || newPosY < 0 || newPosY > 400) {
	    inflictWallsDamage();
	    this.speed = 0;
	}
	
	
	
    }
    
    /**
     * Shot a missile towards the direction specified
     * @param direction The direction towards where the missile is shot
     * @throws IOException
     */
    public final void shoot(int direction) throws IOException {
	if (missileList != null && missileList.size() > 0) {
	    return;
	}
	
	if (!isMissileShot) {
	    isMissileShot = true;
	
            missileList = new ArrayList<Missile>();
            missile = new Missile(this, direction);
        	
            missileList.add(missile);
            missile.begin();
	}
    }

    /**
     * Inflict walls damage depending on the current speed of the robot.
     */
    private void inflictWallsDamage() {
	inflictDamage(speed);
    }

    /**
     * Inflicts the amount of damage specified to the robot
     * @param value the amount of damage to inflict. Absolute value is used
     */
    private void inflictDamage(int value) {
	life = Math.max(0, Math.min(100, life - Math.abs(value)));
    }

    /*
     * Scan the canvasBattle towards a single line direction
     */
    public final int scan(int direction) {
	if (enemyFound()) {
	    return direction;
	}

	return 0;
    }

    /*
     * Scan the battleground towards the specified direction +/- the resolution
     */
    public final int scan(int direction, int resolution) {
	if (enemyFound()) {
	    return direction;
	}

	return 0;
    }

    /**
     * Check if there is an enemy along the direction your robot is pointing.
     */
    public boolean enemyFound() {
	// If yes return true, else return false
	return false;
    }

    /**
     * Check if some missile is dead and, if yes, remove it from the canvasBattle.
     */
    public void cleanMissiles() {
	if (missileList == null || missileList.size() == 0) {
	    return;
	}

	int i = 0;

	while (i < missileList.size()) {
	    if (missile != null) {
		missileList.remove(i);
	    } 
	    
	    else {
		i++;
	    }
	}
    }
    
    /**
     * Update missile information in the game.
     */
    @Override
    public void update() {
	if (!isRobotStarted) {
	    setStartingPosition();
	    isRobotStarted = true;
	}
	
	hasMoved = false;
	runTurn();
    	move(direction, speed);
    	solidArea.x = (int)posX;
    	solidArea.y = (int)posY;
    	
    	// Update missile status only if it exists in the current cycle until its life cycle terminates
    	if (missileList != null && missileList.size() > 0) {
    	    missileLifeCounter ++;
    	    
    	    if (missileLifeCounter >= missileLifeSpan || missile.getPosX() <= 0 || missile.getPosX() >= 400 || missile.getPosY() <= 0 || missile.getPosY() >= 400) {
    		isMissileExploded = true;
    	    	missileLifeCounter = 0;
    	    	isMissileShot = false;
    	    	isParticleGenerated = true;
    		
    		cleanMissiles();
    		particle = new Particle(missile.getPosX(), missile.getPosY(), 50, 50);
    	    }
    	}
    }
    
    /**
     * Render the robot to the battlefield.
     * @param screen Screen that handles the rendering of the robot.
     */
    @Override
    public void render(Screen screen) {
	screen.renderEntity((int)posX,  (int)posY,  Sprite.robot1);
    }
    
    abstract protected void runTurn();
}