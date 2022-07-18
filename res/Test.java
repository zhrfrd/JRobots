package zhrfrd.jrobots;

import zhrfrd.jrobots.Robot;

public class Test {
	public static void main(String[] args) {
        int a = 300;
        int b = 23;
        int res = doSum(a, b);
        System.out.println(res);
        Robot rob = new Robot();
        rob.boom();
    }

    public static int doSum(int a, int b) {
        return a + b;
    }
}