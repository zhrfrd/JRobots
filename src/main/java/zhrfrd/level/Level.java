package zhrfrd.level;

import java.util.Random;

import zhrfrd.graphics.Screen;
import zhrfrd.level.tile.Tile;

public class Level {
    protected int width, height;
    protected int[] tiles;
    private static final Random RANDOM = new Random();
    
    public Level(int width, int height) {
	this.width = width;
	this.height = height;
	tiles = new int[width * height];
	
	generateLevel();
    }
    
    /**
     * Generate level where tiles are set to a random number from 0 - 3.
     */
    protected void generateLevel() {
	for (int y = 0; y < height; y ++)
	    for (int x = 0; x < width; x ++)
		tiles[x + y * width] = RANDOM.nextInt(18);
    } 
    
    public void render(Screen screen) {
	// Corner pins to identify the area of the map to be rendered (from top-left to bottom-right)
	int x0 = 0;
	int x1 = screen.width >> 4;
	int y0 = 0;
	int y1 = screen.height >> 4;
	
	for (int y = y0; y < y1; y ++)
	    for (int x = x0; x < x1; x ++)
		getTile(x, y).render(x, y, screen);
    }
    
    /**
     * Get the tile that needs to be rendered.
     * 
     * @param x X position of the tile in the battlefield using tile precision instead of pixel precision.
     * @param y Y position of the tile in the battlefield using tile precision instead of pixel precision.
     * @return Tile that needs to be rendered.
     */
    public Tile getTile(int x, int y) {
	if (tiles[x + y * width] == 0 || tiles[x + y * width] == 1 || tiles[x + y * width] == 2)
	    return Tile.dirt1;
	
	if (tiles[x + y * width] == 3 || tiles[x + y * width] == 4 || tiles[x + y * width] == 5)
	    return Tile.dirt2;
	
	if (tiles[x + y * width] == 6 || tiles[x + y * width] == 7 || tiles[x + y * width] == 8)
	    return Tile.dirt3;
	
	if (tiles[x + y * width] == 9 || tiles[x + y * width] == 10 || tiles[x + y * width] == 11)
	    return Tile.dirt4;
	
	if (tiles[x + y * width] == 12 || tiles[x + y * width] == 13)
	    return Tile.dirt5;
	
	if (tiles[x + y * width] == 14)
	    return Tile.rock1;
	
	if (tiles[x + y * width] == 15)
	    return Tile.rock2;
	
	if (tiles[x + y * width] == 16)
	    return Tile.rock3;
	
	return Tile.rock4;
    }
}
