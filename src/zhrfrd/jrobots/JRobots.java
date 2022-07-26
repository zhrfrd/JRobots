package zhrfrd.jrobots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import zhrfrd.testjrobots.Test;

public class JRobots {
	static JFrame frame;
	static JPanel panel;
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static Robot robot1;
	static Robot robot2;
	static File fileRobot;
	static BufferedImage bufferedImage;
	static ImageIcon imageIcon;
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		frame = new JFrame("JRobots");
		panel = new JPanel();
		robot1 = new Test();
		robot2 = new Test();
		
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
		
		Class<?> classRobot = Class.forName("zhrfrd.testjrobots.Test");   // Specify robot path
		Constructor<?> constructorRobot = classRobot.getConstructor();   // Get the constructor of the class specified
		Object objectRobot = constructorRobot.newInstance();   // Create a new instance of the object
		Method method = classRobot.getDeclaredMethod("start", null);   // Get the method specified as parameter
		method.invoke(objectRobot, null);   // Invoke the method specified above
//		Field fieldThreadRobot = classRobot.getField("threadRobot");
		
		
	}
}