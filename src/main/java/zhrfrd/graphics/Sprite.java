package zhrfrd.graphics;

public class Sprite {
    public final int SIZE;
    private int x, y; // Coordinates of the sprite inside the sprite sheet
    private int height, width;
    public int[] pixels;
    private SpriteSheet sheet;
    public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles); 
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

    public Sprite(int size, int colour) { 
	this.width = size;
	this.height = size;
	SIZE = size;
	pixels = new int[SIZE * SIZE];
	setColour(colour);
    }

    /*
     * Add colour to each pixel
     */
    public void setColour(int colour) {
	for (int i = 0; i < width * height; i++)
	    pixels[i] = colour;
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
