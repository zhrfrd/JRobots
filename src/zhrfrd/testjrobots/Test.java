package zhrfrd.testjrobots;

import zhrfrd.jrobots.Robot;

public class Test extends Robot {
	@Override
	public void start() {
		
			boom();
	}
	
	@Override
	public void boom() {
		System.out.println("FAI BOOOOOOOOOOOM!!!");
	}
}