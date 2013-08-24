package de.bitowl.ld27;

public class Wall extends Entity{
	public Wall(int pX,int pY) {
		x=pX*level.tileWidth;y=pY*level.tileHeight;
		width=32;
		height=32;
		texture=TestScreen.wallT;
		collidable=true;
		blocking=true;
	}
	public void down(){
		texture=TestScreen.wallOpenT;
		blocking=false;
		collidable=false;
	}
	public void up(){
		texture=TestScreen.wallT;
		blocking=true;
		collidable=true;
	}
}
