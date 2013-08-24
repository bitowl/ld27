package de.bitowl.ld27;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;

public class Player extends Entity{
	
	float life=10;
		
	
/*	float x;
	float y;
	
	// TODO add the x and y offset every
	int height=27;
	int width=27;*/
	
	
	
	float angle;
	
	
	public Player(){
		x=0;y=0;
		height=27;
		width=27;
		texture=TestScreen.playerT;
		testOnOtherEntities=true;
	}
	
	@Override
	public void update(float pDelta) {
		super.update(pDelta);
		
		// calculate in which angle the player will shoot a bullet
		if(speedX!=0 || speedY!=0){
			angle=MathUtils.atan2(speedY, speedX);
		}
	}

	
	@Override
	public void hitEntity(Entity pEntity) {
		pEntity.hitByPlayer();
	}
	
	/*public void moveLeft(float pDelta){
		x-=pDelta*SPEED;
		speedX=-1;
	}
	public void moveRight(float pDelta){
		x+=pDelta*SPEED;
		speedX=1;
	}
	public void moveUp(float pDelta){
		y+=pDelta*SPEED;
		speedY=1;
	}
	public void moveDown(float pDelta){
		y-=pDelta*SPEED;
		speedY=-1;
	}*/
}
