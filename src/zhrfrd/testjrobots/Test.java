package zhrfrd.testjrobots;

import zhrfrd.entity.Robot;

public class Test extends Robot {
	public void start() {
		while (isAlive()) {
			move(RIGHT);
			
			shoot(3, 5);
		}
	}
}