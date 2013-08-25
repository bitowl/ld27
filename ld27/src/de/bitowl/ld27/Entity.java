package de.bitowl.ld27;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;

public class Entity implements Comparable<Entity>{
	
	int z=1000; // default is faaar back
	
	float x;
	float y;
	int width;
	int height;
	
	int offsetX;
	int offsetY;
	
	float speedX;
	float speedY;
	
	float damageOnPlayer;

	Level level;
	float SPEED=200;
	
	AtlasRegion texture;
	
	float newX;
	float newY;
	
	
	/*
	 * for search of the connection
	 */
	int tileX;
	int tileY;
	
	/**
	 * if this entity sends power (true) or might recieve power (false)
	 */
	boolean sendsPower;
	boolean powerState; // the power state of powersending entities
	
	/*
	 * only true for the player
	 * maybe later if monsters can hit monsters
	 */
	boolean testOnOtherEntities;
	
	/**
	 * can the player collide with this entity
	 * see hitByPlayer
	 */
	boolean collidable;
	/**
	 * does this entity block the way of the player
	 */
	boolean blocking;
	
	public Entity(){
		level=Level.current;
		
	}
	
	public Rectangle getRectangle(){
		// TODO do not always create a new one
		return new Rectangle(x,y,width,height);
	
	}
	public Rectangle getRectangle(float pX,float pY){
		return new Rectangle(pX,pY,width,height);
	}
	
	// HEAVY collision code ^^
	// simply test everything on everything and everyone is happy
	public void update(float pDelta){
		
		// precalculate
		newX=x+speedX*SPEED*pDelta;
		newY=y+speedY*SPEED*pDelta;
		
		// on which tile will we be (our left, bottom corner)
		int xtile=(int) (newX/level.tileWidth);
		int ytile=(int) (newY/level.tileHeight);
		
		
		// check if we can go in y direction
		boolean noentcol=true; // no collision with an entity
		if(testOnOtherEntities){
			Entity entity=checkEntities(x,newY,false);
			if(entity!=null){
				if(speedY>0){
					y=entity.y-height;
				}else{
					y=entity.y+entity.height;
				}
				noentcol=false;
			}
		}
		if(noentcol&&speedY!=0){
			getMyCorners(x, newY);
			
	
			if(speedY>0){
				if(isFree(upleft) && isFree(upright)){
					y=newY;
				}else{
					y= (ytile+1)*level.tileHeight-height;
					hitWall(false);
				}
			}else if(speedY<0){
				if(isFree(downleft)&& isFree(downright)){
					y=newY;
				}else{
					y= (ytile+1)*level.tileHeight+1;
					hitWall(false);
				}
			}
		}
		noentcol=true;
		if(speedX!=0&&testOnOtherEntities){
			Entity entity=checkEntities(newX,y,true);
			if(entity!=null){
				if(speedX>0){
					x=entity.x-width;
				}else{
					x=entity.x+entity.width;
				}

				noentcol=false;
			}
		}
	
		if(noentcol&&speedX!=0){
			
			getMyCorners(newX, y);
			
			
			if(speedX<0){
				if(isFree(downleft) && isFree(upleft) ){
					x = newX;
				}else{
					x=(xtile+1)*level.tileWidth;
					hitWall(true);
				}
			}else{
				if(isFree(downright) && isFree(upright) ){
					x = newX;
				}else{
					x=(xtile+1)*level.tileWidth-width-1;
					hitWall(true);
				}
			}
		}
		
		if(x<0){
			hitWall(true);
			x=0;
		}
		if(y<0){
			hitWall(false);
			y=0;
		}
		
	}
	
	int upleft,downleft,upright,downright;
	
	public boolean isFree(int pTile){
		if(this instanceof Bullet){
			return pTile!=1;
		}else{
			return pTile==0;
		}
	}
	
	public void getMyCorners(float pX,float pY){
		// calculate on which tiles our "edges" are
		int downY=(int) Math.floor(level.mapHeight-pY/level.tileHeight);
		int upY=(int) Math.floor(level.mapHeight- (pY+height)/level.tileHeight);
		int leftX=(int) Math.floor(pX/level.tileWidth);
		int rightX=(int) Math.floor( (pX+width)/level.tileWidth);
		
		// look which tiles are on our corners
		upleft=getTile(leftX,upY);
		downleft=getTile(leftX,downY);
		upright=getTile(rightX,upY);
		downright=getTile(rightX,downY);
	}
	
