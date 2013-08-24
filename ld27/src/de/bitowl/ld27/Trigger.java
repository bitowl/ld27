package de.bitowl.ld27;

public class Trigger extends Entity {
	boolean triggered;

	public Trigger(int pX, int pY) {
		x=pX*level.tileWidth;y=pY*level.tileHeight;
		width=32;
		height=32;
		collidable=true;
		blocking=true;
		texture=TestScreen.triggerT;
	}
	@Override
	public void hitByPlayer() {
		if(!triggered){
			texture=TestScreen.trigger2T;
			triggered=true;
		}
	}
	@Override
	public void hitByBullet() {
		hitByPlayer();
	}
}
