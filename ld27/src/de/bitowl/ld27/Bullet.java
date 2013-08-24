package de.bitowl.ld27;

import com.badlogic.gdx.Gdx;

public class Bullet extends Entity{
	/*float x;
	float y;
	
	float speedX;
	float speedY;*/
	
	//float SPEED=400;
	
	public Bullet(float pX,float pY,float pSpeedX,float pSpeedY){
		x=pX;
		y=pY;
		speedX=pSpeedX;
		speedY=pSpeedY;
		width=8;
		height=8;
		
		SPEED=400;
	}
	@Override
	public void hitWall() {
		// bullets are killed by walls
		level.bullets.removeValue(this, true);
	}
	
	/*public void update(float delta){
		x+=speedX*SPEED*delta;
		y+=speedY*SPEED*delta;
	}*/
}
