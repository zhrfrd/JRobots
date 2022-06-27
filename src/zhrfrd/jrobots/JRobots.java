package zhrfrd.jrobots;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class JRobots {
	public static void main (String [] args) {
		try {
			File my_file = new File("myJRobot.j");
			Scanner fileReader = new Scanner (my_file);
			if (my_file.createNewFile())
				System.out.println("File created: " + my_file.getName());
			else
				System.out.println("File '" + my_file + "' already exists.");
			while (fileReader.hasNextLine()) {
		        String data = fileReader.nextLine();
		        System.out.println(data);
		    }
			fileReader.close();
	    } catch (IOException e) {
	    	System.out.println("An error occurred.");
	    	e.printStackTrace();
	    }
	}
}