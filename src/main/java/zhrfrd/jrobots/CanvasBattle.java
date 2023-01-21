package zhrfrd.jrobots;

import java.awt.Canvas;
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
import zhrfrd.level.Level;

public class CanvasBattle extends Canvas implements Runnable {
    private static final long serialVersionUID = -2969862236631824201L;
    public JPanel panelBattleField;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = 1200 - (1200 - (1200 / 3));
    int height = width;
//    private ArrayList<Robot> robot;
    final int FPS = 60;
    final int MAX_ROBOTS = 4;
    private Robot robots[];
//    private Missile missiles[];
    // TODO Change public to protected or private 
    public ArrayList<Entity> entitiesList = new ArrayList<>();
    public ArrayList<Robot> robotsList = new ArrayList<>();
//    public ArrayList<Missile> missilesList = new ArrayList<>();
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
    private Level level;
    Screen screen;
//    public ArrayList<Missile> missileList = new ArrayList<Missile>();
//    public Missile missile;

    /**
     * Create an image for the canvasBattle. 
     */
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
    /**
     * Convert image to array of integers signaling the color of each pixel.
     */
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    
    public CanvasBattle() {
	screen = new Screen(width, height);
	level = new Level(25, 25);
//	player = new Player();
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
	    for (int i = 0; i < robots.length; i ++) 
		if (robots[i] != null) {
		    robots[i].update(screen);
		    
		    for (int j = 0; j < robots[i].missileList.size(); j ++) 
			if (robots[i].missileList.get(j) != null)
			    robots[i].missileList.get(j).update(screen);
		}
	    
//	    for (int i = 0; i < missiles.length; i ++) 
//		if (missiles[i] != null)
//		    missiles[i].update(screen);
	    
	    for (int i = 0; i < particlesList.size(); i++)
		if (particlesList.get(i) != null)
		    // TODO Refactor update() method (see Robot)
		    particlesList.get(i).update(screen);
	}
	
    }
    
//    public void update() {
////	player.update();
//    }
    
    /**
     * Render the CanvasBattle.
     */
    public void render() {
	// Get actual buffer strategy from the object CanvasBattle (subclass of Canvas) and save it to bs. The first time you access render() bs is null.
	BufferStrategy bufferStrategy = getBufferStrategy();
	
	// Called only once (the first time render() is accessed.
	if (bufferStrategy == null) {
		createBufferStrategy(3); // Triple buffering
		return;
	}
	 
	screen.clear();
	level.render(screen);
	
	for (int i = 0; i < robots.length; i ++) 
	    if (robots[i] != null) {
		robots[i].render(screen);
		
		for (int j = 0; j < robots[i].missileList.size(); j ++) { 
		    if (robots[i].missileList.get(j) != null) {
			robots[i].missileList.get(j).render(screen);
		    }
		}
	    }
	
//	for (int i = 0; i < missiles.length; i ++) { 
//	    if (missiles[i] != null) {
//		missiles[i].render(screen);
//	    }
//	}
	
	for (int i = 0; i < pixels.length; i ++)
	    pixels[i] = screen.pixels[i];
	
	Graphics g = bufferStrategy.getDrawGraphics();
	g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	g.dispose();
	bufferStrategy.show();
    }
    
    /**
     * Reset game by interrupting the main thread and clearing out the canvasBattle's JPanel.
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
	robots = new Robot[MAX_ROBOTS];
//	missiles = new Missile[10]; // TODO Change missile from array to something else
	System.out.println(robots.length);
	 
	Class<?> classRobot = null;
	Constructor<?> constructorRobot;
//	this.threadRobots = new Thread[fullClassRobots.size()];

	for (int i = 0; i < fullClassRobotsArrayList.size(); i++) {
	    try {
		classRobot = Class.forName(fullClassRobotsArrayList.get(i));
		constructorRobot = classRobot.getDeclaredConstructor();
		Robot newRobot = (Robot) constructorRobot.newInstance();
//		this.threadRobots[i] = new Thread(newRobot);
//		this.robot.add(newRobot);
		robots[i] = newRobot;
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
		} catch (IOException e) {
		    e.printStackTrace();
		}
		render();

		if (isBattleStopped) 
		    break;

		delta--;
	    }
	}
    }
}
