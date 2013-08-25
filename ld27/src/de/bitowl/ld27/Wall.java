package de.bitowl.ld27;

public class Wall extends Entity{
	public Wall(int pX,int pY) {
		tileX=pX;tileY=pY;
		offsetX=0;
		offsetY=3;
		x=pX*level.tileWidth;y=pY*level.tileHeight+offsetY;
		width=32;
		height=22;
		texture=TestScreen.atlas.findRegion("wall");
		collidable=true;
		blocking=true;
	}
	public void down(){
		texture=TestScreen.atlas.findRegion("wall_down");
		blocking=false;
		collidable=false;
		level.obstacleMap[tileX][tileY]=0;
	}
	public void up(){
		texture=TestScreen.atlas.findRegion("wall");
		blocking=true;
		collidable=true;
		level.obstacleMap[tileX][tileY]=1;
	}
	
	@Override
	public void powerByConnection(boolean pOn) {
		if(pOn){
			down();
		}else{
			up();
		}
	}
}
