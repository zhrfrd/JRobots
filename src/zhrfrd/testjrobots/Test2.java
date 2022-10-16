package zhrfrd.testjrobots;

import zhrfrd.entities.Robot;

public class Test2 extends Robot {
    public void start() {
//		while (isAlive()) {
//			move(UP);
//		}
	System.out.println(scan(90));
    }

    @Override
    protected void runTurn() {
	// TODO Auto-generated method stub

    }
}
