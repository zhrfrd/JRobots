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
    
    /**
     * Render each pixel of the battlefield assigning it a color value
     */
    public void render() {
	for (int y = 0; y < height; y ++)
	    for (int x = 0; x < width; x ++)
		pixels[x + (y  * width)] = 0xff00ff;
    }
    
    /**
     * Reset all the pixels to 0 in order to refresh at each loop the battlefield.
     */
    public void clear() {
	for (int i = 0; i < pixels.length; i ++)
	    pixels[i] = 0;
    }
}
