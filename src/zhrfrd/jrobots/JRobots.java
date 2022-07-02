package zhrfrd.jrobots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class JRobots {
	static JFrame frame;
	static JPanel panel;
//	static Robot label;
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static Robot robot1 = new Robot();
	static Robot robot2 = new Robot();
	static File robotFile;
	static BufferedImage bufferedImage;
	static ImageIcon imageIcon;
	
	public static void main (String[] args) throws IOException {
		robotFile = new File("/Users/faridzouheir/eclipse-workspace/JRobots/res/robot.png");
		bufferedImage = ImageIO.read(robotFile);
		imageIcon = new ImageIcon(bufferedImage);
//		label = new Robot();
//		label.setIcon(imageIcon);
		robot1 = new Robot();
		robot2 = new Robot();
		robot1.setIcon(imageIcon);
		robot2.setIcon(imageIcon);
		frame = new JFrame("JRobots");
		panel = new JPanel();
		
		panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		panel.setBackground(Color.black);
		panel.setLayout(new FlowLayout());   //or null
		panel.add(robot1);
		
		panel.setFocusable(true);
//		frame.getContentPane().add(new Robot());
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		//new GameFrame();
		robot1.t.start();
//		Dimension size = robot1.getPreferredSize();
//		robot1.setBounds(90, 100, size.width, size.height);
//		robot1.setVisible(true);
		
		robot2.t.start();
		panel.add(robot1);
		panel.add(robot2);
		System.out.println(robot1.getX());
	}
}


/*
package zhrfrd.jrobots;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class JRobots {
	static JFrame frame;
	static JPanel panel;
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static Robot robot1 = new Robot();
	static Robot robot2 = new Robot();
	
	public static void main (String[] args) {
		frame = new JFrame("JRobots");
		panel = new JPanel();
		
		panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		panel.setBackground(Color.black);
		panel.setFocusable(true);
		frame.getContentPane().add(new Robot());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		//new GameFrame();
		robot1.t.start();
		robot2.t.start();
	}
}
*/