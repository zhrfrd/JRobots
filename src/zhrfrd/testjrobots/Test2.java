package zhrfrd.testjrobots;

import zhrfrd.jrobots.Robot;

public class Test2 extends Robot{
	public void start() {
		while (isAlive()) {
			move(UP);
		}
	}
}
