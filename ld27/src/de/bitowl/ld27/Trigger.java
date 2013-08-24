package de.bitowl.ld27;

public class Trigger extends Entity {
	boolean triggered;
	
	/**
	 * cooldown before trigger can be triggered again
	 */
	float cooldown;

	public Trigger(int pX, int pY) {
		x=pX*level.tileWidth;y=pY*level.tileHeight;
		width=32;
		height=32;
		collidable=true;
		blocking=true;
		texture=TestScreen.triggerT;
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
			texture=TestScreen.trigger2T;
			triggered=true;
		/*	for(Wall wall:level.walls){
				wall.down();
			}*/
			powerConnection(true);
			//level.putPowerOnConnection((int)(x/level.tileWidth), (int)(y/level.tileHeight), true);
		}else{
			texture=TestScreen.triggerT;
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
