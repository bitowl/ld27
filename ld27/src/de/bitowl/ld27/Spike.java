package de.bitowl.ld27;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Spike extends Entity {
	
	Animation animation;
	float animTime;
	
	public Spike(int pX,int pY){
		tileX=pX;tileY=pY;
		offsetX=4;
		offsetY=4;
		x=pX*level.tileWidth+offsetX;y=pY*level.tileHeight+offsetY;
		width=22;
		height=25;
		collidable=true;
		blocking=true;
		animation=new Animation(0.4f, TestScreen.atlas.findRegions("spike"));
	}
	@Override
	public void update(float pDelta) {
		animTime+=pDelta;
		if(animTime>animation.animationDuration){animTime-=animation.animationDuration;}
	}
	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(animation.getKeyFrame(animTime),x-offsetX,y-offsetY);
	}
	@Override
	public void hitByPlayer(boolean pX) {
		level.restart();
	}
}
