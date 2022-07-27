package zhrfrd.jrobots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JRobots {
	static JFrame frame;
	static JPanel panel;
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static File fileIconRobot;
	static BufferedImage bufferedImage;
	static ImageIcon imageIcon;
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		frame = new JFrame("JRobots");
		panel = new JPanel();
		
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
		
		imageIcon = getIconRobot();
		
		Class<?> classRobot = Class.forName("zhrfrd.testjrobots.Test");   // Specify robot path
		Constructor<?> constructorRobot = classRobot.getConstructor();   // Get the constructor of the class specified
		Object objectRobot = constructorRobot.newInstance();   // Create a new instance of the object
		Method method = classRobot.getDeclaredMethod("start", null);   // Get the method specified as parameter
		method.invoke(objectRobot, null);   // Invoke the method specified above
		
		Robot robot = (Robot) objectRobot;
		robot.setIcon(imageIcon);
		robot.threadRobot.start();
		panel.add(robot);
	}
	
	// Retrieve the icon of the robot from the selected path
	private static ImageIcon getIconRobot() {
		fileIconRobot = new File("/Users/faridzouheir/eclipse-workspace/JRobots/res/robot.png");
		try {
			bufferedImage = ImageIO.read(fileIconRobot);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ImageIcon(bufferedImage);
	}
}