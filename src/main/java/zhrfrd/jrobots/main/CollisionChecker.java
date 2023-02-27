package zhrfrd.jrobots.main;

import zhrfrd.jrobots.entities.Robot;

public class CollisionChecker {
    Robot[] robots;
    
    /**
     * Constructor a collision checker.
     * @param robots Array of robots.
     */
    public CollisionChecker(Robot[] robots) {
	this.robots = robots;
    }
    
    /**
     * Detect collision between entities.
     * @param robots Array containing all the robots in the battlefield.
     * @return True if there is a collision, false otherwise.
     */
    protected boolean checkCollision(Robot[] robots) {
	for (int i = 0; i < robots.length; i ++) {
	    for (int j = 0; j < robots.length; j ++) {
		if (robots[i] == null || robots[j] == null || robots[i] == robots[j]) {
		    break;
		}
		
		else {
		    for (int k = 0; k < robots[j].missileList.size(); k ++) {
			if (robots[i].solidArea.intersects(robots[j].missileList.get(k).solidArea)) {
			    System.out.println("HIT!!!");
			    System.out.println(robots[i].getLife());
			    return true;
    		    	}
		    }
		}
	    }
	}
	
	return false;
    }
}
