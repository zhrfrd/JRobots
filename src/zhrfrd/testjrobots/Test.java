package zhrfrd.testjrobots;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import zhrfrd.jrobots.Robot;

public class Test extends Robot {
	@Override
	public void start() {
		boom();
	}
	
	@Override
	public void boom() {
		System.out.println("FAI BOOOOOOOOOOOM!!!");
	}
}