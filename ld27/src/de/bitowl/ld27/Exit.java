package de.bitowl.ld27;

public class Exit extends Entity {
	public Exit(int pX,int pY) {
		x=pX*level.tileWidth;y=pY*level.tileHeight;
		width=32;
		height=32;
		texture=TestScreen.exitT;
	}
}
