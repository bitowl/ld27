package de.bitowl.ld27;

public class PressurePlate extends Entity {
	boolean down;
	boolean downThisFrame;
	boolean downByBarrel;
	public PressurePlate(float pX,float pY){
		x=pX*level.tileWidth;y=pY*level.tileHeight;
		width=32;
		height=32;
		texture=TestScreen.pressurePlateT;
		collidable=true;
	}
	
	@Override
	public void hitByPlayer(boolean pX) {
		if(!down){
			texture=TestScreen.pressurePlateDownT;
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
			texture=TestScreen.pressurePlateT;
			down=false;
			powerConnection(false);
		}
	}

	public void hitByBarrel() {
		texture=TestScreen.pressurePlateDownT;
		down=true;
		downByBarrel=true;
		powerConnection(true);
	}
}
