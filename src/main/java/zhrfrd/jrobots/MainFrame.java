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
    protected JPanel[] panelsRobot;
    protected JButton[] buttonsLoad;
    protected JButton[] buttonsCancelLoad;
    protected JLabel[] labelsPathRobot;
    protected JLabel[] labelsLifeRobot;
    protected String[] fullClassesRobots;
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

	panelsRobot = new JPanel[4];
	buttonsLoad = new JButton[4];
	buttonsCancelLoad = new JButton[4];
	labelsPathRobot = new JLabel[4];
	labelsLifeRobot = new JLabel[4];
	fullClassesRobots = new String[4];

	for (int i = 0; i < MAX_ROBOTS; i++) {
	    buttonsLoad[i] = new JButton("Load robot " + (i + 1));
	    buttonsCancelLoad[i] = new JButton("Cancel load " + (i + 1));
	    labelsPathRobot[i] = new JLabel("Path: ");
	    labelsLifeRobot[i] = new JLabel("Life: ");
	    buttonsLoad[i].addActionListener(this);
	    buttonsCancelLoad[i].addActionListener(this);
	    panelsRobot[i] = new JPanel();
	    panelRobotsContainer.add(panelsRobot[i]);
	    panelsRobot[i].setBackground(Color.gray);
	    panelsRobot[i].setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
	    panelsRobot[i].add(buttonsLoad[i]);
	    panelsRobot[i].add(buttonsCancelLoad[i]);
	    panelsRobot[i].add(labelsPathRobot[i]);
	    panelsRobot[i].add(labelsLifeRobot[i]);
	    panelsRobot[i].setLayout(new BoxLayout(panelsRobot[i], BoxLayout.Y_AXIS));
	    buttonsLoad[i].setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonsCancelLoad[i].setAlignmentX(Component.CENTER_ALIGNMENT);
	    buttonsCancelLoad[i].setVisible(false);
	    labelsPathRobot[i].setAlignmentX(Component.CENTER_ALIGNMENT);
	    labelsLifeRobot[i].setAlignmentX(Component.CENTER_ALIGNMENT);
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

	if (response == JFileChooser.APPROVE_OPTION) {
	    fileRobot = new File(fileChooser.getSelectedFile().getAbsolutePath());
	    fullClass = extractFullClassRobot();
	    fullClassesRobots[index] = fullClass;
	    labelPathRobot.setText(fullClass);
	}
	
	else if (response == JFileChooser.CANCEL_OPTION) {
	    return;
	}
    }
    
    protected void cancelLoadRobot(JLabel labelPathRobot, int index) {
	resetFullClassRobots();
	labelPathRobot.setText("Path: ");
    }
    
    /**
     * Remove all the classed saved inside the fullClassRobots array list.
     */
    protected void resetFullClassRobots() {
	for (int i = 0; i < fullClassesRobots.length; i ++)
	    fullClassesRobots[i] = null;
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
	if (e.getSource() == buttonsLoad[0]) {
	    buttonsLoad[0].setVisible(false);
	    buttonsCancelLoad[0].setVisible(true);
	    fullClassesRobots[0] = "zhrfrd.testjrobots.Test";
	    labelsPathRobot[0].setText("zhrfrd.testjrobots.Test");
//	    loadRobot(labelPathRobot.get(0), 0);
	}

	if (e.getSource() == buttonsLoad[1]) {
	    buttonsLoad[1].setVisible(false);
	    buttonsCancelLoad[1].setVisible(true);
	    fullClassesRobots[1] = "zhrfrd.testjrobots.Test";
	    labelsPathRobot[1].setText("zhrfrd.testjrobots.Test");
//	    loadRobot(labelPathRobot.get(1), 1);
	}

	if (e.getSource() == buttonsLoad[2]) {
	    buttonsLoad[2].setVisible(false);
	    buttonsCancelLoad[2].setVisible(true);
	    loadRobot(labelsPathRobot[2], 2);
	}

	if (e.getSource() == buttonsLoad[3]) {
	    buttonsLoad[3].setVisible(false);
	    buttonsCancelLoad[3].setVisible(true);
	    loadRobot(labelsPathRobot[3], 3);
	}
	 
	// Cancel load robots
	if (e.getSource() == buttonsCancelLoad[0]) {
	    buttonsLoad[0].setVisible(true);
	    buttonsCancelLoad[0].setVisible(false);
	    cancelLoadRobot(labelsPathRobot[0], 0);
	}
	
	if (e.getSource() == buttonsCancelLoad[1]) {
	    buttonsLoad[1].setVisible(true);
	    buttonsCancelLoad[1].setVisible(false);
	    cancelLoadRobot(labelsPathRobot[1], 1);
	}
	
	if (e.getSource() == buttonsCancelLoad[2]) {
	    buttonsLoad[2].setVisible(true);
	    buttonsCancelLoad[2].setVisible(false);
	    cancelLoadRobot(labelsPathRobot[2], 2);
	}
	
	if (e.getSource() == buttonsCancelLoad[3]) {
	    buttonsLoad[3].setVisible(true);
	    buttonsCancelLoad[3].setVisible(false);
	    cancelLoadRobot(labelsPathRobot[3], 3);
	}

	// Actions 
	if (e.getSource() == buttonStart) {
	    canvasBattle.startBattle(fullClassesRobots);
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
