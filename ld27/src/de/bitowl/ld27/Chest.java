package de.bitowl.ld27;

public class Chest extends Entity {
	boolean open;
	public Chest(float pX,float pY){
		tileX=(int)pX;tileY=(int)pY;
		offsetX=1;
		offsetY=1;
		x=pX*level.tileWidth+offsetX;y=pY*level.tileHeight+offsetY;
		width=28;
		height=25;
		texture=TestScreen.chestT;
		collidable=true;
		blocking=true;
	}
	@Override
	public void hitByPlayer(boolean pX) {
		if(!open){
			texture=TestScreen.chestOpenT;
			open=true;
		}
		
	}
}
