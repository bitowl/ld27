package de.bitowl.ld27;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

/**
 * the current level
 * 
 * @author bitowl
 *
 */
public class Level {

	/**
	 * to make things easier for me :P
	 */
	static Level current;
	
	// map
	TiledMap map;
	TiledMapRenderer renderer;
	int mapWidth;
	int mapHeight;
	int width;
	int height;
	int tileWidth;
	int tileHeight;
	TiledMapTileLayer collisionLayer;
	TiledMapTileLayer connectionsLayer;
	
	// things on the map
	Player player;
	Array<Bullet> bullets;
	Array<Entity> entities;
	
	
	// save all walls to trigger them
	Array<Wall> walls;

	// TMP
	ShapeRenderer debugrenderer;

	public int[][] obstacleMap;
	
	public Level(){
		current=this;
		
		
		
		// add things on the map
		player=new Player();
		
		bullets=new Array<Bullet>();
		entities=new Array<Entity>();
		
		walls=new Array<Wall>();

		player.level=this;
		
		
		// load a test map
		map=new TmxMapLoader().load("maps/testmap.tmx");
		// map.getTileSets().getTile(1).getTextureRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		renderer=new OrthogonalTiledMapRenderer(map);
		
		for(MapLayer layer:map.getLayers()){
			if(layer.getName().equals("collision")){
				//layer.setVisible(false);
				collisionLayer=(TiledMapTileLayer)layer;
				
				mapWidth=collisionLayer.getWidth();
				mapHeight=collisionLayer.getHeight();
				width=(int) (collisionLayer.getWidth()*collisionLayer.getTileWidth());
				height=(int) (collisionLayer.getHeight()*collisionLayer.getTileHeight());
				tileWidth=(int)collisionLayer.getTileWidth();
				tileHeight=(int)collisionLayer.getTileHeight();
				
				// create obstacle map
				obstacleMap=new int[mapHeight][mapWidth];
				for(int y=0;y<mapHeight;y++){
					for(int x=0;x<mapWidth;x++){
						if(collisionLayer.getCell(x, y)!=null){
							obstacleMap[y][x]=collisionLayer.getCell(x, y).getTile().getId();
						}
					}
				}
			}else if(layer.getName().equals("entities")){
				layer.setVisible(false);
				TiledMapTileLayer entlay=(TiledMapTileLayer)layer;
				for(int x=0;x<entlay.getWidth();x++){
					for(int y=0;y<entlay.getHeight();y++){
						if(entlay.getCell(x, y)!=null){
							Entity add=null;
							switch(entlay.getCell(x, y).getTile().getId()){
								case 2:
									Chest chest=new Chest(x,y);
									add=chest;
									break;
								case 3:
									Barrel barrel=new Barrel(x,y);
									add=barrel;
									break;
								case 4:
									Trigger trigger=new Trigger(x,y);
									add=trigger;
									break;
								case 5:
									Exit exit=new Exit(x, y);
									add=exit;
									break;
								case 6:
									Wall wall=new Wall(x,y);
									walls.add(wall);
									add=wall;
									break;
								case 7:
									Crate crate=new Crate(x, y);
									add=crate;
									break;
								case 8:
									PressurePlate plate=new PressurePlate(x, y);
									add=plate;
									break;
									
									
								case 10:
									Mirror mirrorul=new Mirror(x,y,Mirror.UPLEFT);
									add=mirrorul;
									break;
								case 11:
									Mirror mirrorur=new Mirror(x,y,Mirror.UPRIGHT);
									add=mirrorur;
									break;
								case 12:
									Mirror mirrordl=new Mirror(x,y,Mirror.DOWNLEFT);
									add=mirrordl;
									break;
								case 13:
									Mirror mirrordr=new Mirror(x,y,Mirror.DOWNRIGHT);
									add=mirrordr;
									break;
							}
							if(add!=null){
								entities.add(add);
								if(add.blocking){ // TODO moving things that are blocking must check the obstacle map
									obstacleMap[y][x]=1;
								}
							}
						}
					}
				}
			}else if(layer.getName().equals("connections")){
				connectionsLayer=(TiledMapTileLayer)layer;
			}
			
		}
		if(collisionLayer==null){
			throw new RuntimeException("no collision layer found");
		}
		if(connectionsLayer==null){
			throw new RuntimeException("no connections layer found");
		}
		

		

	/*	// spawn chests
		for(int i=0;i<10;i++){
			entities.add(new Chest(MathUtils.random(mapWidth-1), MathUtils.random(mapHeight-1)));
		}
		// spawn barrels (lots of barrels!)
		for(int i=0;i<20;i++){
			entities.add(new Barrel(MathUtils.random(mapWidth-1), MathUtils.random(mapHeight-1)));
		}*/
		
		debugrenderer=new ShapeRenderer();
	}
	
	
	public void update(float delta){
		player.update(delta);
		
		// collision
		
		// borders of the map
		if(player.x<0){player.x=0;}
		if(player.y<0){player.y=0;}
		if(player.x>width){player.x=width;}
		if(player.y>height){player.y=height;}
		for(Bullet bullet:bullets){
		/*	if(outsideScreen(bullet.x, bullet.y, 8, 8)){
				bullets.removeValue(bullet, true);
			}else{*/
				bullet.update(delta);
			}
		//}
		for(Entity entity:entities){
			entity.update(delta);
		}

	}
	
