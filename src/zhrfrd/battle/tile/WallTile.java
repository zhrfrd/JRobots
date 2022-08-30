package zhrfrd.battle.tile;

import zhrfrd.graphics.Screen;
import zhrfrd.graphics.Sprite;

public class WallTile extends Tile {

	public WallTile(Sprite sprite) {
		super(sprite);
	}
	
	@Override
	public void render (int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);   //Pixel precision
	}
	
	// Wall tile is solid
	@Override
	public boolean solid () {
		return true;
	}
}