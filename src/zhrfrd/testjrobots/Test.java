package zhrfrd.testjrobots;

import zhrfrd.jrobots.Robot;

public class Test extends Robot {
	private static final long serialVersionUID = -1551464491653620164L;

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