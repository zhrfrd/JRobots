package zhrfrd.testjrobots;

import zhrfrd.jrobots.Robot;

public class Test2 extends Robot{
	private static final long serialVersionUID = -4189990095839304855L;

	public void start() {
		while (isAlive()) {
			move(UP);
		}
	}
}
