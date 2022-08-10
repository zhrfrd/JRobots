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
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

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

public class JRobots extends JFrame implements ActionListener, Runnable{
	private static final long serialVersionUID = -3190346657795484951L;
	private JFileChooser fileChooser;
	static JPanel panel;
	static JPanel panelBattleContainer, panelBattlefield,panelSideMenu, panelController;
	static JButton bttStart;
	static ArrayList<JPanel> panelRobot;
	static ArrayList<JButton> bttLoad;
	static ArrayList<JLabel> labelPathRobot;
	static ArrayList<JLabel> labelLifeRobot;
	static ArrayList<String> fullClassRobots;
	static ArrayList<Robot> robot;
	static JScrollPane scrollPane;
	static final int SCREEN_WIDTH = 900;
	static final int SCREEN_HEIGHT = SCREEN_WIDTH / 16 * 10;   // ASPECT RATIO 16:10
	static final int BATTLECONTAINER_WIDTH = SCREEN_WIDTH - (SCREEN_WIDTH / 3);
	static final int BATTLECONTAINER_HEIGHT = SCREEN_HEIGHT;
	static final int BATTLEFIELD_WIDTH = BATTLECONTAINER_WIDTH;
	static final int BATTLEFIELD_HEIGHT = BATTLECONTAINER_HEIGHT - (BATTLECONTAINER_HEIGHT / 10);
	static File fileRobot;
	static File fileIconRobot;
	static BufferedReader fileReader;
	static BufferedImage bufferedImage;
	static ImageIcon imageIcon;
	static String firstLineFile = "";
	static Thread threadMain;
	boolean isBattleStarted = false;
	
	/*
	 *  Constructor
	 */
	public JRobots() {
		panel = new JPanel();
		panelRobot = new ArrayList<JPanel>();
		bttLoad = new ArrayList<JButton>();
		labelPathRobot = new ArrayList<JLabel>();
		labelLifeRobot = new ArrayList<JLabel>();
		fullClassRobots = new ArrayList<String>();
		robot = new ArrayList<Robot>();
		panelBattleContainer = new JPanel();
		panelBattlefield = new JPanel();
		panelSideMenu = new JPanel();
		panelController = new JPanel();
		
		for (int i = 0; i < 4; i ++) {
			panelRobot.add(new JPanel());
			labelPathRobot.add(new JLabel("Path: "));
			labelLifeRobot.add(new JLabel("Life: "));
			bttLoad.add(new JButton("Load robot " + (i + 1)));
			bttLoad.get(i).addActionListener(this);
		}
		
		bttStart = new JButton("Start!");
		bttStart.addActionListener(this);
		
		organizeScreenLayout();
		
		panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel.setFocusable(true);
		panel.add(panelBattleContainer);
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
		
		frame.start();   // Go to start() function
	}
	
	/*
	 * Create and start the main thread
	 */
	public void start() {
		threadMain = new Thread(this, "Thread main");
		threadMain.start();   // Go to run()
	}
	
	/*
	 *  This function encloses the panels and their layout separately just for organisational purposes
	 */
	private void organizeScreenLayout() {
		for (int i = 0; i < 4; i ++) {
			panelRobot.get(i).setLayout(new BoxLayout(panelRobot.get(i), BoxLayout.Y_AXIS));
			panelRobot.get(i).setBackground(Color.gray);
			panelRobot.get(i).setMinimumSize(new Dimension(SCREEN_WIDTH - BATTLEFIELD_WIDTH, SCREEN_HEIGHT / 4));
			panelRobot.get(i).setBorder(BorderFactory.createLoweredBevelBorder());
			panelRobot.get(i).add(bttLoad.get(i));
			panelRobot.get(i).add(labelPathRobot.get(i));
			panelRobot.get(i).add(labelLifeRobot.get(i));
		}
		
		panelBattleContainer.setPreferredSize(new Dimension(BATTLECONTAINER_WIDTH, BATTLECONTAINER_HEIGHT));
		panelBattleContainer.setLayout(new BoxLayout(panelBattleContainer, BoxLayout.Y_AXIS));
		panelBattleContainer.add(panelBattlefield);
		panelBattleContainer.add(panelController);
		
		panelBattlefield.setBackground(Color.black);
		panelBattlefield.setPreferredSize(new Dimension(BATTLEFIELD_WIDTH, BATTLEFIELD_HEIGHT));
		
		panelController.setPreferredSize(new Dimension(BATTLEFIELD_WIDTH, BATTLEFIELD_HEIGHT / 10));
		panelController.setBackground(Color.black);
		panelController.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.white));   // Add top white border
		panelController.add(bttStart);
		
		panelSideMenu.setLayout(new GridLayout(0,1));
		panelSideMenu.setBackground(Color.gray);
		
		for (int i = 0; i < 4; i ++) {
			panelSideMenu.add(panelRobot.get(i));
		}
		
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
	 *  Load Robot file
	 */
	private void loadRobot(JLabel labelPathRobot) {
		fileChooser = new JFileChooser();
		int response = fileChooser.showOpenDialog(null);
		String fullClass = "";
		
		if (response == JFileChooser.APPROVE_OPTION)
			fileRobot = new File(fileChooser.getSelectedFile().getAbsolutePath());
		
		fullClass = extractFullClassRobot();
		fullClassRobots.add(fullClass);
		labelPathRobot.setText(fullClass);
	}
	
	/*
	 * Start the battle by getting the full class of each robot
	 */
	private void startBattle() {
		Class<?> classRobot = null;
		Constructor<?> constructorRobot;
		
		for (int i = 0; i < fullClassRobots.size(); i ++) {
			try {
				classRobot = Class.forName(fullClassRobots.get(i));
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			};
			
			try {
				constructorRobot = classRobot.getConstructor();
				robot.add((Robot) constructorRobot.newInstance());
				robot.get(i).getWindowSize(BATTLEFIELD_WIDTH, BATTLEFIELD_HEIGHT);
				robot.get(i).setIcon(imageIcon);
				robot.get(i).threadRobot.start();
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
			
			panelBattlefield.add(robot.get(i));
		}
		
		isBattleStarted = true;
	}

	/*
	 * Get the full class name of the robot (eg: packagefolder.subpackagefolder.Classname)
	 */
	private String extractFullClassRobot() {
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
		if (e.getSource() == bttLoad.get(0))
			loadRobot(labelPathRobot.get(0));
		
		if (e.getSource() == bttLoad.get(1))
			loadRobot(labelPathRobot.get(1));
		
		if (e.getSource() == bttLoad.get(2))
			loadRobot(labelPathRobot.get(2));
		
		if (e.getSource() == bttLoad.get(3))
			loadRobot(labelPathRobot.get(3));
		
		if (e.getSource() == bttStart) {
			startBattle();
		}
	}

	@Override
	public void run () {
		while (true) {
			if (isBattleStarted) {
				for (int i = 0; i < robot.size(); i ++) 
					labelLifeRobot.get(i).setText(String.valueOf(robot.get(i).life()));
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}