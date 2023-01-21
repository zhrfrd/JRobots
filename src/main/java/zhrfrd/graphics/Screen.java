package zhrfrd.graphics;

import zhrfrd.entities.mobs.Player;
import zhrfrd.level.tile.Tile;

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
		    
		    pixels[xAbsolute + yAbsolute * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
	    }
	} 
    }
    
    public void renderPlayer(int xBattlefield, int yBattlefield, Sprite sprite) {
	for (int y = 0; y < 16; y ++) {
	    int yAbsolute = y + yBattlefield;
	    
	    for (int x = 0; x < 16; x ++) {
		    int xAbsolute = x + xBattlefield;
		    
		    pixels[xAbsolute + yAbsolute * width] = sprite.pixels[x + y * 16];
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
