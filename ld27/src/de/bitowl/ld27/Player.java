package de.bitowl.ld27;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Player extends Entity{
	
	/**
	 * the angle the player shoots bullets in
	 */
	float angle;
	
	Animation animation;
	float animTime;

	public Player(){
		offsetX=3;
		offsetY=3;
		x=0+offsetX;y=0+offsetY;
		height=26;
		width=28;
		testOnOtherEntities=true;
		animation=new Animation(0.1f, TestScreen.atlas.findRegions("player"));
	}
	
	@Override
	public void update(float pDelta) {
		super.update(pDelta);
		

		if(speedX!=0 || speedY!=0){
			// calculate in which angle the player will shoot a bullet
			angle=MathUtils.atan2(speedY, speedX);
			animTime+=pDelta;
			if(animTime>animation.animationDuration){
				animTime-=animation.animationDuration;
			}
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(animation.getKeyFrame(animTime),x-offsetX,y-offsetY);
	}
	
	@Override
	public void hitEntity(Entity pEntity,boolean pX) {
		pEntity.hitByPlayer(pX);
	}

}
