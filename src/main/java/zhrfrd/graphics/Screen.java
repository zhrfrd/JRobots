package zhrfrd.graphics;

import java.util.Random;

import zhrfrd.level.tile.Tile;

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
    
//    /**
//     * Render each pixel of the canvasBattle.
//     */
//    public void render() {
//	for (int y = 0; y < height; y ++) {
//	    int yBattlefield = y;
//	    if (yBattlefield < 0 ||  yBattlefield >= height)
//		continue;
//	    
//	    for (int x = 0; x < width; x ++) {
//		int xBattlefield = x;
//		
//		if (xBattlefield < 0 || xBattlefield >= width)
//		    break;
//		
////		int sx = (xx / tileSize) & 39;
////		int sy = (yy / tileSize) & 39;
//		
//		int sx = x & 39;
//		int sy = y & 39;
//		
////		int tileIndex = ((xx / tileSize) & 39) + ((yy / tileSize) & 39) * MAP_COLS; // Tile that needs to be rendered at a particular position
////		pixels[x + (y * width)] = tiles[tileIndex];
//
//		// Which pixels on the screen get rendered    //Which pixels on the tile get rendered
//		pixels[xBattlefield + yBattlefield * width] = Sprite.grass.pixels[(xBattlefield & 15) + (yBattlefield & 15) * Sprite.grass.SIZE]; 
//	    }
//	}
//    }
    
    /**
     * Render tile on the battlefield by iterating through each pixel of the single tile'sprite (eg. tile.sprite.SIZE = 16 ).
     * 
     * @param xp Individual position of the tile in the x coordinate of the battlefield.
     * @param yp Individual position of the tile in the Y coordinate of the battlefield.
     * @param tile Tile to be rendered.
     */
    public void renderTile(int xBattlefield, int yBattlefield, Tile tile) {
	for (int y = 0; y < tile.sprite.SIZE; y ++) {
	    int yAbsolute = y + yBattlefield;
	    
	    for (int x = 0; x < tile.sprite.SIZE; x ++) {
		    int xAbsolute = x + xBattlefield;
		    
		    if (xAbsolute < 0 || xAbsolute >= width || yAbsolute < 0 || yAbsolute >= width)
			break;
		    
		    pixels[xAbsolute + yAbsolute * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
	    }
	}
	    
    }
    
    /**
     * Reset all the pixels to 0 in order to refresh at each loop the canvasBattle.
     */
    public void clear() {
	for (int i = 0; i < pixels.length; i ++)
	    pixels[i] = 0;
    }
}
