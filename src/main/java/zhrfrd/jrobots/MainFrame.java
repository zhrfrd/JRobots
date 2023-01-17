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

public class MainFrame extends JFrame implements ActionListener {
    private static final long serialVersionUID = -3190346657795484951L;
    protected JFileChooser fileChooser;
    protected JPanel panelMain, panelRightMenuContainer, panelStartController, panelRobotsContainer;
    protected Battlefield battlefield;
    protected JButton buttonStart;
    protected JButton buttonPause;
    protected JButton buttonReset;
    protected JButton buttonRestart;
    protected ArrayList<JPanel> panelRobotArrayList;
    protected ArrayList<JButton> buttonsLoadArrayList;
    protected ArrayList<JLabel> labelPathRobotArrayList;
    protected ArrayList<JLabel> labelLifeRobotArrayList;
    protected ArrayList<String> fullClassRobotsArrayList;
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

    /**
     * Creates the layout of the battlefield with all the related components
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

	battlefield = new Battlefield();
	battlefield.setBackground(new Color(153, 102, 51));

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

	panelRobotArrayList = new ArrayList<JPanel>();
	buttonsLoadArrayList = new ArrayList<JButton>();
	labelPathRobotArrayList = new ArrayList<JLabel>();
	labelLifeRobotArrayList = new ArrayList<JLabel>();
	fullClassRobotsArrayList = new ArrayList<String>();

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
	    buttonsLoadArrayList.add(buttonLoad);
	    labelPathRobotArrayList.add(labelPathRobot);
	    labelLifeRobotArrayList.add(labelLifeRobot);
	    panelRobotArrayList.add(panelRobot);
	    panelRobotsContainer.add(panelRobot);
	}

	panelRightMenuContainer.add(panelRobotsContainer);
	panelRightMenuContainer.add(panelStartController);

	panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.X_AXIS));
	panelMain.setFocusable(true);
	panelMain.add(battlefield);
	panelMain.add(panelRightMenuContainer);

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension minimumSize = new Dimension(480, 270);

	setMinimumSize(minimumSize);
	setPreferredSize(new Dimension(screenSize.width * 8 / 10, screenSize.height * 8 / 10));
	panelRightMenuContainer.setMinimumSize(new Dimension(screenSize.width / 5, minimumSize.height));
	panelRightMenuContainer.setPreferredSize(new Dimension(screenSize.width / 5, screenSize.height));
	panelRightMenuContainer.setMaximumSize(new Dimension(screenSize.width / 5, screenSize.height));

	panelStartController.setMinimumSize(new Dimension(screenSize.width / 5, 100));
	panelStartController.setPreferredSize(new Dimension(screenSize.width / 5, 200));
	panelStartController.setMaximumSize(new Dimension(screenSize.width / 5, 200));

	add(panelMain);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setResizable(true);
	pack();
	setVisible(true);
	setLocationRelativeTo(null);
    }
    
    /**
     * Upload the robot from a folder and load it to the game by adding the class
     * name of the robot to its specific JLabel.
     * 
     * @param labelPathRobot The JLabel that will contain the class name of the
     *                       robot created by the user.
     */
    protected void loadRobot(JLabel labelPathRobot) {
	resetFullClassRobots();
	
	fileChooser = new JFileChooser();
	int response = fileChooser.showOpenDialog(null);
	String fullClass = "";

	if (response == JFileChooser.APPROVE_OPTION)
	    fileRobot = new File(fileChooser.getSelectedFile().getAbsolutePath());

	fullClass = extractFullClassRobot();
	fullClassRobotsArrayList.add(fullClass);
	labelPathRobot.setText(fullClass);
    }
    
    /**
     * Remove all the classed saved inside the fullClassRobots array list.
     */
    protected void resetFullClassRobots() {
	for (int i = 0; i < fullClassRobotsArrayList.size(); i ++)
	    fullClassRobotsArrayList.remove(i);
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

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == buttonsLoadArrayList.get(0)) {
//	    loadRobot(labelPathRobot.get(0));
	    fullClassRobotsArrayList.add("zhrfrd.testjrobots.Test");
	    labelPathRobotArrayList.get(0).setText("zhrfrd.testjrobots.Test");
	}

	if (e.getSource() == buttonsLoadArrayList.get(1)) {
	    fullClassRobotsArrayList.add("zhrfrd.testjrobots.Test");
	    labelPathRobotArrayList.get(0).setText("zhrfrd.testjrobots.Test");
//	    loadRobot(labelPathRobot.get(1));
	}

	if (e.getSource() == buttonsLoadArrayList.get(2))
	    loadRobot(labelPathRobotArrayList.get(2));

	if (e.getSource() == buttonsLoadArrayList.get(3))
	    loadRobot(labelPathRobotArrayList.get(3));

	if (e.getSource() == buttonStart) {
	    battlefield.startBattle(fullClassRobotsArrayList);
	    buttonStart.setEnabled(false);
	}
	
	if (e.getSource() == buttonPause) {
	    battlefield.pauseBattle();
	    buttonStart.setEnabled(false);
	}
	
	if (e.getSource() == buttonReset) {
	    isBattleStopped = true;
	    buttonStart.setEnabled(true);
	}
	
	if (e.getSource() == buttonRestart) {
	    isBattleStopped = true;
	    buttonStart.setEnabled(true);
	}
    }

    
}