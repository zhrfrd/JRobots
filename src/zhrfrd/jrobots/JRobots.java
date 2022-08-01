package zhrfrd.jrobots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JRobots extends JFrame implements ActionListener{
	private static final long serialVersionUID = -3190346657795484951L;
	static JPanel panel;
	static JPanel panelBattlefield;
	static JPanel panelSideMenu;
	static JPanel panelRobot1, panelRobot2, panelRobot3, panelRobot4;
	static JButton bttLoad1, bttLoad2, bttLoad3, bttLoad4;
	static JLabel labelRobot1Path, labelRobot2Path, labelRobot3Path, labelRobot4Path;
	static JLabel labelRobot1Life, labelRobot2Life, labelRobot3Life, labelRobot4Life;
	static JScrollPane scrollPane;
	static final int SCREEN_WIDTH = 900;
	static final int SCREEN_HEIGHT = SCREEN_WIDTH / 16 * 9;   // ASPECT RATIO 16:9
	static final int BATTLEFIELD_WIDTH = SCREEN_WIDTH - (SCREEN_WIDTH / 3);
	static final int BATTLEFIELD_HEIGHT = SCREEN_HEIGHT;
	static File fileRobot;
	static File fileIconRobot;
	private JFileChooser fileChooser;
	static BufferedImage bufferedImage;
	static ImageIcon imageIcon;
	
	public JRobots() {
		panel = new JPanel();
		panelBattlefield = new JPanel();
		panelSideMenu = new JPanel();
		panelRobot1 = new JPanel();
		panelRobot2 = new JPanel();
		panelRobot3 = new JPanel();
		panelRobot4 = new JPanel();
		labelRobot1Path = new JLabel("Path: ");
		labelRobot2Path = new JLabel("Path: ");
		labelRobot3Path = new JLabel("Path: ");
		labelRobot4Path = new JLabel("Path: ");
		labelRobot1Life = new JLabel("Life: ");
		labelRobot2Life = new JLabel("Life: ");
		labelRobot3Life = new JLabel("Life: ");
		labelRobot4Life = new JLabel("Life: ");
		bttLoad1 = new JButton("Load robot 1");
		bttLoad2 = new JButton("Load robot 2");
		bttLoad3 = new JButton("Load robot 3");
		bttLoad4 = new JButton("Load robot 4");
		
		bttLoad1.addActionListener(this);
		bttLoad2.addActionListener(this);
		bttLoad3.addActionListener(this);
		bttLoad4.addActionListener(this);
		
		organizeScreenLayout();
		
		panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel.setFocusable(true);
		panel.add(panelBattlefield);
		panel.add(scrollPane);
	}

	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		JRobots frame = new JRobots();
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		imageIcon = getIconRobot();
		
		Class<?> classRobot = Class.forName("zhrfrd.testjrobots.Test");   // Specify robot path
		Class<?> classRobot2 = Class.forName("zhrfrd.testjrobots.Test2");
		Constructor<?> constructorRobot = classRobot.getConstructor();   // Get the constructor of the class specified
		Constructor<?> constructorRobot2 = classRobot2.getConstructor();
//		Object objectRobot1 = constructorRobot.newInstance();   // Create a new instance of the object
//		Object objectRobot2 = constructorRobot2.newInstance();
//		Method method = classRobot.getDeclaredMethod("start", null);   // Get the method specified as parameter
//		method.invoke(objectRobot1, null);   // Invoke the method specified above
		
		Robot robot1 = (Robot) constructorRobot.newInstance();
		Robot robot2 = (Robot) constructorRobot2.newInstance();
		robot1.getWindowSize(BATTLEFIELD_WIDTH, BATTLEFIELD_HEIGHT);
		robot2.getWindowSize(BATTLEFIELD_WIDTH, BATTLEFIELD_HEIGHT);
		robot1.setIcon(imageIcon);
		robot2.setIcon(imageIcon);
 		robot1.threadRobot.start();
		robot2.threadRobot.start();
		panelBattlefield.add(robot1);
		panelBattlefield.add(robot2);
		
		String s = labelRobot1Path.toString();
		System.out.println(s);
	}
	
	// This function encloses the panels and their layout separately just for organisational purposes
	private void organizeScreenLayout () {
		panelRobot1.setLayout(new BoxLayout(panelRobot1, BoxLayout.Y_AXIS));
		panelRobot1.setBackground(Color.gray);
		panelRobot1.setMinimumSize(new Dimension(SCREEN_WIDTH - BATTLEFIELD_WIDTH, SCREEN_HEIGHT / 4));
		panelRobot1.setBorder(BorderFactory.createLoweredBevelBorder());
		panelRobot1.add(bttLoad1);
		panelRobot1.add(labelRobot1Path);
		panelRobot1.add(labelRobot1Life);
		
		panelRobot2.setLayout(new BoxLayout(panelRobot2, BoxLayout.Y_AXIS));
		panelRobot2.setBackground(Color.gray);
		panelRobot2.setPreferredSize(new Dimension(SCREEN_WIDTH - BATTLEFIELD_WIDTH, SCREEN_HEIGHT / 4));
		panelRobot2.setBorder(BorderFactory.createLoweredBevelBorder());
		panelRobot2.add(bttLoad2);
		panelRobot2.add(labelRobot2Path);
		panelRobot2.add(labelRobot2Life);
		
		panelRobot3.setLayout(new BoxLayout(panelRobot3, BoxLayout.Y_AXIS));
		panelRobot3.setBackground(Color.gray);
		panelRobot3.setPreferredSize(new Dimension(SCREEN_WIDTH - BATTLEFIELD_WIDTH, SCREEN_HEIGHT / 4));
		panelRobot3.setBorder(BorderFactory.createLoweredBevelBorder());
		panelRobot3.add(bttLoad3);
		panelRobot3.add(labelRobot3Path);
		panelRobot3.add(labelRobot3Life);
		
		panelRobot4.setLayout(new BoxLayout(panelRobot4, BoxLayout.Y_AXIS));
		panelRobot4.setBackground(Color.gray);
		panelRobot4.setPreferredSize(new Dimension(SCREEN_WIDTH - BATTLEFIELD_WIDTH, SCREEN_HEIGHT / 4));
		panelRobot4.setBorder(BorderFactory.createLoweredBevelBorder());
		panelRobot4.add(bttLoad4);
		panelRobot4.add(labelRobot4Path);
		panelRobot4.add(labelRobot4Life);
		
		panelBattlefield.setBackground(Color.black);
		panelBattlefield.setPreferredSize(new Dimension(BATTLEFIELD_WIDTH, BATTLEFIELD_HEIGHT));
		
		panelSideMenu.setLayout(new GridLayout(0,1));
		panelSideMenu.setBackground(Color.gray);
		panelSideMenu.add(panelRobot1);
		panelSideMenu.add(panelRobot2);
		panelSideMenu.add(panelRobot3);
		panelSideMenu.add(panelRobot4);
		
		scrollPane = new JScrollPane(panelSideMenu, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(SCREEN_WIDTH - BATTLEFIELD_WIDTH, SCREEN_HEIGHT));
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

	@Override
	public void actionPerformed (ActionEvent e) {
		if (e.getSource() == bttLoad1) {
			fileChooser = new JFileChooser();
			
			int response = fileChooser.showOpenDialog(null);
			
			if (response == JFileChooser.APPROVE_OPTION)
				fileRobot = new File("Path: " + fileChooser.getSelectedFile().getAbsolutePath());
			
			labelRobot1Path.setText(fileRobot.toString()); 
		}
		
		if (e.getSource() == bttLoad2) {
			fileChooser = new JFileChooser();
			
			int response = fileChooser.showOpenDialog(null);
			
			if (response == JFileChooser.APPROVE_OPTION)
				fileRobot = new File("Path: " + fileChooser.getSelectedFile().getAbsolutePath());
			
			labelRobot2Path.setText(fileRobot.toString()); 
		}
		
		if (e.getSource() == bttLoad3) {
			fileChooser = new JFileChooser();
			
			int response = fileChooser.showOpenDialog(null);
			
			if (response == JFileChooser.APPROVE_OPTION)
				fileRobot = new File("Path: " + fileChooser.getSelectedFile().getAbsolutePath());
			
			labelRobot3Path.setText(fileRobot.toString()); 
		}
		
		if (e.getSource() == bttLoad4) {
			fileChooser = new JFileChooser();
			
			int response = fileChooser.showOpenDialog(null);
			
			if (response == JFileChooser.APPROVE_OPTION)
				fileRobot = new File("Path: " + fileChooser.getSelectedFile().getAbsolutePath());
			
			labelRobot4Path.setText(fileRobot.toString()); 
		}
	}
}