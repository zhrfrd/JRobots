package zhrfrd.battle;

import java.util.ArrayList;
import java.util.List;

import zhrfrd.entities.Entity;
import zhrfrd.entities.Missile;
import zhrfrd.entities.Particle;

public class Battle {
	protected int width, height;
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Missile> missiles = new ArrayList<Missile>();
	private List<Particle> particles = new ArrayList<Particle>();
	
	// Constructor
	public Battle(int width, int height) {
		this.width = width;
		this.height = height;
		generateBattle();
	}

	/*
	 * Generate the battle
	 */
	protected void generateBattle () {	
	
	}
	
	/*
	 * Add an entity to the battle
	 */
	public void add (Entity e) {
		e.init(this);
	}
}
