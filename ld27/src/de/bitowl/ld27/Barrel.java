package de.bitowl.ld27;

public class Barrel extends Entity{
	boolean open;
	public Barrel(float pX,float pY){
		x=pX*level.tileWidth;y=pY*level.tileHeight;
		width=32;
		height=32;
		texture=TestScreen.barrelT;
		collidable=true;
	}
	@Override
	public void hitByPlayer() {
		if(!open){
			texture=TestScreen.barrelOpenT;
			open=true;
		}
	}
}
