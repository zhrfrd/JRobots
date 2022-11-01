package zhrfrd.testjrobots;

import zhrfrd.entities.Robot;

public class Test extends Robot {
    private static final long serialVersionUID = -3948041885212023909L;

    public void runTurn() {
	if (this.speed == 0) {
	    this.move(0, 2);
	} else {
	    if (this.direction == 0 && this.posX > 90) {
		this.move(180, 2);
	    } else if (this.direction == 180 && this.posX < 10) {
		this.move(0, 2);
	    }
	}
    }

    @Override
    public void boom() {
	System.out.println("FAI BOOOOOOOOOOOM!!!");
    }
}