package zhrfrd.graphics;

import java.util.Random;

public class Screen {
    public int width, height;
//    public final int MAP_SIZE = 64;
//    public final int MAP_SIZE_MASK = MAP_SIZE - 1;
    public int[] pixels;
    public int[] tiles = new int[10 * 10];
    private int tileSize = 40;
    private Random random = new Random();

    // Constructor
    public Screen(int width, int height) {
	this.width = width;
	this.height = height;
	pixels = new int[width * height];
	
	// Random color for each tile
	for (int i = 0; i < 10 * 10; i ++)
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
		
		int tileIndex = (x / tileSize) + (y / tileSize) * 10; // Tile that needs to be rendered at a particular position
		pixels[x + y * width] = tiles[tileIndex];
//		pixels[x + y * width] = Sprite.grass.pixels[(x & 39) + (y & 39) * Sprite.grass.SIZE];
			
//		System.out.println(tileIndex);
//		pixels[x + y * width] = 0xff00ff;
//		int tileIndex = (x / 32) + (y / 32) * 64;
//		pixels[x + y + width] = tiles[tileIndex];
//		int tileIndex = ((x / 10) & MAP_SIZE_MASK) + ((y / 10) & MAP_SIZE_MASK)  * MAP_SIZE;
//		pixels[x + y  * width] = Sprite.grass.pixels[(x & 9) + (y & 9)  * Sprite.grass.SIZE];
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
