package zhrfrd.graphics;

public class Sprite {
    public final int SIZE;
    private int x, y; // Coordinates of the sprite inside the sprite sheet
    private int height, width;
    public int[] pixels;
    private SpriteSheet sheet;
    public static Sprite dirt1 = new Sprite(16, 0, 0, SpriteSheet.tiles); 
    public static Sprite dirt2 = new Sprite(16, 1, 0, SpriteSheet.tiles); 
    public static Sprite dirt3 = new Sprite(16, 2, 0, SpriteSheet.tiles); 
    public static Sprite dirt4 = new Sprite(16, 3, 0, SpriteSheet.tiles); 
    public static Sprite dirt5 = new Sprite(16, 4, 0, SpriteSheet.tiles);
    public static Sprite rock1 = new Sprite(16, 5, 0, SpriteSheet.tiles); 
    public static Sprite rock2 = new Sprite(16, 6, 0, SpriteSheet.tiles); 
    public static Sprite rock3 = new Sprite(16, 7, 0, SpriteSheet.tiles); 
    public static Sprite rock4 = new Sprite(16, 8, 0, SpriteSheet.tiles);
    public static Sprite robot1 = new Sprite(16, 0, 1, SpriteSheet.tiles);
//    public static Sprite robot2 = new Sprite(16, 1, 10, SpriteSheet.tiles);
//    public static Sprite robot3 = new Sprite(16, 0, 11, SpriteSheet.tiles);
//    public static Sprite robot4 = new Sprite(16, 1, 11, SpriteSheet.tiles);
    public static Sprite missile = new Sprite(16, 1, 1, SpriteSheet.tiles);
    public static Sprite particle_explosion = new Sprite(3, 0xaaaaaa);

    /**
     * The sprite of a specific entity.
     * @param size Size of the sprite in pixels.
     * @param x X coordinate of the sprite inside the spritesheet. (Not in pixels but in tiles)
     * @param y Y coordinate of the sprite inside the spritesheet. (Not in pixels but in tiles)
     * @param sheet Spritesheet containing the sprite.
     */
    public Sprite(int size, int x, int y, SpriteSheet sheet) {
	this.width = size;
	this.height = size;
	this.x = x * size; // Set coordinates for the target sprite in the spritesheet
	this.y = y * size; //
	this.sheet = sheet;
	SIZE = size;
	pixels = new int[SIZE * SIZE];
	load();
    }

    public Sprite(int size, int color) { 
	this.width = size;
	this.height = size;
	SIZE = size;
	pixels = new int[SIZE * SIZE];
	setColor(color);
    }

    /*
     * Add colour to each pixel 
     */
    public void setColor(int color) {
	for (int i = 0; i < SIZE * SIZE; i++)
	    pixels[i] = color;
    }

    /**
     * Load single sprite out of the spritesheet.
     */
    private void load() {
	for (int y = 0; y < SIZE; y++)
	    for (int x = 0; x < SIZE; x++)
		pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
    }

    /*
     * Get width of the sprite
     */
    public int getWidth() {
	return width;
    }

    /*
     * Get height of the sprite
     */
    public int getHeight() {
	return height;
    }
}
