package zhrfrd.jrobots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 75;
	int appleX = 0;
	int appleY = 0;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	boolean running = false;
	Timer timer;
	Random random;
	
	// Constructor
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		startGame();
		startGame();
	}
	
	// Methods
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
	}
	
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}
	
	public void move() {
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	@Override
	public void actionPerformed (ActionEvent e) {
	}

}
