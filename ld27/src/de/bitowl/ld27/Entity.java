package de.bitowl.ld27;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
	float x;
	float y;
	int width;
	int height;
	
	float speedX;
	float speedY;
	
	float damageOnPlayer;

	Level level;
	float SPEED=200;
	
	Texture texture;
	
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
	// simple test everything on everything and everyone is happy
	public void update(float pDelta){
		
		// precalculate
		float newX=x+speedX*SPEED*pDelta;
		float newY=y+speedY*SPEED*pDelta;
		
		// on which tile will we be (our left, bottom corner)
		int xtile=(int) (newX/level.tileWidth);
		int ytile=(int) (newY/level.tileHeight);
		
		
		// check if we can go in y direction
		boolean noentcol=true; // no collision with an entity
		if(testOnOtherEntities){
			Entity entity=checkEntities(x,newY);
			if(entity!=null){
				if(speedY>0){
					y=entity.y-height;
					//y= (ytile+1)*level.tileHeight-height;
				}else{
					y=entity.y+entity.height;
				//	y= (ytile+1)*level.tileHeight+1;
				}
				noentcol=false;
			}
		}
		if(noentcol&&speedY!=0){
			getMyCorners(x, newY);
			
	
			if(speedY>0){
				if(upleft==0 && upright == 0){
					y=newY;
				}else{
					y= (ytile+1)*level.tileHeight-height;
					hitWall();
				}
			}else if(speedY<0){
				if(downleft==0 && downright ==0){
					y=newY;
				}else{
					y= (ytile+1)*level.tileHeight+1;
					hitWall();
				}
			}
		}
		noentcol=true;
		if(speedX!=0&&testOnOtherEntities){
			Entity entity=checkEntities(newX,y);
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
				if(downleft == 0 && upleft ==0){
					x = newX;
				}else{
					x=(xtile+1)*level.tileWidth;
					hitWall();
				}
			}else{
				if(downright==0 && upright==0){
					x = newX;
				}else{
					x=(xtile+1)*level.tileWidth-width-1;
					hitWall();
				}
			}
		}

		
	}
	
	int upleft,downleft,upright,downright;
	
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
	
	
	public int getTile(int pX,int pY){
		if(pX<0){return 0;}
		if(pY<0){return 1;}
		if(pX>level.mapWidth-1){return 1;}
		if(pY>level.mapHeight){return 1;}
		System.out.println(pX+" "+pY);
		if(level.collisionLayer.getCell(pX, level.collisionLayer.getHeight()-pY-1)==null){
			return 0;
		}else{
			return level.collisionLayer.getCell(pX, level.collisionLayer.getHeight()-pY-1).getTile().getId();
		}
		//System.out.println(collisionLayer.getCell(0,0).toString());
	//	return 1;
		//.getTile().getId();
	}
	
	/**
	 * checks collisions with other entities
	 * @return true if collision 
	 */
	public Entity checkEntities(float pX,float pY){
		for(Entity entity:level.entities){
			if(entity.collidable && entity.getRectangle().overlaps(getRectangle(pX,pY))){
				
				
				// TODO add some "bouncing" code :/ never got that right :/ maybe exploding monsters? :P
				//life-=entity.damageOnPlayer;
				
				//entity.hitByPlayer();
				hitEntity(entity);
				
				if(entity.blocking){
					return entity;
				}
			}
		}
		return null;
	}
	
	/**
	 * this entity hit another entity
	 */
	public void hitEntity(Entity pEntity){
		
	}
	/**
	 * this entity hit a wall
	 */
	public void hitWall(){
		
	}
	
	/**
	 * this entity is being hit by the player (how dare he?)
	 */
	public void hitByPlayer(){
		
	}
	
	
	public void draw(SpriteBatch batch){
		batch.draw(texture,x,y);
	}
}
