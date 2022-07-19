package zhrfrd.jrobotss;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import zhrfrd.jrobots.Robot;

public class Test {
	static Robot rob;
    
	public static void main(String[] args) {
        int a = 300;
        int b = 23;
        int res = doSum(a, b);
        JFrame frame = new JFrame("boo");
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(100, 100));
		panel.setLayout(null);   // Or new FlowLayout()??
		panel.setFocusable(true);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
        System.out.println(res);
        // boom();
        rob = new Robot();
        rob.boom();
	}

    public static int doSum(int a, int b) {
        return a + b;
    }
}