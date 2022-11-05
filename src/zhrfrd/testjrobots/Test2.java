package zhrfrd.testjrobots;

import zhrfrd.entities.Robot;
import zhrfrd.jrobots.JRobots;

public class Test2 extends Robot {
    public Test2(JRobots jrobots) {
	super(jrobots);
	// TODO Auto-generated constructor stub
    }

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
