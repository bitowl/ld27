package de.bitowl.ld27;

import com.badlogic.gdx.Gdx;

public class Bullet extends Entity{
	/*float x;
	float y;
	
	float speedX;
	float speedY;*/
	
	//float SPEED=400;
	
	float nextSpeedX;
	float nextSpeedY;
	
	public Bullet(float pX,float pY,float pSpeedX,float pSpeedY){
		x=pX;
		y=pY;
		speedX=pSpeedX;
		speedY=pSpeedY;
		width=8;
		height=8;
		
		SPEED=400;
		texture=TestScreen.bulletT;
		testOnOtherEntities=true;
	}
	@Override
	public void hitWall(boolean pX) {
		// bullets are killed by walls
		//level.bullets.removeValue(this, true);
		//bounce(pX);
		kill();
	}
	
	@Override
	public void update(float pDelta) {
		if(nextSpeedX!=0){
			speedX=nextSpeedX;
			nextSpeedX=0;
		}
		if(nextSpeedY!=0){
			speedY=nextSpeedY;
			nextSpeedY=0;
		}
		super.update(pDelta);
	}
	@Override
	public void hitEntity(Entity pEntity,boolean pX) {
		// bullets are killed by other entities
		//
		if(pEntity instanceof Trigger || pEntity instanceof Chest || pEntity instanceof Barrel || pEntity instanceof Wall){
			bounce(pX);
		}else if(!(pEntity instanceof PressurePlate)){
			kill();
		}
		pEntity.hitByBullet();
		
		// but they might cause SERIOUS damage :P
	}
	
	public void kill(){
		level.bullets.removeValue(this, true);
	}
	public void bounce(boolean pX){
		if(pX){
			//speedX*=-1;
			nextSpeedX=-speedX;
			//x+=speedX*30;
		}else{
			nextSpeedY=-speedY;
			//speedY*=-1;
			//y+=speedY*30;
		}
	}
	/*public void update(float delta){
		x+=speedX*SPEED*delta;
		y+=speedY*SPEED*delta;
	}*/
}
