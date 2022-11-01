package zhrfrd.jrobots;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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

import zhrfrd.entities.Robot;

public class JRobots extends JFrame implements ActionListener, Runnable {
    private static final long serialVersionUID = -3190346657795484951L;
    private JFileChooser fileChooser;
    private JPanel panelMain, panelBattleField, panelRightMenuContainer, panelStartController, panelRobotsContainer;
    private JButton buttonStart;
    private ArrayList<JPanel> panelRobot;
    private ArrayList<JButton> buttonsLoad;
    private ArrayList<JLabel> labelPathRobot;
    private ArrayList<JLabel> labelLifeRobot;
    private ArrayList<String> fullClassRobots;
    private ArrayList<Robot> robot;
    static File fileRobot;
    static BufferedReader fileReader;
    static BufferedImage bufferedImage;
    static ImageIcon imageIcon;
    static String firstLineFile = "";
    private Thread threadMain;
    boolean isBattleStarted = false;
    private static ImageIcon iconRobot;

    /**
     * Creates the layout of the battlefield with all the related components
     */
    public JRobots() {
	if (iconRobot == null) {
	    try {
		iconRobot = this.initializeRobotIcon();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}

	this.initializeLayout();
    }

    /**
     * JRobots Main
     * 
     * @param args
     */
    public static void main(String[] args) {
	JRobots frame = new JRobots();
	frame.start();
    }

    /**
     * Creates the layout with all depending objects
     */
    private void initializeLayout() {
	this.panelMain = new JPanel();
	Font font = this.panelMain.getFont().deriveFont(50);
	this.panelMain.setFont(font);

	this.panelBattleField = new JPanel();
	this.panelBattleField.setBackground(Color.black);

	this.panelRightMenuContainer = new JPanel();
	this.panelRightMenuContainer.setLayout(new BoxLayout(this.panelRightMenuContainer, BoxLayout.Y_AXIS));
	this.panelRightMenuContainer.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.red));

	this.panelRobotsContainer = new JPanel();
	this.panelRobotsContainer.setLayout(new GridLayout(0, 1));
	this.panelRobotsContainer.setBackground(Color.gray);

	this.buttonStart = new JButton("Start!");
	this.buttonStart.addActionListener(this);

	this.panelStartController = new JPanel();
	this.panelStartController.setBackground(Color.black);
	this.panelStartController.setLayout(new GridBagLayout());
	this.panelStartController.add(this.buttonStart);

	this.panelRobot = new ArrayList<JPanel>();
	this.buttonsLoad = new ArrayList<JButton>();
	this.labelPathRobot = new ArrayList<JLabel>();
	this.labelLifeRobot = new ArrayList<JLabel>();
	this.fullClassRobots = new ArrayList<String>();
	this.robot = new ArrayList<Robot>();

	for (int i = 0; i < 4; i++) {
	    JButton buttonLoad = new JButton("Load robot " + (i + 1));
	    buttonLoad.addActionListener(this);
	    JLabel labelPathRobot = new JLabel("Path: ");
	    JLabel labelLifeRobot = new JLabel("Life: ");
	    JPanel panelRobot = new JPanel();
	    panelRobot.setBackground(Color.gray);
	    panelRobot.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
	    panelRobot.add(buttonLoad);
	    panelRobot.add(labelPathRobot);
	    panelRobot.add(labelLifeRobot);
	    panelRobot.setLayout(new BoxLayout(panelRobot, BoxLayout.Y_AXIS));
	    buttonLoad.setAlignmentX(Component.CENTER_ALIGNMENT);
	    labelPathRobot.setAlignmentX(Component.CENTER_ALIGNMENT);
	    labelLifeRobot.setAlignmentX(Component.CENTER_ALIGNMENT);
	    this.buttonsLoad.add(buttonLoad);
	    this.labelPathRobot.add(labelPathRobot);
	    this.labelLifeRobot.add(labelLifeRobot);
	    this.panelRobot.add(panelRobot);
	    this.panelRobotsContainer.add(panelRobot);
	}

