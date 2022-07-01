package zhrfrd.jrobots;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	//Constructor
	GameFrame() {
		this.add(new GamePanel());
		this.setTitle("JRobots");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
