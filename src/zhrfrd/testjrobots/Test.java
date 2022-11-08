package zhrfrd.testjrobots;

import zhrfrd.entities.Robot;
import zhrfrd.jrobots.JRobots;

public class Test extends Robot {
    public Test(JRobots jrobots) {
	super(jrobots);
	// TODO Auto-generated constructor stub
    }

    public void runTurn() {
	if (posX != 100)
	    move(0, 2);
	
	if (posX >= 75 && posX <= 80)
	    shoot(180, 60);
//	if (posX == 100)
//	    move(90, 4);
    }
}