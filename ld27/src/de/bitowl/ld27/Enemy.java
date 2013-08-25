package de.bitowl.ld27;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.bitowl.ld27.astar.AStar;
import de.bitowl.ld27.astar.AStarHeuristic;
import de.bitowl.ld27.astar.AreaMap;
import de.bitowl.ld27.astar.ClosestHeuristic;
import de.bitowl.ld27.astar.Path;

public class Enemy extends Entity{

	Path path;
	int pathIndex;
	
	float time;
	
	Animation animation;
	float animTime;
	
	public Enemy(float pX,float pY){
		z=3;
		
		x=pX*level.tileWidth;
		y=pY*level.tileHeight;
		width=32;
		height=32;
		damageOnPlayer=1;
		collidable=true;
		blocking=true;
		
		
		findPlayer();
		animation=new Animation(0.2f, TestScreen.atlas.findRegions("enemy"));
	}
	@Override
	public void update(float pDelta) {
		time+=pDelta;
		if(path!=null && pathIndex<path.getLength()){
			if(time>0.5f){
				time-=0.5f;
				x=path.getX(pathIndex)*level.tileWidth;
				y=path.getY(pathIndex)*level.tileHeight;
				pathIndex++;
			}else{
				
				// TODO interpolate
				x=interpol(path.getX(pathIndex-1),path.getX(pathIndex),time*2)*level.tileWidth;
				y=interpol(path.getY(pathIndex-1),path.getY(pathIndex),time*2)*level.tileHeight;
			}
		}else{
			findPlayer();
		}
		/*if(path!=null&&pathIndex<path.getLength()){
			System.out.println(x+" -> "+path.getX(pathIndex)*level.tileWidth);
			if(x<path.getX(pathIndex)*level.tileWidth){
				speedX=1;
			}else if(x>path.getX(pathIndex)*level.tileWidth){
				speedX=-1;
			}else{ // TODO add a small dead zone
				speedX=0;
			}
			
			if(y<path.getY(pathIndex)*level.tileHeight){
				speedY=1;
			}else if(y>path.getY(pathIndex)*level.tileHeight){
				speedY=-1;
			}else{ // TODO add a small dead zone
				speedY=0;
			}
			
			
				
		}else{
			System.err.println("NICHTS ZU LAUFEN");
		}
		super.update(pDelta);*/

		animTime+=pDelta;
		if(animTime>animation.animationDuration){animTime-=animation.animationDuration;}
	}
	@Override
	public void draw(SpriteBatch batch) {
		batch.draw(animation.getKeyFrame(animTime),x-offsetX,y-offsetY);
	}
	
	public float interpol(float v0,float v1,float delta){ // catch dat bad guy!
		return v0+(v1-v0)*delta;
	}
	
	 @Override
	public void hitByPlayer(boolean pX) {
		level.restart();
	}
	@Override
	public void hitByBullet() {
		kill();
	}
	public void kill(){
		level.entities.removeValue(this, true);
	}
	
	public void findPlayer(){
		// search the way to the player
		// http://code.google.com/p/a-star-java/source/browse/AStar/src/aStar/TestAStar.java?r=7
		AreaMap map=new AreaMap(level.mapWidth,level.mapHeight,level.obstacleMap);
		
		AStarHeuristic heuristic = new ClosestHeuristic();
		
		AStar pathFinder = new AStar(map,heuristic);
		
		path=pathFinder.calcShortestPath((int)(x/level.tileWidth),(int)(y/level.tileHeight), (int)(level.player.x/level.tileWidth),(int)(level.player.y/level.tileHeight));
		pathIndex=1;
		time=0;
	}
}
