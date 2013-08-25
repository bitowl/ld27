package de.bitowl.ld27;

public class Barrel extends Entity{
	boolean open;
	PressurePlate standingOn;
	public Barrel(float pX,float pY){
		tileX=(int)pX;tileY=(int)pY;
		z=1;
		offsetX=3;
		offsetY=1;
		x=pX*level.tileWidth+offsetX;y=pY*level.tileHeight+offsetY;
		width=24;
		height=31;
		texture=TestScreen.atlas.findRegion("barrel");
		collidable=true;
		blocking=true;
	}
	@Override
	public void hitByPlayer(boolean pX) {
		if(!open){
			// first remove the heavy loot from the barrel
			texture=TestScreen.atlas.findRegion("barrel_open");
			open=true;
			// moved
			level.obstacleMap[tileX][tileY]=0;
		}else{
			// move the empty barrel
			if(pX){
				if(level.player.speedX>0){
					
					if(canMoveTo(x+3,y,RIGHT)){
						x+=3;
					}
				}else{
					if(canMoveTo(x-3,y,LEFT)){
						x-=3;
					}
				}
			}else{
				if(level.player.speedY>0){
					if(canMoveTo(x,y+3,UP)){
						y+=3;
					}
				}else{
					if(canMoveTo(x,y-3,DOWN)){
						y-=3;
					}
				}
			}
			Entity entity=getEntity(x,y);
			System.err.println("CHÄÄK");
			if(entity!=null && entity instanceof PressurePlate){ // a barell can as well keep a pressure plate down
				if(standingOn!=null){
					if(standingOn==entity){
						return;
					}
					standingOn.downByBarrel=false;// this pressureplate will move up next frame
				}
				System.err.println("barrel on pressureplate");
				standingOn=(PressurePlate)entity;
				
				standingOn.hitByBarrel();
			}else{
				if(standingOn!=null){
					standingOn.downByBarrel=false;
					standingOn=null;
				}
			}
		}
		
	}
}
