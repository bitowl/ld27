package de.bitowl.ld27;

/**
 * exact the opposite of a wall, goes up if powered
 * 
 * @author bitowl
 *
 */
public class AntiWall extends Entity{
	public AntiWall(int pX,int pY) {
		tileX=pX;tileY=pY;
		offsetX=0;
		offsetY=3;
		x=pX*level.tileWidth;y=pY*level.tileHeight+offsetY;
		width=32;
		height=22;
		texture=TestScreen.atlas.findRegion("wall_down");
		blocking=false;
		collidable=false;
	}
	public void down(){
		texture=TestScreen.atlas.findRegion("wall_down");
		blocking=false;
		collidable=false;
	}
	public void up(){
		texture=TestScreen.atlas.findRegion("wall");
		blocking=true;
		collidable=true;
	}
	
	@Override
	public void powerByConnection(boolean pOn) {
		if(pOn){
			up();
		}else{
			down();
		}
	}
}
