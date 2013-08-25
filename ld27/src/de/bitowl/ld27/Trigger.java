package de.bitowl.ld27;

public class Trigger extends Entity {
	boolean triggered;
	
	/**
	 * cooldown before trigger can be triggered again
	 */
	float cooldown;

	public Trigger(int pX, int pY) {
		tileX=pX;tileY=pY;
		offsetX=6;
		offsetY=2;
		x=pX*level.tileWidth+offsetX;y=pY*level.tileHeight+offsetY;
		width=22;
		height=27;
		collidable=true;
		blocking=true;
		texture=TestScreen.atlas.findRegion("trigger");
		sendsPower=true;
	}
	@Override
	public void hitByPlayer(boolean pX) {	
		trigger();
	}
	@Override
	public void hitByBullet() {
		trigger();
	}
	public void trigger(){
		if(cooldown>0){
			return;
		}
		if(!triggered){
			texture=TestScreen.atlas.findRegion("trigger_triggered");
			triggered=true;
		/*	for(Wall wall:level.walls){
				wall.down();
			}*/
			powerConnection(true);
			//level.putPowerOnConnection((int)(x/level.tileWidth), (int)(y/level.tileHeight), true);
		}else{
			texture=TestScreen.atlas.findRegion("trigger");
			triggered=false;
			/*for(Wall wall:level.walls){
				wall.up();
			}*/
			powerConnection(false);
		}
		cooldown=0.3f;
	}
	@Override
	public void update(float pDelta) {
		super.update(pDelta);
		if(cooldown>0){cooldown-=pDelta;}
	}
}
