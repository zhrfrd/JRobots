package zhrfrd.jrobots;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

public class MainFrame extends JFrame implements ActionListener {
    private static final long serialVersionUID = -3190346657795484951L;
    protected JFileChooser fileChooser;
    protected JPanel panelMain, panelRightMenuContainer, panelStartController, panelRobotsContainer;
    protected CanvasBattle canvasBattle;
    protected JButton buttonStart;
    protected JButton buttonPause;
    protected JButton buttonReset;
    protected JButton buttonRestart;
    protected JButton buttonLoad0;
    protected JButton buttonLoad1;
    protected JButton buttonLoad2;
    protected JButton buttonLoad3;
    protected JButton buttonCancelLoad0;
    protected JButton buttonCancelLoad1;
    protected JButton buttonCancelLoad2;
    protected JButton buttonCancelLoad3;
    protected JPanel[] panelsRobotArrayList;
    protected JButton[] buttonsLoadArrayList;
    protected JButton[] buttonsCancelLoadArrayList;
    protected JLabel[] labelsPathRobotArrayList;
    protected JLabel[] labelsLifeRobotArrayList;
    protected String[] fullClassesRobotsArrayList;
    protected boolean isButtonLoad0Active = true;
    protected boolean isButtonLoad1Active = true;
    protected boolean isButtonLoad2Active = true;
    protected boolean isButtonLoad3Active = true;
//    private ArrayList<Robot> robot;
    final int FPS = 60;
    final int MAX_ROBOTS = 4;
    protected Robot robot[];
    protected ArrayList<Missile> missileListArrayList = new ArrayList<>();
    static File fileRobot;
    static BufferedReader fileReader;
    static BufferedImage bufferedImage;
    static ImageIcon imageIcon;
    static String firstLineFile = "";
    
    protected boolean isBattleStopped = false;
    protected boolean isBattlePaused = false;
    
    static final int SCREEN_WIDTH = 1000;
    static final int SCREEN_HEIGHT = SCREEN_WIDTH / 3 * 2; // ASPECT RATIO 16:9
    static final int RIGHT_MENU_WIDTH = SCREEN_WIDTH - (SCREEN_WIDTH / 3);
    static final int RIGHT_MENU_HEIGHT = SCREEN_HEIGHT;
    static final int BATTLEFIELD_WIDTH = SCREEN_WIDTH - RIGHT_MENU_WIDTH;
    static final int BATTLEFIELD_HEIGHT = BATTLEFIELD_WIDTH;


    /**
     * Creates the layout of the canvasBattle with all the related components
     */
    public MainFrame() {
	initializeLayout();
    }

