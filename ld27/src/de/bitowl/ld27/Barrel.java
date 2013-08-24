package de.bitowl.ld27;

public class Barrel extends Entity{
	public Barrel(float pX,float pY){
		x=pX*level.tileWidth;y=pY*level.tileHeight;
		width=32;
		height=32;
		texture=TestScreen.barrelT;
		collidable=true;
	}
	@Override
	public void hitByPlayer() {
		texture=TestScreen.barrelOpenT;
		collidable=false;
	}
}
