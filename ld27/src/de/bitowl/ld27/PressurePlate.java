package de.bitowl.ld27;

public class PressurePlate extends Entity {
	boolean down;
	boolean downThisFrame;
	boolean downByBarrel;
	public PressurePlate(float pX,float pY){
		tileX=(int)pX;tileY=(int)pY;
		offsetX=1;
		offsetY=0;
		x=pX*level.tileWidth+offsetX;y=pY*level.tileHeight+offsetY;
		width=31;
		height=12;
		texture=TestScreen.atlas.findRegion("pressureplate");
		collidable=true;
		sendsPower=true;
	}
	
	@Override
	public void hitByPlayer(boolean pX) {
		if(!down){
			texture=TestScreen.atlas.findRegion("pressureplate_down");
			down=true;
			powerConnection(true);
		}
		downThisFrame=true;
	}
	
	@Override
	public void update(float pDelta) {
		// put it up in every frame, so the player has to still stand on it to be down
		if((down && downThisFrame) || downByBarrel) {
			downThisFrame=false;
		}else if(down){
			texture=TestScreen.atlas.findRegion("pressureplate");
			down=false;
			powerConnection(false);
		}
	}

	public void hitByBarrel() {
		texture=TestScreen.atlas.findRegion("pressureplate_down");
		down=true;
		downByBarrel=true;
		powerConnection(true);
	}
}
