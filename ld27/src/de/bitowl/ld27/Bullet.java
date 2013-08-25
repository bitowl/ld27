package de.bitowl.ld27;

public class Bullet extends Entity{
	/*float x;
	float y;
	
	float speedX;
	float speedY;*/
	
	//float SPEED=400;
	
	float nextSpeedX;
	float nextSpeedY;
	
	Mirror lastMirror;
	float lastMirrorCooldown;
	
	public Bullet(float pX,float pY,float pSpeedX,float pSpeedY){
		z=0;
		
		x=pX;
		y=pY;
		speedX=pSpeedX;
		speedY=pSpeedY;
		width=8;
		height=8;
		
		SPEED=400;
		texture=TestScreen.atlas.findRegion("bullet");
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
		if(lastMirror!=null){
			if(lastMirrorCooldown>0){
				lastMirrorCooldown-=pDelta;
			}else{
				lastMirror=null;
			}
		}
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
		if(pEntity instanceof Trigger || pEntity instanceof Chest || pEntity instanceof Barrel || pEntity instanceof Wall || pEntity instanceof AntiWall){
			bounce(pX);
			pEntity.hitByBullet();
		}else if(pEntity instanceof PressurePlate){
			
		}else if(pEntity instanceof Mirror){ // epic high physics mirror logic here
			Mirror mirror=(Mirror)pEntity;
			switch(mirror.type){
				case Mirror.UPLEFT:
					if(speedX<0 && lastMirror!=mirror){
						bounce(true);
					}else if(speedY>0 && lastMirror!=mirror){
						bounce(false);
					}else{
						if(((int)newX+width)%level.tileWidth>=(((int)newY)%level.tileWidth)){ // reflect when we are on the diagonal
							if(lastMirror!=mirror){
								float tmp=speedY;
								speedY=speedX;
								speedX=tmp;
								lastMirror=mirror;
								lastMirrorCooldown=0.4f;
							}
						}
					}
					break;
					
				case Mirror.UPRIGHT:
					if(speedX>0 && lastMirror!=mirror){
						bounce(true);
					}else if(speedY>0 && lastMirror!=mirror){
						bounce(false);
					}else{
						if(((int)newX)%level.tileWidth<=32-(((int)newY)%level.tileWidth)){ // reflect when we are on the diagonal
							if(lastMirror!=mirror){
								float tmp=speedY;
								speedY=-speedX;
								speedX=-tmp;
								lastMirror=mirror;
								lastMirrorCooldown=0.4f;
							}
						}
					}
					break;				
					
					
				case Mirror.DOWNLEFT:
					if(speedX<0 && lastMirror!=mirror){
						bounce(true);
					}else if(speedY<0 && lastMirror!=mirror){
						bounce(false);
					}else{
						if(32-((int)newX+width)%level.tileWidth<=(((int)newY+height)%level.tileWidth)){ // reflect when we are on the diagonal
							if(lastMirror!=mirror){
								float tmp=speedY;
								speedY=-speedX;
								speedX=-tmp;
								lastMirror=mirror;
								lastMirrorCooldown=0.4f;
							}
						}
					}
					break;	
					
				case Mirror.DOWNRIGHT:
					if(speedX>0 && lastMirror!=mirror){
						bounce(true);
					}else if(speedY<0 && lastMirror!=mirror){
						bounce(false);
					}else{
						if(((int)newX)%level.tileWidth<=(((int)newY+height)%level.tileWidth)){ // reflect when we are on the diagonal
							if(lastMirror!=mirror){
								float tmp=speedY;
								speedY=speedX;
								speedX=tmp;
								lastMirror=mirror;
								lastMirrorCooldown=0.4f;
							}
						}
					}
					break;
			}
			
		}else{
			kill();
			pEntity.hitByBullet();
		}
		
		
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
