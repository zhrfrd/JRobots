package zhrfrd.jrobots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	static JButton bttStart;
	static JLabel labelPathRobot1, labelPathRobot2, labelPathRobot3, labelPathRobot4;
	static JLabel labelLifeRobot1, labelLifeRobot2, labelLifeRobot3, labelLifeRobot4;
	static JScrollPane scrollPane;
	static final int SCREEN_WIDTH = 900;
	static final int SCREEN_HEIGHT = SCREEN_WIDTH / 16 * 9;   // ASPECT RATIO 16:9
	static final int BATTLEFIELD_WIDTH = SCREEN_WIDTH - (SCREEN_WIDTH / 3);
	static final int BATTLEFIELD_HEIGHT = SCREEN_HEIGHT;
	static File fileRobot;
	static File fileIconRobot;
	private JFileChooser fileChooser;
	static BufferedReader fileReader;
	static BufferedImage bufferedImage;
	static ImageIcon imageIcon;
	static String firstLineFile = "";
	
	/*
	 *  Constructor
	 */
	public JRobots() {
		panel = new JPanel();
		panelBattlefield = new JPanel();
		panelSideMenu = new JPanel();
		panelRobot1 = new JPanel();
		panelRobot2 = new JPanel();
		panelRobot3 = new JPanel();
		panelRobot4 = new JPanel();
		labelPathRobot1 = new JLabel("Path: ");
		labelPathRobot2 = new JLabel("Path: ");
		labelPathRobot3 = new JLabel("Path: ");
		labelPathRobot4 = new JLabel("Path: ");
		labelLifeRobot1 = new JLabel("Life: ");
		labelLifeRobot2 = new JLabel("Life: ");
		labelLifeRobot3 = new JLabel("Life: ");
		labelLifeRobot4 = new JLabel("Life: ");
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

	public static void main(String[] args) {
		JRobots frame = new JRobots();
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		imageIcon = getIconRobot();
	}
	
	/*
	 *  This function encloses the panels and their layout separately just for organisational purposes
	 */
	private void organizeScreenLayout() {
		panelRobot1.setLayout(new BoxLayout(panelRobot1, BoxLayout.Y_AXIS));
		panelRobot1.setBackground(Color.gray);
		panelRobot1.setMinimumSize(new Dimension(SCREEN_WIDTH - BATTLEFIELD_WIDTH, SCREEN_HEIGHT / 4));
		panelRobot1.setBorder(BorderFactory.createLoweredBevelBorder());
		panelRobot1.add(bttLoad1);
		panelRobot1.add(labelPathRobot1);
		panelRobot1.add(labelLifeRobot1);
		
		panelRobot2.setLayout(new BoxLayout(panelRobot2, BoxLayout.Y_AXIS));
		panelRobot2.setBackground(Color.gray);
		panelRobot2.setPreferredSize(new Dimension(SCREEN_WIDTH - BATTLEFIELD_WIDTH, SCREEN_HEIGHT / 4));
		panelRobot2.setBorder(BorderFactory.createLoweredBevelBorder());
		panelRobot2.add(bttLoad2);
		panelRobot2.add(labelPathRobot2);
		panelRobot2.add(labelLifeRobot2);
		
		panelRobot3.setLayout(new BoxLayout(panelRobot3, BoxLayout.Y_AXIS));
		panelRobot3.setBackground(Color.gray);
		panelRobot3.setPreferredSize(new Dimension(SCREEN_WIDTH - BATTLEFIELD_WIDTH, SCREEN_HEIGHT / 4));
		panelRobot3.setBorder(BorderFactory.createLoweredBevelBorder());
		panelRobot3.add(bttLoad3);
		panelRobot3.add(labelPathRobot3);
		panelRobot3.add(labelLifeRobot3);
		
		panelRobot4.setLayout(new BoxLayout(panelRobot4, BoxLayout.Y_AXIS));
		panelRobot4.setBackground(Color.gray);
		panelRobot4.setPreferredSize(new Dimension(SCREEN_WIDTH - BATTLEFIELD_WIDTH, SCREEN_HEIGHT / 4));
		panelRobot4.setBorder(BorderFactory.createLoweredBevelBorder());
		panelRobot4.add(bttLoad4);
		panelRobot4.add(labelPathRobot4);
		panelRobot4.add(labelLifeRobot4);
		
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
	
	/*
	 *  Retrieve the icon of the robot from the selected path
	 */
	private static ImageIcon getIconRobot() {
		fileIconRobot = new File("res/robot.png");
		try {
			bufferedImage = ImageIO.read(fileIconRobot);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ImageIcon(bufferedImage);
	}
	
	/*
	 *  Load Robot file and run it
	 */
	private void loadRobot(JLabel labelPathRobot, Class<?> classRobot, Robot robot) {
		fileChooser = new JFileChooser();
		int response = fileChooser.showOpenDialog(null);
		String fullClass = "";
		Constructor<?> constructorRobot;
		
		if (response == JFileChooser.APPROVE_OPTION)
			fileRobot = new File(fileChooser.getSelectedFile().getAbsolutePath());
		
		fullClass = extractFullClassRobot();
		labelPathRobot.setText(fullClass);
		
		try {
			classRobot = Class.forName(fullClass);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		};
		
		try {
			constructorRobot = classRobot.getConstructor();
			robot = (Robot) constructorRobot.newInstance();
			robot.getWindowSize(BATTLEFIELD_WIDTH, BATTLEFIELD_HEIGHT);
			robot.setIcon(imageIcon);
			robot.threadRobot.start();
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		
		panelBattlefield.add(robot);
	}

	/*
	 * Get the full class name of the robot (eg: packagefolder.subpackagefolder.Classname)
	 */
	protected String extractFullClassRobot() {
		// Read the first line of the file 
		try {
			fileReader = new BufferedReader(new FileReader(fileRobot));
			firstLineFile = fileReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int index = firstLineFile.indexOf(" ");
		StringBuffer strPackageRobot = new StringBuffer(firstLineFile).replace(0, index + 1, "");   // Extract ONLY package name from the first line of the file
		String className = fileRobot.getName().replace(".java", "");   // Get the class name removing the .java extension from the file name (for convention class name = to file name)
		String fullClass = (strPackageRobot + "." + className).replace(";", "");   // Merge package name and class name to create the full class name necessary for loading the robot
		
		return fullClass;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bttLoad1) {
			Class<?> classRobot1 = null;
			Robot robot1 = null;
			
			loadRobot(labelPathRobot1, classRobot1, robot1);
		}
		
		if (e.getSource() == bttLoad2) {
			Class<?> classRobot2 = null;
			Robot robot2 = null;
			
			loadRobot(labelPathRobot2, classRobot2, robot2);
		}
		
		if (e.getSource() == bttLoad3) {
			Class<?> classRobot3 = null;
			Robot robot3 = null;
			
			loadRobot(labelPathRobot3, classRobot3, robot3);
		}
		
		if (e.getSource() == bttLoad4) {
			Class<?> classRobot4 = null;
			Robot robot4 = null;
			
			loadRobot(labelPathRobot4, classRobot4, robot4);
		}
	}
}