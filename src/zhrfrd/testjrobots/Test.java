package zhrfrd.testjrobots;

import zhrfrd.jrobots.Robot;

public class Test extends Robot {
	public void start() {
		while (isAlive()) {
			move(RIGHT);
		
			
		}
	}
	
	@Override
	public void boom() {
		System.out.println("FAI BOOOOOOOOOOOM!!!");
	}
}