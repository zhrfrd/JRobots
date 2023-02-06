package zhrfrd.jrobots;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFileChooser;

import zhrfrd.entities.Robot;
import zhrfrd.graphics.Screen;
import zhrfrd.scenario.Scenario;

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
    private Scenario scenario;
    Screen screen;
    // Create an image for the canvasBattle. 
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
    // Convert image to array of integers signalling the color of each pixel.
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    // Game loop
    double drawInterval;
    double delta;
    long lastTime;
    long currentTime;
    
    public CanvasBattle() {
	screen = new Screen(width, height);
	scenario = new Scenario(25, 25);
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
	if (isBattleStopped) {
	    resetBattle();
	}
	
	else if (!isBattleStopped && !isBattlePaused) {
	    for (int i = 0; i < robots.length; i ++) {
		if (robots[i] != null) {
		    robots[i].update();
		    
		    for (int j = 0; j < robots[i].missileList.size(); j ++) { 
			if (robots[i].missileList.get(j) != null) {
			    robots[i].missileList.get(j).update();
			}
		    }
		}
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
	    createBufferStrategy(3);
	    return;
	}
	 
	screen.clear();
	scenario.render(screen);
	
	for (int i = 0; i < robots.length; i ++) {
	    if (robots[i] != null) {
		robots[i].render(screen);
		
		for (int j = 0; j < robots[i].missileList.size(); j ++) {
		    if (robots[i].missileList.get(j) != null) {
			robots[i].missileList.get(j).render(screen);
		    }
		}
		
		if (robots[i].isMissileExploded) {
		    for (int k = 0; k < robots[i].particle.particlesList.size(); k ++) {
			if (robots[i].particle.particlesList.get(k).life > 0) {
			    robots[i].particle.particlesList.get(k).update(screen);
			    robots[i].particle.particlesList.get(k).render(screen);
			    
			}
		    }
		}
	    }
	}
	
	for (int i = 0; i < pixels.length; i ++) {
	    pixels[i] = screen.pixels[i];
	}
	
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
	    isBattlePaused = true;
	}
	
	else if (isBattlePaused) {
	    resumeGame();
	}
    }
    
    /**
     * Start the battle by getting the full class of each robot and creating a new
     * instance of Robot passing JRobots as parameter.
     * @param fullClassesRobots ArrayList containing the full class string of the robot.
     */
    protected void startBattle(String[] fullClassRobotsArrayList) {
	robots = new Robot[MAX_ROBOTS]; 
	Class<?> classRobot = null;
	Constructor<?> constructorRobot; 
	
	for (int i = 0; i < fullClassRobotsArrayList.length; i++) {
	    if (fullClassRobotsArrayList[i] == null) {
		break;
	    }
	    
	    try {
		classRobot = Class.forName(fullClassRobotsArrayList[i]);
		System.out.println(fullClassRobotsArrayList[0]);
		constructorRobot = classRobot.getDeclaredConstructor();
		Robot newRobot = (Robot) constructorRobot.newInstance();
		robots[i] = newRobot;
	    } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
		e1.printStackTrace();
	    }
	}
	
	start();
    }
    
    private void setupGameLoop() {
	isBattlePaused = false;
	drawInterval = 1000000000 / FPS; // Draw every 0.01666 seconds
	delta = 0;
	lastTime = System.nanoTime();
    }
    
    @Override
    public void run() {
	setupGameLoop();
   
	// Game loop
	while (true) {
	    synchronized(this) {
		while (isBattlePaused) {
		    try {
			wait();
		    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}
	    }
                 
	    currentTime = System.nanoTime();
	    delta += (currentTime - lastTime) / drawInterval;
	    lastTime = currentTime;
              
	    // Every 0.01666 seconds (60 FPS)
	    if (delta >= 1) {
       	    	update();
       	    	render();
             
       	    	if (isBattleStopped) {
       	    	    break;
       	    	}
              
       	    	delta--;
	    }
	}
    }
    
    synchronized void resumeGame() {
	setupGameLoop();
	notify();
    }
}
