package de.bitowl.ld27;

public class Crate extends Entity {
	public Crate(float pX,float pY){
		x=pX*level.tileWidth;y=pY*level.tileHeight;
		width=32;
		height=32;
		texture=TestScreen.crateT;
		collidable=true;
		blocking=true;
	}
	@Override
	public void hitByBullet() {
		texture=TestScreen.crateDestroyedT;
		collidable=false;
		blocking=false;
	}
}