	/**
	 * returns a tile
	 * TODO make collisionLayer an int[][]-array so this is faster
	 */
	public int getTile(int pX,int pY){
		if(pX<0){return 0;}
		if(pY<0){return 1;}
		if(pX>level.mapWidth-1){return 1;}
		if(pY>level.mapHeight){return 1;}

		if(level.collisionLayer.getCell(pX, level.collisionLayer.getHeight()-pY-1)==null){
			return 0;
		}else{
			return level.collisionLayer.getCell(pX, level.collisionLayer.getHeight()-pY-1).getTile().getId();
		}
		
	}
	
	/**
	 * checks collisions with other entities
	 * @return true if collision 
	 */
	public Entity checkEntities(float pX,float pY,boolean x){
		for(Entity entity:level.entities){
			if(entity.collidable && entity!=this && entity.getRectangle().overlaps(getRectangle(pX,pY))){
				
				hitEntity(entity,x);
				
				if(entity instanceof Mirror && this instanceof Bullet){
					// bullets should not be blocked by mirrors
					return null;
				}
				
				if(entity.blocking){
					return entity;
				}
			}
		}
		return null;
	}
	
	/**
	 * checks collisions with other entities without calling hitEntity();
	 * @param pX
	 * @param pY
	 * @return
	 */
	public Entity checkEntities(float pX,float pY){
		for(Entity entity:level.entities){
			if(entity.collidable && entity!=this && entity.getRectangle().overlaps(getRectangle(pX,pY))){			
				if(entity.blocking){
					return entity;
				}
			}
		}
		return null;
	}
	/**
	 * returns a colliding entity even if it's not blocking (for barrel on pressure plate)
	 */
	public Entity getEntity(float pX,float pY){
		for(Entity entity:level.entities){
			if(entity.collidable && entity!=this && entity.getRectangle().overlaps(getRectangle(pX,pY))){			
				return entity;
			}
		}
		return null;
	}
	
	/**
	 * this entity hit another entity
	 */
	public void hitEntity(Entity pEntity,boolean pX){
		
	}
	/**
	 * this entity hit a wall
	 */
	public void hitWall(boolean pX){
		
	}
	
	/**
	 * this entity is being hit by the player (how dare he?)
	 * @param pX 
	 */
	public void hitByPlayer(boolean pX){
		
	}
	public void hitByBullet(){
		
	}
	
	
	public void draw(SpriteBatch batch){
		batch.draw(texture,x-offsetX,y-offsetY);
	}
	
	
	
	
	public final int LEFT=0;
	public final int RIGHT=1;
	public final int UP=2;
	public final int DOWN=3;
	
	/* collision test for moved objects */
	public boolean canMoveTo(float pX,float pY,int pMove){
		getMyCorners(pX, pY);
		
		switch(pMove){
			case LEFT:
				if(downleft == 0 && upleft == 0){
					return checkEntities(pX,pY)==null;
				}else{
					return false;
				}
			case RIGHT:
				if(downright == 0 && upright == 0){
					return checkEntities(pX,pY)==null;
				}else{
					return false;
				}
			case UP:
				if(upright == 0 && upleft == 0){
					return checkEntities(pX,pY)==null;
				}else{
					return false;
				}
			case DOWN:
				if(downright == 0 && downleft == 0){
					return checkEntities(pX,pY)==null;
				}else{
					return false;
				}
		}
		
			
		return false;
	}
	
	/**
	 * some trigger/pressureplate has powered this thing via a colored connection
	 * 
	 * @param pOn
	 */
	public void powerByConnection(boolean pOn){
		
	}
	/**
	 * this trigger/pressureplat wants to power a color-connection
	 * @param pOn
	 */
	public void powerConnection(boolean pOn){
		powerState=pOn;
		level.putPowerOnConnection((int)(x/level.tileWidth), (int)(y/level.tileHeight), pOn);
	}

	
	
	@Override
	/**
	 * the smaller the z the later it should be rendered
	 */
	public int compareTo(Entity o) {
		if(z<o.z){
			return 1;
		}else if(z>o.z){
			return -1;
		}else{
			return 0;
		}
	}
}
