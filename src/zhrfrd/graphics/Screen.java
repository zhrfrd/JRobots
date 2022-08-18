package zhrfrd.graphics;

import zhrfrd.battle.tile.Tile;
import zhrfrd.entity.Missile;

public class Screen {
	public int width, height, xOffset, yOffset;;
	public int[] pixels;
	
	// Constructor
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	//Methods
	
	/*
	 * Render single sprite from spritesheet
	 */
	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		
		for (int y = 0; y < sheet.HEIGHT; y ++) {
			int ya = y + yp;
			
			for (int x = 0; x < sheet.WIDTH; x ++) {
				int xa = x + xp;
				
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)   //Don't draw when exceed the size of screen by skipping one iteration
					continue;
				
				pixels[xa + ya * width] = sheet.pixels[x + y * sheet.WIDTH];
			}
		}
	}
	
	/*
	 * Render single sprite
	 */
	public void renderSprite (int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		
		for (int y = 0; y < sprite.getHeight(); y ++) {
			int ya = y + yp;
			
			for (int x = 0; x < sprite.getWidth(); x ++) {
				int xa = x + xp;
				
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)   //Don't draw when exceed the size of screen by skipping one iteration
					continue;
				
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
			}
		}
	}
	
	/*
	 * Render tiles
	 */
	public void renderTile (int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		
		for (int y = 0; y < tile.sprite.SIZE; y++) {   // Update absolute position of the single tile
			int ya = y + yp;
			
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp;
				
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) // Optimisation (Don't render tiles outside the	screen. Only render one tile extra in order to not show the blank tile at the edges when moving)
					break;
				
				if (xa < 0)
					xa = 0;
				
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}
	}

	/*
	 * Render missile sprite 
	 */
	public void renderMissile(int xp, int yp, Missile m) {
		xp -= xOffset;
		yp -= yOffset;
		
		for (int y = 0; y < m.getSpriteSize(); y++) {
			int ya = y + yp;
			
			for (int x = 0; x < m.getSpriteSize(); x++) {
				int xa = x + xp;
				
				if (xa < -m.getSpriteSize() || xa >= width || ya < 0 || ya >= height)
					break;
				
				if (xa < 0) 
					xa = 0;
				
				int col = m.getSprite().pixels[x + y * m.getSprite().SIZE];
				
				if (col != 0xffff00ff)
					pixels[xa + ya * width] = col;
			}
		}
	}
	
	/*
	 * Render robot sprite
	 */
	public void renderRobot (int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		
		for (int y = 0; y < 32; y++) {   // Update absolute position of the single tile
			int ya = y + yp;
			int ys = y;

			for (int x = 0; x < 32; x++) {
				int xa = x + xp;
				int xs = x;
				
				if (xa < 0)
					xa = 0;
				
				int col = sprite.pixels [xs + ys * 32];
				
				if (col != 0xffff00ff)   //Remove pink background from sprite (first 2 ffs are alpha channel)
					pixels [xa + ya * width] = col;
			}
		}
	}
	
	/*
	 * Clear screen
	 */
	public void clear() {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = 0;
	}
	
	/*
	 * Set offsets when moving (TO BE REMOVED)
	 */
	public void setOffset (int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}
