package zhrfrd.battle;

import java.util.ArrayList;
import java.util.List;

import zhrfrd.battle.tile.Tile;
import zhrfrd.entity.Entity;
import zhrfrd.entity.Missile;
import zhrfrd.entity.Particle;
import zhrfrd.graphics.Screen;

public class Battle {
	protected int width, height;
	protected int [] tiles;   //Contains all the level tiles
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Missile> missiles = new ArrayList<Missile>();
	private List<Particle> particles = new ArrayList<Particle>();
	public static Battle spawn = new SpawnBattle("res/battle/battleField1.png");
	
	// Constructor
	public Battle(int width, int height) {
		this.width = width;
		this.height = height;
		generateBattle();
	}
	
	public Battle(String path) {
		loadLevel(path);
//		generateLevel();
	}

	/*
	 * Generate the battle
	 */
	protected void generateBattle() {	
	}
	
	/*
	 * Load level from image
	 */
	protected void loadLevel (String path) {
	}
	
	/*
	 * Update battle to show entities behaviours
	 */
	public void update() {
		for (int i = 0; i < entities.size(); i ++)
			entities.get(i).update();
		
		for (int i = 0; i < missiles.size(); i ++)
			missiles.get(i).update();
		
		for (int i = 0; i < particles.size(); i ++)
			particles.get(i).update();
		
		remove();
	}
	
	/*
	 * Remove entities after been updated (example particles after ending their life cycle)
	 */
	private void remove () {
		for (int i = 0; i < entities.size(); i ++)
			if (entities.get(i).isRemoved())
				entities.remove(i);
		
		for (int i = 0; i < missiles.size(); i ++)
			if (missiles.get(i).isRemoved())
				missiles.remove(i);
		
		for (int i = 0; i < particles.size(); i ++)
			if (particles.get(i).isRemoved())
				particles.remove(i);
	}
	
	public List <Missile> getMissiles () {
		return missiles;
	}
	
	private void time() {
	}
	
	public boolean tileCollision(int x, int y, int size) {
		boolean solid = false;
		
		for (int c = 0; c < 4; c ++) {   //Check each corner of the tile
			int xt = (x - c  % 2 * size) >> 4;
			int yt = (y - c  / 2 * size) >> 4;
			
			// Check if the tile is solid
			if (getTile(xt, yt).solid())
				solid = true;
		}
		
		return solid;
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		int x0 = xScroll >> 4;   // Every 16 pixels equals to one tile
		int x1 = xScroll + screen.width >> 4;
		int y0 = yScroll >> 4;
		int y1 = yScroll + screen.height >> 4;
		
		for (int y = y0; y < y1; y ++)   // Render from top to bottom of the battlefield
			for (int x = x0; x < x1; x ++)   // Render from left to right of the battlefield
				getTile (x, y).render(x, y, screen);
		
		for (int i = 0; i < entities.size (); i ++)
			entities.get(i).render(screen);
			
		for (int i = 0; i < missiles.size (); i ++)
			missiles.get(i).render(screen);
		
		for (int i = 0; i < particles.size (); i ++)
			particles.get(i).render (screen);
		
	}
	
	// Get the tile 
	public Tile getTile (int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.voidTile;
		
		if (tiles [x + y * width] == Tile.colour_floor) 
			return Tile.floor;
		
		if (tiles [x + y * width] == Tile.colour_wall) 
			return Tile.wall;
		
		return Tile.voidTile;
	}
	
	/*
	 * Add an entity to the battle
	 */
	public void add (Entity e) {
		e.init(this);
		
		if (e instanceof Particle) 
			particles.add((Particle) e);
		else if (e instanceof Missile) 
			missiles.add((Missile) e);
		else
			entities.add(e);
	}
}