	public void render(OrthographicCamera camera,SpriteBatch batch){
		renderer.setView(camera);
		renderer.render();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		
		for(Entity entity:entities){
			//batch.draw(enemyT,entity.x,entity.y);
			entity.draw(batch);
		}
		
		for(Bullet bullet:bullets){
			bullet.draw(batch);
			//batch.draw(bulletT,bullet.x,bullet.y);
		}
		
		player.draw(batch);
		batch.end();


		Gdx.gl.glEnable(GL10.GL_BLEND);	
	    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		debugrenderer.setProjectionMatrix(camera.combined);
		debugrenderer.begin(ShapeType.Line);
		debugrenderer.setColor(1,0,0,0.4f);
		for(Entity entity:entities){
			debugrenderer.rect(entity.x,entity.y,entity.width,entity.height);
		}
		debugrenderer.setColor(0, 1, 0, 0.4f);
		debugrenderer.rect(player.x, player.y, player.width, player.height);
		debugrenderer.end();
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}
	
	/**
	 * is this Rectangle outside of the screen?
	 */
	public boolean outsideScreen(float pX,float pY,float pWidth,float pHeight){
		if(pX<0){return true;}
		if(pY<0){return true;}
		if(pX+pWidth>width){return true;}
		if(pY+pHeight>height){return true;}
		
		return false;
	}
	
	
	public void spawnEnemy(float pX,float pY){
		entities.add(new Enemy(pX,pY));
	}
	
	
	public void dispose(){
		map.dispose();
	}
	
	public void putPowerOnConnection(int pX,int pY,boolean pOn){
		
		// TODO save connectionsLayer in an int[][] array to do not have to call .getCell(pX,pY).getTile().getId() so often
		
		if(connectionsLayer.getCell(pX, pY)==null){
			System.err.println("not connected");
			return;
		}
		int color=connectionsLayer.getCell(pX, pY).getTile().getId();
		System.out.println("found connection: "+color);
		
		// find all other entities that are powered by this connection
		for(int x=0;x<mapWidth;x++){
			for(int y=0;y<mapHeight;y++){
				if(connectionsLayer.getCell(x,y)!=null){
					if(connectionsLayer.getCell(x,y).getTile().getId()==color){
						System.out.println("connectionpoint at "+x+","+y);
						// find the entity, that is here
						for(Entity entity:entities){
							if(entity.x==x*tileWidth&&entity.y==y*tileHeight){
								entity.powerByConnection(pOn);
								break;
							}
						}
						
					}
				}
			}
		}
	}
}
