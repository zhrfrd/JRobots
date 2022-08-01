package zhrfrd.actionlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

public class ActionListenerJRobots implements ActionListener {
	private JFileChooser fileChooser;
	private File fileRobot;
	public int actionResponse;
	
	@Override
	public void actionPerformed (ActionEvent e) {
		fileChooser = new JFileChooser();
		
		actionResponse = fileChooser.showOpenDialog(null);
		
		if (actionResponse == JFileChooser.APPROVE_OPTION) {
			fileRobot = new File(fileChooser.getSelectedFile().getAbsolutePath());
			
		}
	
	}
	
	public File getFile() {
		System.out.println("ljndsklhsdlj");
		return fileRobot;
	}
}
