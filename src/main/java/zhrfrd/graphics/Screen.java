package zhrfrd.graphics;

import java.util.Random;

public class Screen {
    public int width, height;
    public final int MAP_COLS = 10; // 10 x 10 tiles
    public final int MAP_COLS_MASK = MAP_COLS - 1;
    public int[] pixels;
    public int[] tiles = new int[MAP_COLS * MAP_COLS];
    private int tileSize = 40;
    private Random random = new Random();

    // Constructor
    public Screen(int width, int height) {
	this.width = width;
	this.height = height; 
	pixels = new int[width * height];
	
	// Random color for each tile
	for (int i = 0; i < MAP_COLS * MAP_COLS; i ++)
	    tiles[i] = random.nextInt(0xffffff);
    }
    
    /**
     * Render each pixel of the battlefield.
     */
    public void render() {
	int c = 0;
	for (int y = 0; y < height; y ++) {
//	    System.out.println("y " + y);
	    int yy = y;
//	    if (yy < 0 ||  yy >= height)
//		break;
	    
	    for (int x = 0; x < width; x ++) {
//		System.out.println("x " + x);
		int xx = x;
		c++;
//		if (xx < 0 || xx >= width)
//		    break;
//		int sx = (xx / tileSize) & 39;
//		int sy = (yy / tileSize) & 39;
		
		int sx = x & 39;
		int sy = y & 39;
		
//		int tileIndex = ((xx / tileSize) & 39) + ((yy / tileSize) & 39) * MAP_COLS; // Tile that needs to be rendered at a particular position
//		pixels[x + (y * width)] = tiles[tileIndex];

		
		pixels[x + y * width] = Sprite.grass.pixels[(x & 15) + (y & 15) * Sprite.grass.SIZE]; 
	    }
	    System.out.println(c); 
	    System.out.println("--------------"); // Every 400
	}
//	System.out.println(c);
    }
    
    /**
     * Reset all the pixels to 0 in order to refresh at each loop the battlefield.
     */
    public void clear() {
	for (int i = 0; i < pixels.length; i ++)
	    pixels[i] = 0;
    }
}
