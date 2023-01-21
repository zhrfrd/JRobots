package zhrfrd.entities.mobs;

import zhrfrd.entities.Entity;
import zhrfrd.graphics.Screen;
import zhrfrd.graphics.Sprite;

public class Player extends Entity {

    public Player() {
	
    }
    
    public Player(int x, int y) {
	this.x = x;
	this.y = y;
    }
    
    @Override
    public void update() {
	
    }
    
    @Override
    public void render(Screen screen) {
	screen.renderPlayer(x,  y,  Sprite.player0);
    }

    @Override
    protected void setStartingPosition() {
	// TODO Auto-generated method stub
	
    }
}
