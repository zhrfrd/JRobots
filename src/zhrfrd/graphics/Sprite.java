package zhrfrd.graphics;

public class Sprite {
	public final int SIZE;
	private int x, y;   // Coordinates of the sprite inside the sprite sheet
	private int height, width;
	public int[] pixels;
	private SpriteSheet sheet;
	public static Sprite robot = new Sprite(48, SpriteSheet.robot);
	public static Sprite missile = new Sprite(16, SpriteSheet.missile);
	public static Sprite floor = new Sprite(16, 0xffffff);   // Change 16 to full battlefield size??
	public static Sprite particle_explosion = new Sprite(3, 0xaaaaaa);
	public static Sprite wall = new Sprite(16, 0x964b00);   // Change colour to actual sprite? 
	public static Sprite voidSprite = new Sprite(16, 0x1b87e0);
	
	// Constructors
	public Sprite(int size, SpriteSheet sheet) {
		this.width = size;
		this.height = size;
		this.sheet = sheet;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		
		load();
	}
	
	public Sprite(int size, int colour) {
		this.width = size;
		this.height = size;
		SIZE = - 1;
		pixels = new int[SIZE * SIZE];
		setColour(colour);
	}
	
	// Methods
	
	/*
	 * Add colour to each pixel
	 */
	public void setColour(int colour) {
		for (int i = 0; i < width * height; i++)
			pixels[i] = colour;
	}
	
	/*
	 * Load sprite from the sprite sheet pixel by pixel starting from the coordinate x,y specified
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
