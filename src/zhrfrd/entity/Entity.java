package zhrfrd.entity;

import java.util.Random;

import zhrfrd.battle.Battle;
import zhrfrd.graphics.Screen;

public abstract class Entity {
	public int x, y;
	private boolean removed = false;
	protected Battle battle;
	protected final Random random = new Random ();
	
	public void update () {

	}
	
	public void render (Screen screen) {
		
	}
	
	/*
	 * Remove entity from the battle
	 */
	public void remove () {
		removed = true;
	}
	
	public boolean isRemoved () {
		return removed;
	}
	
	/*
	 * Initiate the entity into the battle
	 */
	public void init (Battle battle) {
		this.battle = battle;
	}
}
