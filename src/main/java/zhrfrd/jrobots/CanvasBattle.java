package zhrfrd.jrobots;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import zhrfrd.entities.Robot;
import zhrfrd.graphics.Screen;
import zhrfrd.level.Level;

public class CanvasBattle extends Canvas implements Runnable {
    private static final long serialVersionUID = -2969862236631824201L;
    protected int width = 1200 - (1200 - (1200 / 3));
    protected int height = width;
    private final int FPS = 60;
    private final int MAX_ROBOTS = 4;
    private Robot robots[];
    // TODO Change public to protected or private 
    private Thread threadBattle;
    protected boolean isBattleStopped = false;
    protected boolean isBattlePaused = false;
    protected JFileChooser fileChooser;
    private Level level;
    Screen screen;
    // Create an image for the canvasBattle. 
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
    // Convert image to array of integers signalling the color of each pixel.
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    
    public CanvasBattle() {
	screen = new Screen(width, height);
	level = new Level(25, 25);
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
     */
    protected void update() {
	if (isBattleStopped)
	    resetBattle();
	
	else if (!isBattleStopped && !isBattlePaused) {
	    for (int i = 0; i < robots.length; i ++) 
		if (robots[i] != null) {
		    robots[i].update();
		    
		    for (int j = 0; j < robots[i].missileList.size(); j ++) 
			if (robots[i].missileList.get(j) != null)
			    robots[i].missileList.get(j).update();
		}
	}
	
    }
    
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
		
		for (int j = 0; j < robots[i].missileList.size(); j ++) 
		    if (robots[i].missileList.get(j) != null)
			robots[i].missileList.get(j).render(screen);
	    }
	
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
     * @param fullClassRobotsArrayList ArrayList containing the full class string of the robot.
     */
    protected void startBattle(ArrayList<String> fullClassRobotsArrayList) {
	robots = new Robot[MAX_ROBOTS]; 
	Class<?> classRobot = null;
	Constructor<?> constructorRobot;
	
	for (int i = 0; i < fullClassRobotsArrayList.size(); i++) {
	    try {
		classRobot = Class.forName(fullClassRobotsArrayList.get(i));
		constructorRobot = classRobot.getDeclaredConstructor();
		Robot newRobot = (Robot) constructorRobot.newInstance();
		robots[i] = newRobot;
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
	    if (isBattlePaused)
		try {
		    threadBattle.wait();
		} catch (InterruptedException e1) {
		    e1.printStackTrace();
		}
	    
	    currentTime = System.nanoTime();
	    delta += (currentTime - lastTime) / drawInterval;
	    lastTime = currentTime;

	    // Every 0.01666 seconds (60 FPS)
	    if (delta >= 1) {
		update();
		render();

		if (isBattleStopped) 
		    break;

		delta--;
	    }
	}
    }
}
