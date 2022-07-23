package zhrfrd.jrobots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
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
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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
		
//		robot1.boom();
		robot1.threadRobot.start();
		robot2.threadRobot.start();
		panel.add(robot1);
		panel.add(robot2);
		
		Class<?> c = Class.forName("zhrfrd.jrobots.Robot");   //
		Constructor<?> cons = c.getConstructor();			  //
		Object object = cons.newInstance();					  // Till here it works, the class Robot is found and recognised
		System.out.println(object.getClass());
		Method method = c.getDeclaredMethod("boom", null);	  //
		method.invoke(object, null);						  //  Invoke specified method;
		Method [] methods = c.getMethods();					  //
		System.out.println(methods[1]);						  // Call method by position
		
//		robot1.goo();
		// Compile and execute external java program
		try {
			Process processCompilation = Runtime.getRuntime().exec("javac -d /Users/faridzouheir/eclipse-workspace/JRobots/src/ /Users/faridzouheir/eclipse-workspace/JRobots/src/zhrfrd/jrobots/Robot.java /Users/faridzouheir/eclipse-workspace/JRobots/src/zhrfrd/testjrobots/Test.java");   // Compile Test.java and Robot.Java
			processCompilation.waitFor();   // Wait until the process is terminated before starting the following process (to avoid the second process not working properly)
			Process processExecution = Runtime.getRuntime().exec("java -cp /Users/faridzouheir/eclipse-workspace/JRobots/src/ zhrfrd.testjrobots.Test");   // Execute java (-cp is class path)
			
			BufferedReader in = new BufferedReader(new InputStreamReader(processExecution.getInputStream()));   // Get result of the execution of the external file
			String line = null;
			
			while ((line = in.readLine()) != null) { 
	            System.out.println(line); 
			}
		} catch (IOException e) {  
            e.printStackTrace();
            System.out.println("Execution error.");
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Interrupted exception.");
		}
	}
}