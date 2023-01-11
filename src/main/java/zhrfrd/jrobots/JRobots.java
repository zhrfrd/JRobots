package zhrfrd.jrobots;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import zhrfrd.entities.Missile;
import zhrfrd.entities.Robot;

public class JRobots extends JFrame implements ActionListener, Runnable {
    private static final long serialVersionUID = -3190346657795484951L;
    private static JRobots frame;
    private JFileChooser fileChooser;
    private JPanel panelMain, panelRightMenuContainer, panelStartController, panelRobotsContainer;
    public JPanel panelBattleField;
    private JButton buttonStart;
    private JButton buttonPause;
    private JButton buttonReset;
    private JButton buttonRestart;
    private ArrayList<JPanel> panelRobot;
    private ArrayList<JButton> buttonsLoad;
    private ArrayList<JLabel> labelPathRobot;
    private ArrayList<JLabel> labelLifeRobot;
    private ArrayList<String> fullClassRobots;
//    private ArrayList<Robot> robot;
    final int FPS = 60;
    final int MAX_ROBOTS = 4;
    private Robot robot[] = new Robot[MAX_ROBOTS];
    private ArrayList<Missile> missileList = new ArrayList<>();
    static File fileRobot;
    static BufferedReader fileReader;
    static BufferedImage bufferedImage;
    static ImageIcon imageIcon;
    static String firstLineFile = "";
    private Thread threadMain;
    private boolean isBattleStopped = false;

    /**
     * Creates the layout of the battlefield with all the related components
     */
    public JRobots() {
	this.initializeLayout();
    }

    /**
     * JRobots Main
     * 
     * @param args
     */
    public static void main(String[] args) {
	frame = new JRobots();
    }