    /**
     * Create the layout of the game's window by positioning all the components in their position
     */
    protected void initializeLayout() {
	panelMain = new JPanel();
	Font font = panelMain.getFont().deriveFont(50);
	panelMain.setFont(font);

	canvasBattle = new CanvasBattle();
	canvasBattle.setBackground(new Color(153, 102, 51));

	panelRightMenuContainer = new JPanel();
	panelRightMenuContainer.setLayout(new BoxLayout(panelRightMenuContainer, BoxLayout.Y_AXIS));
	panelRightMenuContainer.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.red));

	panelRobotsContainer = new JPanel();
	panelRobotsContainer.setLayout(new GridLayout(0, 1));
	panelRobotsContainer.setBackground(Color.gray);

	buttonStart = new JButton("Start!");
	buttonStart.addActionListener(this);
	buttonPause = new JButton("Pause");
	buttonPause.addActionListener(this);
	buttonReset = new JButton("Reset");
	buttonReset.addActionListener(this);
	buttonRestart = new JButton("Restart");
	buttonRestart.addActionListener(this);

	panelStartController = new JPanel();
	panelStartController.setBackground(Color.black);
	panelStartController.setLayout(new GridLayout(4, 0));
	panelStartController.add(buttonStart);
	panelStartController.add(buttonPause);
	panelStartController.add(buttonReset);
	panelStartController.add(buttonRestart);

	panelsRobotArrayList = new JPanel[4];
	buttonsLoadArrayList = new JButton[4];
	buttonsCancelLoadArrayList = new JButton[4];
	labelsPathRobotArrayList = new JLabel[4];
	labelsLifeRobotArrayList = new JLabel[4];
	fullClassesRobotsArrayList = new String[4];

	for (int i = 0; i < MAX_ROBOTS; i++) {
	    JButton buttonLoad = new JButton("Load robot " + (i + 1));
	    JButton buttonCancelLoad = new JButton("Cancel load " + (i + 1));
	    buttonLoad.addActionListener(this);
	    JLabel labelPathRobot = new JLabel("Path: ");
	    JLabel labelLifeRobot = new JLabel("Life: ");
	    JPanel panelRobot = new JPanel();
	    panelRobot.setBackground(Color.gray);
	    panelRobot.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
	    panelRobot.add(buttonLoad);
	    panelRobot.add(buttonCancelLoad);
	    panelRobot.add(labelPathRobot);
	    panelRobot.add(labelLifeRobot);
	    panelRobot.setLayout(new BoxLayout(panelRobot, BoxLayout.Y_AXIS));
	    buttonLoad.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonCancelLoad.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonCancelLoad.setVisible(false);
	    labelPathRobot.setAlignmentX(Component.CENTER_ALIGNMENT);
	    labelLifeRobot.setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonsLoadArrayList[i] = buttonLoad;
	    buttonsCancelLoadArrayList[i] = buttonCancelLoad;
	    labelsPathRobotArrayList[i] = labelPathRobot;
	    labelsLifeRobotArrayList[i] = labelLifeRobot;
	    panelsRobotArrayList[i] = panelRobot;
	    panelRobotsContainer.add(panelRobot);
	}

	panelRightMenuContainer.add(panelRobotsContainer);
	panelRightMenuContainer.add(panelStartController);

	panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.X_AXIS));
	panelMain.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
	panelMain.setFocusable(true);
	panelMain.add(canvasBattle);
	panelMain.add(panelRightMenuContainer);
	
	canvasBattle.setPreferredSize(new Dimension(SCREEN_HEIGHT, SCREEN_HEIGHT));
	panelRightMenuContainer.setPreferredSize(new Dimension(SCREEN_WIDTH - SCREEN_HEIGHT, SCREEN_HEIGHT));

