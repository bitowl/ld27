package de.bitowl.ld27;

import de.bitowl.ld27.astar.AStar;
import de.bitowl.ld27.astar.AStarHeuristic;
import de.bitowl.ld27.astar.AreaMap;
import de.bitowl.ld27.astar.ClosestHeuristic;
import de.bitowl.ld27.astar.Path;

public class Enemy extends Entity{

	Path path;
	int pathIndex;
	
	float time;
	public Enemy(float pX,float pY){
		x=pX;
		y=pY;
		width=32;
		height=32;
		damageOnPlayer=1;
		texture=TestScreen.enemyT;
		collidable=true;
		blocking=true;
		
		
		findPlayer();
	}
	@Override
	public void update(float pDelta) {
		time+=pDelta;
		if(path!=null && pathIndex<path.getLength()){
			if(time>1){
				time-=1;
				x=path.getX(pathIndex)*level.tileWidth;
				y=path.getY(pathIndex)*level.tileHeight;
				pathIndex++;
			}else{
				// TODO interpolate
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
		pathIndex=0;
	}
}
