package de.bitowl.ld27;

public class Exit extends Entity {
	boolean open;
	public Exit(int pX,int pY) {
		tileX=pX;tileY=pY;
		x=pX*level.tileWidth;y=pY*level.tileHeight;
		width=32;
		height=32;
		texture=TestScreen.exitT;
		collidable=true;
		blocking=true;
	}
	@Override
	public void powerByConnection(boolean pOn) {
		open=pOn;
		if(pOn){
			texture=TestScreen.exitOpenT;
			blocking=false;
		}else{
			texture=TestScreen.exitT;
		}
	}
	@Override
	public void hitByPlayer(boolean pX) {
		if(open){
			((TestScreen)LdGame.current.getScreen()).nextLevel();
		}
	}
}
