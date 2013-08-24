package de.bitowl.ld27;

public class Chest extends Entity {
	public Chest(float pX,float pY){
		x=pX*level.tileWidth;y=pY*level.tileHeight;
		width=32;
		height=32;
		texture=TestScreen.chestT;
		collidable=true;
	}
	@Override
	public void hitByPlayer() {
		texture=TestScreen.chestOpenT;
		collidable=false;
	}
}
