package zhrfrd.battle.tile;

import zhrfrd.graphics.Screen;
import zhrfrd.graphics.Sprite;

public class Tile {
	public int x, y;
	public Sprite sprite;
	public static Tile robot = new RobotTile(Sprite.robot);
	public static Tile missile = new MissileTile(Sprite.missile);
	public static Tile floor = new FloorTile(Sprite.floor);
	public static Tile wall = new WallTile(Sprite.wall);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);  // Void tile used when there is a rendering tile error
	public static final int colour_wall = 0xffff0000;   // Red #ff0000
	public static final int colour_floor = 0xff000000;

	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}

	public void render(int x, int y, Screen screen) {
	}

	// Default tile value is false
	public boolean solid() {
		return false;
	}
}
