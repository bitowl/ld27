package de.bitowl.ld27;

public class Chest extends Entity {
	boolean open;
	public Chest(float pX,float pY){
		x=pX*level.tileWidth;y=pY*level.tileHeight;
		width=32;
		height=32;
		texture=TestScreen.chestT;
		collidable=true;
		blocking=true;
	}
	@Override
	public void hitByPlayer() {
		if(!open){
			texture=TestScreen.chestOpenT;
			open=true;
		}
		
	}
}
