package zhrfrd.level;

import zhrfrd.graphics.Screen;
import zhrfrd.level.tile.Tile;

public class Level {
    protected int width, height;
    protected int[] tiles;
    
    public Level(int width, int height) {
	this.width = width;
	this.height = height;
	tiles = new int[width * height];
	
	generateLevel();
    }
    
    protected void generateLevel() {
	
    }
    
    public void update() {
	
    }
    
    public void render(int xScroll, int yScroll, Screen screen) {
	// Corner pins to identify the area of the map to be rendered
	int x0 = xScroll >> 4; // Tile precision 
	int x1 = (xScroll + screen.width) >> 4;
	int y0 = yScroll >> 4;
	int y1 = (yScroll + screen.height) >> 4;
	
	for (int y = y0; y < y1; y ++) {
	    for (int x = x0; x < x1; x ++) {
		getTile(x, y).render(x, y, screen);
	    }
	}
    }
    
    /**
     * Get the tile that needs to be rendered.
     * 
     * @param x X position of the tile in the battlefield using tile precision instead of pixel precision.
     * @param y Y position of the tile in the battlefield using tile precision instead of pixel precision.
     * @return Tile that needs to be rendered.
     */
    public Tile getTile(int x, int y) {
	if (tiles[x + y * width] == 0)
	    return Tile.dirt1;
	
	else if (tiles[x + y * width] == 1)
	    return Tile.dirt2;
	
	else if (tiles[x + y * width] == 2)
	    return Tile.dirt3;
	
	else if (tiles[x + y * width] == 3)
	    return Tile.dirt4;
	
	else if (tiles[x + y * width] == 4)
	    return Tile.dirt5;
	
	else if (tiles[x + y * width] == 5)
	    return Tile.dirt6;
	
	else if (tiles[x + y * width] == 6)
	    return Tile.dirt7;
	
	else if (tiles[x + y * width] == 7)
	    return Tile.rock1;
	
	else if (tiles[x + y * width] == 8)
	    return Tile.rock2;
	
	else if (tiles[x + y * width] == 9)
	    return Tile.crack2;
	
	return Tile.crack2;
    }
}
