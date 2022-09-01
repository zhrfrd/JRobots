package zhrfrd.testjrobots;

import zhrfrd.entities.Robot;

public class Test extends Robot {
	public void start() {
		while (isAlive()) {
			move(45, 4);
		
//			shoot(3, 5);
		}
	}
	
	@Override
	public void boom() {
		System.out.println("FAI BOOOOOOOOOOOM!!!");
	}
}