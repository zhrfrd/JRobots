package zhrfrd.battle.tile;

import zhrfrd.graphics.Sprite;

public class RobotTile extends Tile {

	public RobotTile(Sprite sprite) {
		super(sprite);
	}
	
	// Robot tile is solid
	@Override
	public boolean solid () {
		return true;
	}
}