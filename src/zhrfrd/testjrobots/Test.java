package zhrfrd.testjrobots;

import zhrfrd.jrobots.Robot;

public class Test extends Robot {
	@Override
	public void start() {
		
			if (getPosX() <= 0)
				move(RIGHT);
			else
			move (LEFT);
	}
	
	@Override
	public void boom() {
		System.out.println("FAI BOOOOOOOOOOOM!!!");
	}
}