package zhrfrd.graphics;

import zhrfrd.scenario.Tile;

public class Screen {
    public int width, height;
    public int[] pixels;
    private int SPRITE_SIZE = 16;

    /**
     * Screen handles the rendering of the elements to the game's screen.
     * @param width Screen width in pixels.
     * @param height Screen height in pixels.
     */
    public Screen(int width, int height) {
	this.width = width;
	this.height = height; 
	pixels = new int[width * height];
    } 
    
    /**
     * Render tile on the battlefield by iterating through each pixel of the single tile'sprite (eg. tile.sprite.SIZE = 16).
     * @param xBattlefield Individual position of the tile in the x coordinate of the battlefield.
     * @param yBattlefield Individual position of the tile in the Y coordinate of the battlefield.
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
    
    /**
     * Render particles to the screen.
     * @param xBattlefield Individual position of the sprite in the X coordinate of the battlefield.
     * @param yBattlefield Individual position of the sprite in the Y coordinate of the battlefield.
     * @param sprite The sprite of the particle.
     */
    public void renderSprite (int xBattlefield, int yBattlefield, Sprite sprite) {   //Render single sprite
	for (int y = 0; y < sprite.getHeight(); y ++) {
	    int yAbsolute = y + yBattlefield;
		
	    for (int x = 0; x < sprite.getWidth(); x ++) {
		int xAbsolute = x + xBattlefield;
			
		if (xAbsolute < 0 || xAbsolute >= width || yAbsolute < 0 || yAbsolute >= height)   //Don't draw when exceed the size of screen by skipping one iteration
		    continue;
		
		pixels[xAbsolute + yAbsolute * width] = sprite.pixels [x + y * sprite.getWidth()];
	    }
	}
    }
    
    /**
     * Render the entities to the screen.
     * @param xBattlefield Individual position of the entity in the X coordinate of the battlefield.
     * @param yBattlefield Individual position of the entity in the Y coordinate of the battlefield.
     * @param sprite Entity's sprite.
     */
    public void renderEntity(int xBattlefield, int yBattlefield, Sprite sprite) {
	for (int y = 0; y < SPRITE_SIZE; y ++) {
	    int yAbsolute = y + yBattlefield;
	    
	    for (int x = 0; x < SPRITE_SIZE; x ++) {
		int xAbsolute = x + xBattlefield;
		    
		if (sprite.pixels[x + y * SPRITE_SIZE] != 0xffff00ff)
		    pixels[xAbsolute + yAbsolute * width] = sprite.pixels[x + y * SPRITE_SIZE];
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