    /**
     * Create the layout of the game's window by positioning all the components in their position
     */
    private void initializeLayout() {
	this.panelMain = new JPanel();
	Font font = this.panelMain.getFont().deriveFont(50);
	this.panelMain.setFont(font);

	this.panelBattleField = new JPanel();
	this.panelBattleField.setBackground(new Color(153, 102, 51));

	this.panelRightMenuContainer = new JPanel();
	this.panelRightMenuContainer.setLayout(new BoxLayout(this.panelRightMenuContainer, BoxLayout.Y_AXIS));
	this.panelRightMenuContainer.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.red));

	this.panelRobotsContainer = new JPanel();
	this.panelRobotsContainer.setLayout(new GridLayout(0, 1));
	this.panelRobotsContainer.setBackground(Color.gray);

	this.buttonStart = new JButton("Start!");
	this.buttonStart.addActionListener(this);
	this.buttonPause = new JButton("Pause");
	this.buttonPause.addActionListener(this);
	this.buttonReset = new JButton("Reset");
	this.buttonReset.addActionListener(this);
	this.buttonRestart = new JButton("Restart");
	this.buttonRestart.addActionListener(this);

	this.panelStartController = new JPanel();
	this.panelStartController.setBackground(Color.black);
	this.panelStartController.setLayout(new GridLayout(4, 0));
	this.panelStartController.add(this.buttonStart);
	this.panelStartController.add(this.buttonPause);
	this.panelStartController.add(this.buttonReset);
	this.panelStartController.add(this.buttonRestart);

	this.panelRobot = new ArrayList<JPanel>();
	this.buttonsLoad = new ArrayList<JButton>();
	this.labelPathRobot = new ArrayList<JLabel>();
	this.labelLifeRobot = new ArrayList<JLabel>();
	this.fullClassRobots = new ArrayList<String>();

	for (int i = 0; i < MAX_ROBOTS; i++) {
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

    /**
     * Create and start the main thread when the user presses the Start! button.
     */
    public void start() {
	threadMain = new Thread(this, "Thread main");
	threadMain.start(); // Go to run()
    }

    /**
     * Upload the robot from a folder and load it to the game by adding the class
     * name of the robot to its specific JLabel.
     * 
     * @param labelPathRobot The JLabel that will contain the class name of the
     *                       robot created by the user.
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

    /**
     * Start the battle by getting the full class of each robot and creating a new
     * instance of Robot passing JRobots as parameter.
     */
    private void startBattle() {
	Class<?> classRobot = null;
	Constructor<?> constructorRobot;
//	this.threadRobots = new Thread[fullClassRobots.size()];

	for (int i = 0; i < fullClassRobots.size(); i++) {
	    try {
		classRobot = Class.forName(fullClassRobots.get(i));
		constructorRobot = classRobot.getDeclaredConstructor();
		System.out.println(constructorRobot);
		Robot newRobot = (Robot) constructorRobot.newInstance();
//		this.threadRobots[i] = new Thread(newRobot);
//		this.robot.add(newRobot);
		this.robot[i] = newRobot;
		this.panelBattleField.add(robot[i]);
	    } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
		    | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
		e1.printStackTrace();
	    }
	}
	
	frame.start();
    }

    /**
     * Get the full class name of the robot to be used to locate the robot's file.
     * 
     * @return The class name of the robot in this format: packagefolder.subpackagefolder.Classname
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
	// Extract ONLY package name from the first line of the file
	StringBuffer strPackageRobot = new StringBuffer(firstLineFile).replace(0, index + 1, "");
	// Get the class name removing the .java extension from the file name (for convention class name = to file name)
	String className = fileRobot.getName().replace(".java", "");
	// Merge package name and class name to create the full class name necessary for loading the robot
	String fullClass = (strPackageRobot + "." + className).replace(";", "");

	return fullClass;
    }
    
    /**
     * Update game's information every 0.01666 seconds (60fps).
     * 
     * @throws IOException 
     */
    public void update() throws IOException {
	if (isBattleStopped)
	    resetGame();
	
	else if (!isBattleStopped)
	    for (int i = 0; i < robot.length; i ++) 
		if (robot[i] != null)
		    robot[i].update();
    }
    
    /**
     * Reset game by interrupting the main thread and clearing out the battlefield's JPanel.
     */
    private void resetGame() {
	System.out.println("Reset game");
	this.threadMain.interrupt();
	this.panelBattleField.removeAll();
	this.panelBattleField.revalidate();
	this.panelBattleField.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == buttonsLoad.get(0)) {
//	    loadRobot(labelPathRobot.get(0));
	    fullClassRobots.add("zhrfrd.testjrobots.Test");
	    labelPathRobot.get(0).setText("zhrfrd.testjrobots.Test");
	}

	if (e.getSource() == buttonsLoad.get(1)) {
	    fullClassRobots.add("zhrfrd.testjrobots.Test");
	    labelPathRobot.get(0).setText("zhrfrd.testjrobots.Test");
//	    loadRobot(labelPathRobot.get(1));
	}

	if (e.getSource() == buttonsLoad.get(2))
	    loadRobot(labelPathRobot.get(2));

	if (e.getSource() == buttonsLoad.get(3))
	    loadRobot(labelPathRobot.get(3));

	if (e.getSource() == buttonStart) {
	    startBattle();
	    buttonStart.setEnabled(false);
	}
	
	if (e.getSource() == buttonReset) {
	    isBattleStopped = true;
	    buttonStart.setEnabled(true);
	}
    }

    @Override
    public void run() {
	double drawInterval = 1000000000 / FPS; // Draw every 0.01666 seconds
	double delta = 0;
	long lastTime = System.nanoTime();
	long currentTime;

	// Game loop
	while (true) {
	    System.out.println("Running gameloop");
	    currentTime = System.nanoTime();
	    delta += (currentTime - lastTime) / drawInterval;
	    lastTime = currentTime;

	    // Every 0.01666 seconds (60 FPS)
	    if (delta >= 1) {
		try {
		    update();
		    
		    if (isBattleStopped) 
			break;
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		delta--;
	    }
	}
    }
}