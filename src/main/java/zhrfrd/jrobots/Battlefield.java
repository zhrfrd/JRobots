package zhrfrd.jrobots;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import zhrfrd.entities.Entity;
import zhrfrd.entities.Missile;
import zhrfrd.entities.Particle;
import zhrfrd.entities.Robot;
import zhrfrd.graphics.Screen;

public class Battlefield extends Canvas implements Runnable {
    private static final long serialVersionUID = -2969862236631824201L;
    public JPanel panelBattleField;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = 1200 - (1200 - (1200 / 3));
    int height = width;
//    private ArrayList<Robot> robot;
    final int FPS = 60;
    final int MAX_ROBOTS = 4;
    private Robot robot[];
    // TODO Change public to protected or private
    public ArrayList<Entity> entitiesList = new ArrayList<>();
    public ArrayList<Robot> robotsList = new ArrayList<>();
    public ArrayList<Missile> missilesList = new ArrayList<>();
    public ArrayList<Particle> particlesList = new ArrayList<>();
    static File fileRobot;
    static BufferedReader fileReader;
    static BufferedImage bufferedImage;
    static ImageIcon imageIcon;
    static String firstLineFile = "";
    private Thread threadBattle;
    protected boolean isBattleStopped = false;
    protected boolean isBattlePaused = false;
    protected JFileChooser fileChooser;
    Screen screen;
    /**
     * Create an image for the battlefield. 
     */
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
    /**
     * Convert image to array of integers signaling the color of each pixel.
     */
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    
    public Battlefield() {
	this.screen = new Screen(width, height);
    }
    
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
	
	else if (!isBattleStopped && !isBattlePaused) {
	    for (int i = 0; i < robot.length; i ++) 
		if (robot[i] != null)
		    robot[i].update();
	    
	    for (int i = 0; i < particlesList.size(); i++)
		if (particlesList.get(i) != null)
		    particlesList.get(i).update();
	}
    }
    
    /**
     * Render the Battlefield.
     */
    public void render() {
	// Get actual buffer strategy from the object Battlefield (subclass of Canvas) and save it to bs. The first time you access render() bs is null.
	BufferStrategy bufferStrategy = getBufferStrategy();
	
	// Called only once (the first time render() is accessed.
	if (bufferStrategy == null) {
		createBufferStrategy(3); // Triple buffering
		return;
	}
	
	screen.clear();
	screen.render();
	
	for (int i = 0; i < pixels.length; i ++) {
	    pixels[i] = screen.pixels[i];
	}
	
	Graphics g = bufferStrategy.getDrawGraphics();
	g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	g.dispose();
	bufferStrategy.show();
	
//	Graphics g = bs.getDrawGraphics();
//	g.setColor(new Color(23, 45, 68));
//	g.fillRect(0, 0, width, height);
//	g.dispose();
//	bs.show(); // Show the content of the buffer in queue
//	System.out.println("kslfkjsldfjlksd");
    }
    
    /**
     * Reset game by interrupting the main thread and clearing out the battlefield's JPanel.
     */
    protected void resetBattle() {
	System.out.println("Reset game");
	isBattleStopped = false;
	threadBattle.interrupt();
//	removeAll();
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
//		add(robot[i]);
	    } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
		    | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
		e1.printStackTrace();
	    }
	}
	
	start();
    }
    
    /**
     * Automatically called method when the JPanel component is created to draw.
     */
//    @Override
//    public void paintComponent(Graphics g) {
//	super.paintComponent(g); // Must be done for the JPanel painting takes place
//
//	Graphics2D g2 = (Graphics2D)g;
//	
//	new Particle(40, 100, new Color(64, 47, 46), 0, 0, 500).draw(g2);
//	
//	// Add particles list to array list
//	for (int i = 0; i < particlesList.size(); i++)
//	    if (particlesList.get(i) != null)
//		entitiesList.add(particlesList.get(i));
//    }

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
		// update();
		render();

		if (isBattleStopped) 
		    break;

		delta--;
	    }
	}
    }
}
