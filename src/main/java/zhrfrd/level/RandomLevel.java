package zhrfrd.level;

import java.util.Random;

public class RandomLevel extends Level {
    private static final Random RANDOM = new Random();

    public RandomLevel(int width, int height) {
	super(width, height);
    }

    /**
     * Generate level where tiles are set to a random number from 0 - 3.
     */
    @Override
    protected void generateLevel() {
	for (int y = 0; y < height; y ++) {
	    for (int x = 0; x < width; x ++) {
		tiles[x + y * width] = RANDOM.nextInt(11);
	    }
	}
    }
}
