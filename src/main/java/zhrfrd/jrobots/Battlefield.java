package zhrfrd.jrobots;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import zhrfrd.entities.Missile;
import zhrfrd.entities.Robot;

public class Battlefield extends JPanel implements Runnable {
    private static final long serialVersionUID = -2969862236631824201L;
    public JPanel panelBattleField;
//    private ArrayList<Robot> robot;
    final int FPS = 60;
    final int MAX_ROBOTS = 4;
    private Robot robot[];
    private ArrayList<Missile> missileList = new ArrayList<>();
    static File fileRobot;
    static BufferedReader fileReader;
    static BufferedImage bufferedImage;
    static ImageIcon imageIcon;
    static String firstLineFile = "";
    private Thread threadBattle;
    protected boolean isBattleStopped = false;
    protected boolean isBattlePaused = false;
    protected JFileChooser fileChooser;
    
    /**
     * Create and start the main thread when the user presses the Start! button.
     */
    protected void start() {
	threadBattle = new Thread(this, "Thread main");
	threadBattle.start(); // Go to run()
    }
    
    /**
     * Update game's information every 0.01666 seconds (60fps).
     * 
     * @throws IOException 
     */
    protected void update() throws IOException {
	if (isBattleStopped)
	    resetBattle();
	
	else if (!isBattleStopped && !isBattlePaused)
	    for (int i = 0; i < robot.length; i ++) 
		if (robot[i] != null)
		    robot[i].update();
    }
    
    /**
     * Reset game by interrupting the main thread and clearing out the battlefield's JPanel.
     */
    protected void resetBattle() {
	System.out.println("Reset game");
	isBattleStopped = false;
	threadBattle.interrupt();
	removeAll();
	revalidate();
	repaint();
    }
    
    /**
     * Pause the battle thread.
     */
    protected void pauseBattle() {
	if (!isBattlePaused) {
//	    this.threadMain.interrupt();
	    isBattlePaused = true;
	}
	
	else if (isBattlePaused) {
//	    this.threadMain.notify();
	    isBattlePaused = false;
	}
    }
    
    /**
     * Start the battle by getting the full class of each robot and creating a new
     * instance of Robot passing JRobots as parameter.
     */
    protected void startBattle(ArrayList<String> fullClassRobotsArrayList) {
	robot = new Robot[MAX_ROBOTS];
	System.out.println(robot.length);
	
	Class<?> classRobot = null;
	Constructor<?> constructorRobot;
//	this.threadRobots = new Thread[fullClassRobots.size()];

	for (int i = 0; i < fullClassRobotsArrayList.size(); i++) {
	    try {
		classRobot = Class.forName(fullClassRobotsArrayList.get(i));
		constructorRobot = classRobot.getDeclaredConstructor();
		System.out.println(constructorRobot);
		Robot newRobot = (Robot) constructorRobot.newInstance();
//		this.threadRobots[i] = new Thread(newRobot);
//		this.robot.add(newRobot);
		robot[i] = newRobot;
		add(robot[i]);
	    } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
		    | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
		e1.printStackTrace();
	    }
	}
	
	start();
    }

    @Override
    public void run() {
	double drawInterval = 1000000000 / FPS; // Draw every 0.01666 seconds
	double delta = 0;
	long lastTime = System.nanoTime();
	long currentTime;

	// Game loop
	while (true) {
	    if (this.isBattlePaused)
		try {
		    this.threadBattle.wait();
		} catch (InterruptedException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
//	    System.out.println("Running gameloop");
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
