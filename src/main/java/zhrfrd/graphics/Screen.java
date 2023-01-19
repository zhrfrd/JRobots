package zhrfrd.graphics;

import java.util.Random;

public class Screen {
    public int width, height;
    public final int MAP_SIZE = 40;
    public final int MAP_SIZE_MASK = MAP_SIZE - 1;
    public int[] pixels;
    public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
    private Random random = new Random();

    // Constructor
    public Screen(int width, int height) {
	this.width = width;
	this.height = height;
	pixels = new int[width * height];
	
	for (int i = 0; i < MAP_SIZE * MAP_SIZE; i ++)
	    tiles[i] = random.nextInt(0xffffff);
    }
    
    /**
     * Render each pixel of the battlefield.
     */
    public void render() {
	for (int y = 0; y < height; y ++) {
	    if (y < 0 ||  y >= height)
		break; 
	    
	    for (int x = 0; x < width; x ++) {
		if (x < 0 || x >= width)
		    break;
		
//		int tileIndex = (x / 40) + (y / 40) * 10;
//		pixels[x + y + width] = tiles[tileIndex];
		int tileIndex = ((x / 10) & MAP_SIZE_MASK) + ((y / 10) & MAP_SIZE_MASK)  * MAP_SIZE;
		pixels[x + y  * width] = Sprite.grass.pixels[(x & 9) + (y & 9)  * Sprite.grass.SIZE];
	    }
	}
    }
    
    /**
     * Reset all the pixels to 0 in order to refresh at each loop the battlefield.
     */
    public void clear() {
	for (int i = 0; i < pixels.length; i ++)
	    pixels[i] = 0;
    }
}
