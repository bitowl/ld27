package de.bitowl.ld27;

public class Crate extends Entity {
	public Crate(float pX,float pY){
		tileX=(int)pX;tileY=(int)pY;
		x=pX*level.tileWidth;y=pY*level.tileHeight;
		width=32;
		height=32;
		texture=TestScreen.atlas.findRegion("crate");
		collidable=true;
		blocking=true;
		sendsPower=true;
	}
	@Override
	public void hitByBullet() {
		texture=TestScreen.atlas.findRegion("crate_destroyed");
		collidable=false;
		blocking=false;
		
		level.obstacleMap[tileX][tileY]=0;
		
		powerConnection(true);
	}
}
