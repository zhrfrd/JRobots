package zhrfrd.graphics;

public class Screen {
	public int width, height;
	public int[] pixels;
	
	// Constructor
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	//Methods

	/*
	 * Render missile sprite 
	 */
	public void renderMissile() {
		
	}
	
	/*
	 * Render robot sprite
	 */
	public void renderRobot() {
		
	}
	
	/*
	 * Clear screen
	 */
	public void clear() {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = 0;
	}
}
