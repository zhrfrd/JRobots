package zhrfrd.scenario;

import zhrfrd.graphics.Screen;
import zhrfrd.graphics.Sprite;

public class Tile {
    public int xPos, y;
    public Sprite sprite;
    public static Tile dirt1 = new Tile(Sprite.dirt1);
    public static Tile dirt2 = new Tile(Sprite.dirt2);
    public static Tile dirt3 = new Tile(Sprite.dirt3); 
    public static Tile dirt4 = new Tile(Sprite.dirt4);
    public static Tile dirt5 = new Tile(Sprite.dirt5);
    public static Tile rock1 = new Tile(Sprite.rock1);
    public static Tile rock2 = new Tile(Sprite.rock2);
    public static Tile rock3 = new Tile(Sprite.rock3);
    public static Tile rock4 = new Tile(Sprite.rock4);
    
    public Tile(Sprite sprite) {
	this.sprite = sprite;
    }
    
    public void render(int x, int y, Screen screen) {
	screen.renderTile(x << 4, y << 4, this);
    }
    
    public boolean solid() {
	return false;
    }
}
