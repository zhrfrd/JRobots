package zhrfrd.jrobots.main;

import zhrfrd.jrobots.entities.Robot;

public class CollisionChecker {
    Robot[] robots;
    public CollisionChecker(Robot[] robots) {
	this.robots = robots;
    }
    
    /**
     * Detect collision between robots.
     * @param robots Array containing all the robots in the battlefield.
     * @return True if there is a collision, false otherwise.
     */
    protected boolean checkCollision(Robot[] robots) {
	for (int i = 0; i < robots.length; i ++) {
	    for (int j = 0; j < robots.length; j ++) {
		if (robots[i] == null || robots[j] == null) {
		    break;
		}
		
		else if (robots[i].solidArea.intersects(robots[j].solidArea) && robots[i] != robots[j]) {
		    System.out.println("HIT!");
		    System.out.println(robots[i].getLife());
		    return true;
		}
	    }
	}
	
	return false;
    }
}
