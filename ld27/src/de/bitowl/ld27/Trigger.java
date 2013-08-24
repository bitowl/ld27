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
		level.player.speedX=0;
		level.player.speedY=0;
		if(!triggered){
			texture=TestScreen.trigger2T;
			triggered=true;
			for(Wall wall:level.walls){
				wall.down();
			}
		}else{
			texture=TestScreen.triggerT;
			triggered=false;
			for(Wall wall:level.walls){
				wall.up();
			}
		}
	}
	@Override
	public void hitByBullet() {
		hitByPlayer();
	}
}
