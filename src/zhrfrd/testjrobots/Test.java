package zhrfrd.testjrobots;

import zhrfrd.entities.Robot;

public class Test extends Robot {
    public void runTurn() {
	if (posX != 100)
	    move(0, 2);
	
	if (posX == 100)
	    move(90, 4);
    }

    @Override
    public void boom() {
	System.out.println("FAI BOOOOOOOOOOOM!!!");
    }
}