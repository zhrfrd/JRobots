package zhrfrd.jrobots;

import java.awt.Color;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	//Constructor
	GameFrame() {
		GamePanel gp = new GamePanel();
		GamePanel gp2 = new GamePanel();
		gp.setBackground(Color.black);
		gp.t.start();
		gp2.t.start();
		this.add(gp);
		this.setTitle("JRobots");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
