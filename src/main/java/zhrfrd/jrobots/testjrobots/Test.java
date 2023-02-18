package zhrfrd.jrobots.testjrobots;

import java.io.IOException;
import java.util.Random;

import zhrfrd.jrobots.entities.Robot;

public class Test extends Robot {
    private static final long serialVersionUID = 2939127812037210694L;
    private boolean shot = false;
    private boolean moved = false; 

    public Test() {
	super();
    }

    public void runTurn() {
	if (speed == 0) {
	    move(0, 5);
	}
	
	else if (direction == 0 && posX >= 390) {
	    move(180, 5);
	}
	
	else if (direction == 180 && posX <= 10) {
	    move(180, 5);
	}
//	if (speed == 0) {
//	    move(0, 5);
//	} 
//	
//	else if (direction == 0 && posX >= 90 && posY < 90) {
//	    move(90, 5);
//	} 
//    	    
//	else if (direction == 90 && posX > 10 && posY >= 90) {
//	    move(180, 5);
//	} 
//    	    
//	else if (direction == 180 && posX <= 10 && posY >= 90) {
//	    move(270, 5);
//	} 
//    	    
//	else if (direction == 270 && posX <= 10 && posY <= 10) {
//	    move(0, 5);
//	}

	Random r = new Random();

	try {
	    shoot(r.nextInt(360));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}