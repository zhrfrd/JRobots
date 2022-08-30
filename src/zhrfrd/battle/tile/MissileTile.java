package zhrfrd.battle.tile;

import zhrfrd.graphics.Sprite;

public class MissileTile extends Tile {

	public MissileTile(Sprite sprite) {
		super(sprite);
	}
	
	// Missile tile is solid
	@Override
	public boolean solid () {
		return true;
	}
}