//	Dimension monitorSize = Toolkit.getDefaultToolkit().getScreenSize();
//	
//	setPreferredSize(new Dimension(monitorSize.width * 8 / 10, monitorSize.height * 8 / 10));
//	panelRightMenuContainer.setPreferredSize(new Dimension(monitorSize.width / 5, monitorSize.height));
//	panelRightMenuContainer.setMaximumSize(new Dimension(monitorSize.width / 5, monitorSize.height));
//
//	panelStartController.setPreferredSize(new Dimension(monitorSize.width / 5, 200));
//	panelStartController.setMaximumSize(new Dimension(monitorSize.width / 5, 200));
//
//	canvasBattle.setBackground(Color.red);
//	canvasBattle.setPreferredSize(new Dimension(monitorSize.height * 8 / 10, monitorSize.height * 8 / 10));
//	canvasBattle.setMaximumSize(new Dimension(monitorSize.height * 8 / 10, monitorSize.height * 8 / 10));
	add(panelMain);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setResizable(false);
	pack();
	setVisible(true);
	setLocationRelativeTo(null);
    }
    
    /**
     * Upload the robot from a folder and load it to the game by adding the class
     * name of the robot to its specific JLabel.
     * @param labelPathRobot The JLabel that will contain the class name of the robot created by the user.
     */
    protected void loadRobot(JLabel labelPathRobot, int index) {
	resetFullClassRobots();
	
	fileChooser = new JFileChooser();
	int response = fileChooser.showOpenDialog(null);
	String fullClass = "";

	if (response == JFileChooser.APPROVE_OPTION)
	    fileRobot = new File(fileChooser.getSelectedFile().getAbsolutePath());

	fullClass = extractFullClassRobot();
	fullClassesRobotsArrayList[index] = fullClass;
	labelPathRobot.setText(fullClass);
    }
    
    /**
     * Remove all the classed saved inside the fullClassRobots array list.
     */
    protected void resetFullClassRobots() {
	for (int i = 0; i < fullClassesRobotsArrayList.length; i ++)
	    fullClassesRobotsArrayList[i] = "";
    }
    
    /**
     * Get the full class name of the robot to be used to locate the robot's file.
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

    @Override
    public void actionPerformed(ActionEvent e) {
	// Load robots
	if (e.getSource() == buttonsLoadArrayList[0]) {
	    buttonLoad0 = buttonsLoadArrayList[0];
	    buttonCancelLoad0 = buttonsCancelLoadArrayList[0];
	    buttonLoad0.setVisible(false);
	    buttonCancelLoad0.setVisible(true);
	    isButtonLoad0Active = false;
	    fullClassesRobotsArrayList[0] = "zhrfrd.testjrobots.Test";
	    labelsPathRobotArrayList[0].setText("zhrfrd.testjrobots.Test");
//	    loadRobot(labelPathRobot.get(0), 0);
	}

	if (e.getSource() == buttonsLoadArrayList[1]) {
	    buttonLoad1 = buttonsLoadArrayList[1];
	    buttonCancelLoad1 = buttonsCancelLoadArrayList[1];
	    buttonLoad1.setVisible(false);
	    buttonCancelLoad1.setVisible(true);
	    isButtonLoad1Active = false;
	    fullClassesRobotsArrayList[1] = "zhrfrd.testjrobots.Test";
	    labelsPathRobotArrayList[1].setText("zhrfrd.testjrobots.Test");
//	    loadRobot(labelPathRobot.get(1), 1);
	}

	if (e.getSource() == buttonsLoadArrayList[2]) {
	    buttonLoad2 = buttonsLoadArrayList[2];
	    buttonCancelLoad2 = buttonsCancelLoadArrayList[2];
	    buttonLoad2.setVisible(false);
	    buttonCancelLoad2.setVisible(true);
	    isButtonLoad2Active = false;
	    loadRobot(labelsPathRobotArrayList[2], 2);
	}

	if (e.getSource() == buttonsLoadArrayList[3]) {
	    buttonLoad3 = buttonsLoadArrayList[3];
	    buttonCancelLoad3 = buttonsCancelLoadArrayList[3];
	    buttonLoad3.setVisible(false);
	    buttonCancelLoad3.setVisible(true);
	    isButtonLoad3Active = false;
	    loadRobot(labelsPathRobotArrayList[3], 3);
	}
	
	// Cancel load robots
	if (e.getSource() == buttonsCancelLoadArrayList[0]) {
	    buttonLoad0 = buttonsLoadArrayList[0];
	    buttonCancelLoad0 = buttonsCancelLoadArrayList[0];
	    buttonCancelLoad0.setVisible(false);
	    buttonLoad0.setVisible(true);
	    isButtonLoad0Active = true;
	    
	}
	
	if (e.getSource() == buttonsCancelLoadArrayList[1]) {
	    buttonLoad1 = buttonsLoadArrayList[1];
	    buttonCancelLoad1 = buttonsCancelLoadArrayList[1];
	    buttonCancelLoad1.setVisible(false);
	    buttonLoad1.setVisible(true);
	    isButtonLoad1Active = true;
	}
	
	if (e.getSource() == buttonsCancelLoadArrayList[2]) {
	    buttonLoad2 = buttonsLoadArrayList[2];
	    buttonCancelLoad2 = buttonsCancelLoadArrayList[2];
	    buttonCancelLoad2.setVisible(false);
	    buttonLoad2.setVisible(true);
	    isButtonLoad2Active = true; 
	}
	
	if (e.getSource() == buttonsCancelLoadArrayList[3]) {
	    buttonLoad3 = buttonsLoadArrayList[3];
	    buttonCancelLoad3 = buttonsCancelLoadArrayList[3];
	    buttonCancelLoad3.setVisible(false);
	    buttonLoad3.setVisible(true);
	    isButtonLoad3Active = true;
	}

	// Actions 
	if (e.getSource() == buttonStart) {
	    canvasBattle.startBattle(fullClassesRobotsArrayList);
	    buttonStart.setEnabled(false);
	}
	
	if (e.getSource() == buttonPause) {
	    canvasBattle.pauseBattle();
	    buttonStart.setEnabled(false);
	}
	
	if (e.getSource() == buttonReset) {
	    canvasBattle.isBattleStopped = true;
	    buttonStart.setEnabled(true);
	}
	
	if (e.getSource() == buttonRestart) {
	    canvasBattle.isBattleStopped = true;
	    buttonStart.setEnabled(true);
	}
    }
}