	this.panelRightMenuContainer.add(this.panelRobotsContainer);
	this.panelRightMenuContainer.add(this.panelStartController);

	this.panelMain.setLayout(new BoxLayout(this.panelMain, BoxLayout.X_AXIS));
	this.panelMain.setFocusable(true);
	this.panelMain.add(this.panelBattleField);
	this.panelMain.add(this.panelRightMenuContainer);

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension minimumSize = new Dimension(480, 270);

	this.setMinimumSize(minimumSize);
	this.setPreferredSize(new Dimension(screenSize.width * 8 / 10, screenSize.height * 8 / 10));
	this.panelRightMenuContainer.setMinimumSize(new Dimension(screenSize.width / 5, minimumSize.height));
	this.panelRightMenuContainer.setPreferredSize(new Dimension(screenSize.width / 5, screenSize.height));
	this.panelRightMenuContainer.setMaximumSize(new Dimension(screenSize.width / 5, screenSize.height));

	this.panelStartController.setMinimumSize(new Dimension(screenSize.width / 5, 100));
	this.panelStartController.setPreferredSize(new Dimension(screenSize.width / 5, 200));
	this.panelStartController.setMaximumSize(new Dimension(screenSize.width / 5, 200));

	this.add(this.panelMain);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(true);
	this.pack();
	this.setVisible(true);
	this.setLocationRelativeTo(null);
    }

    /*
     * Create and start the main thread
     */
    public void start() {
	threadMain = new Thread(this, "Thread main");
	threadMain.start(); // Go to run()
    }

    /*
     * Retrieve the icon of the robot from the selected path
     */
    private ImageIcon initializeRobotIcon() throws IOException {
	InputStream robotIconStream = getClass().getClassLoader().getResourceAsStream("robot.png");

	return new ImageIcon(ImageIO.read(robotIconStream));
    }

    /*
     * Load Robot file
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

	for (int i = 0; i < fullClassRobots.size(); i++) {
	    try {
		classRobot = Class.forName(fullClassRobots.get(i));
		constructorRobot = classRobot.getConstructor();
		System.out.println(constructorRobot);
		Robot newRobot = (Robot) constructorRobot.newInstance();
		newRobot.setIcon(iconRobot);
		newRobot.threadRobot.start();
		robot.add(newRobot);
	    } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
		    | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
		e1.printStackTrace();
	    }

	    panelBattleField.add(robot.get(i));
	}

	isBattleStarted = true;
    }

    /*
     * Get the full class name of the robot (eg:
     * packagefolder.subpackagefolder.Classname)
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
	StringBuffer strPackageRobot = new StringBuffer(firstLineFile).replace(0, index + 1, ""); // Extract ONLY
												  // package name from
												  // the first line of
												  // the file
	String className = fileRobot.getName().replace(".java", ""); // Get the class name removing the .java extension
								     // from the file name (for convention class name
								     // = to file name)
	String fullClass = (strPackageRobot + "." + className).replace(";", ""); // Merge package name and class name to
										 // create the full class name
										 // necessary for loading the robot

	return fullClass;
    }

    public void render() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == buttonsLoad.get(0)) {
//			loadRobot(labelPathRobot.get(0));
	    fullClassRobots.add("zhrfrd.testjrobots.Test");
	    labelPathRobot.get(0).setText("zhrfrd.testjrobots.Test");
	}

	if (e.getSource() == buttonsLoad.get(1))
	    loadRobot(labelPathRobot.get(1));

	if (e.getSource() == buttonsLoad.get(2))
	    loadRobot(labelPathRobot.get(2));

	if (e.getSource() == buttonsLoad.get(3))
	    loadRobot(labelPathRobot.get(3));

	if (e.getSource() == buttonStart)
	    startBattle();
    }

    @Override
    public void run() {
	while (true) {
	    if (isBattleStarted) {
		for (int i = 0; i < robot.size(); i++) {
		    labelLifeRobot.get(i).setText("Life: " + String.valueOf(robot.get(i).getLife()));
		}
	    }

	    try {
		Thread.sleep(10);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }
}