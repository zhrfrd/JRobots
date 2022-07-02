package zhrfrd.jrobots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JRobots {
	static JFrame frame;
	static JPanel panel;
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static Robot robot1 = new Robot();
	static Robot robot2 = new Robot();
	static File fileRobot;
	static BufferedImage bufferedImage;
	static ImageIcon imageIcon;
	
	public static void main (String[] args) throws IOException {
		frame = new JFrame("JRobots");
		panel = new JPanel();
		robot1 = new Robot();
		robot2 = new Robot();
		fileRobot = new File("/Users/faridzouheir/eclipse-workspace/JRobots/res/robot.png");
		bufferedImage = ImageIO.read(fileRobot);
		imageIcon = new ImageIcon(bufferedImage);
		robot1.setIcon(imageIcon);
		robot2.setIcon(imageIcon);
		
		panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		panel.setBackground(Color.black);
		panel.setLayout(null);   // Or new FlowLayout()??
		panel.setFocusable(true);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		robot1.threadRobot.start();
		robot2.threadRobot.start();
		panel.add(robot1);
		panel.add(robot2);
		System.out.println(robot1.getX());
	}
}