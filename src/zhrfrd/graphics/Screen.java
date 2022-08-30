package zhrfrd.graphics;

import zhrfrd.battle.tile.Tile;
import zhrfrd.entity.Missile;

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
	public void renderMissile(int xp, int yp, Missile m) {
		
	}
	
	/*
	 * Render robot sprite
	 */
	public void renderRobot() {
		
	}
	
	/*
	 * Render tiles
	 */
	public void renderTile (int xPos, int yPos, Tile tile) {   // xPos and yPos are individual positions of the tile
		for (int y = 0; y < tile.sprite.SIZE; y++)
			for (int x = 0; x < tile.sprite.SIZE; x++)
				pixels [xPos + yPos * width] = tile.sprite.pixels [x + y * tile.sprite.SIZE];
	}
	
	/*
	 * Clear screen
	 */
	public void clear() {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = 0;
	}
}
