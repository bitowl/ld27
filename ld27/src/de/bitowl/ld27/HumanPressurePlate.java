package de.bitowl.ld27;

public class HumanPressurePlate extends Entity {
	boolean down;
	public HumanPressurePlate(float pX,float pY){
		tileX=(int)pX;tileY=(int)pY;
		offsetX=2;
		offsetY=1;
		x=pX*level.tileWidth+offsetX;y=pY*level.tileHeight+offsetY;
		width=29;
		height=9;
		texture=TestScreen.pressurePlatehT;
		collidable=true;
		sendsPower=true;
	}
	
	@Override
	public void hitByPlayer(boolean pX) {
		if(!down){
			texture=TestScreen.pressurePlatehDownT;
			down=true;
			powerConnection(true);
		}
	}
	


}
