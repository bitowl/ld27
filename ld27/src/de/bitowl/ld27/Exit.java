package de.bitowl.ld27;

public class Exit extends Entity {
	boolean open;
	public Exit(int pX,int pY) {
		tileX=pX;tileY=pY;
		x=pX*level.tileWidth;y=pY*level.tileHeight;
		width=32;
		height=32;
		texture=TestScreen.atlas.findRegion("exit");
		collidable=true;
		blocking=true;
	}
	@Override
	public void powerByConnection(boolean pOn) {
		open=pOn;
		if(pOn){
			texture=TestScreen.atlas.findRegion("exit_open");
			blocking=false;
		}else{
			texture=TestScreen.atlas.findRegion("exit");
		}
	}
	@Override
	public void hitByPlayer(boolean pX) {
		if(open){
			((TestScreen)LdGame.current.getScreen()).nextLevel();
		}
	}
}
