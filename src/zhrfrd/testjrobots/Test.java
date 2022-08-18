package zhrfrd.testjrobots;

import zhrfrd.entities.Robot;

public class Test extends Robot {
	public void start() {
		while (isAlive()) {
			move(RIGHT);
			
			shoot(3, 5);
		}
	}
	
	@Override
	public void boom() {
		System.out.println("FAI BOOOOOOOOOOOM!!!");
	}
}