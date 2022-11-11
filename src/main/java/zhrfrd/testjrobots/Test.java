package zhrfrd.testjrobots;

import java.io.IOException;

import zhrfrd.entities.Robot;

public class Test extends Robot {
    private static final long serialVersionUID = 2939127812037210694L;
    private boolean shot = false;

    public Test() throws IOException {
	super();
    }

    public void runTurn() {
	if (this.speed == 0) {
	    this.move(0, 2);
	} else {
	    if (this.direction == 0 && this.posX >= 90 && this.posY < 90) {
		this.move(90, 2);
	    } else if (this.direction == 90 && this.posX > 10 && this.posY >= 90) {
		this.move(180, 2);
	    } else if (this.direction == 180 && this.posX <= 10 && this.posY <= 10) {
		this.move(270, 2);
	    } else if (this.direction == 270 && this.posX < 90 && this.posY > 10) {
		this.move(0, 2);
	    }
	}

	if (this.posX >= 75 && this.posX <= 80) {
	    if (!this.shot) {
		try {
		    this.shoot(180, 60);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		this.shot = true;
	    }
	} else
	    this.shot = false;
    }
}