package zhrfrd.jrobots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JRobots{
	static JFrame frame;
	static JPanel panel;
	static JButton bttLoad;
	static JFileChooser fileChooser;
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static File fileIconRobot;
	static BufferedImage bufferedImage;
	static ImageIcon imageIcon;
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		frame = new JFrame("JRobots");
		panel = new JPanel();
		bttLoad = new JButton("Load robot");
		
		panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		panel.setBackground(Color.black);
//		panel.setLayout(null);   // Or new FlowLayout()??
		panel.setFocusable(true);
		panel.add(bttLoad);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		bttLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				if (e.getSource() == bttLoad) {
					fileChooser = new JFileChooser();
					
					int response = fileChooser.showOpenDialog(null);
					
					if (response == JFileChooser.APPROVE_OPTION) {
						File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
						System.out.println(file);
					}
				}
			}
		});
		
		imageIcon = getIconRobot();
		
		Class<?> classRobot = Class.forName("zhrfrd.testjrobots.Test");   // Specify robot path
		Class<?> classRobot2 = Class.forName("zhrfrd.testjrobots.Test2");
		Constructor<?> constructorRobot = classRobot.getConstructor();   // Get the constructor of the class specified
		Constructor<?> constructorRobot2 = classRobot2.getConstructor();
		Object objectRobot1 = constructorRobot.newInstance();   // Create a new instance of the object
		Object objectRobot2 = constructorRobot2.newInstance();
//		Method method = classRobot.getDeclaredMethod("start", null);   // Get the method specified as parameter
//		method.invoke(objectRobot1, null);   // Invoke the method specified above
		
		Robot robot1 = (Robot) constructorRobot.newInstance();
		Robot robot2 = (Robot) constructorRobot2.newInstance();
		robot1.getWindowSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		robot2.getWindowSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		robot1.setIcon(imageIcon);
		robot2.setIcon(imageIcon);
 		robot1.threadRobot.start();
		robot2.threadRobot.start();
		panel.add(robot1);
		panel.add(robot2);
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