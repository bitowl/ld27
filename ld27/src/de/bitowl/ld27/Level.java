package de.bitowl.ld27;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
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
	
	// things on the map
	Player player;
	Array<Bullet> bullets;
	Array<Entity> entities;
	

	// TMP
	ShapeRenderer debugrenderer;
	
	public Level(){
		current=this;
		
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
				
				break;
			}
		}
		if(collisionLayer==null){
			throw new RuntimeException("no collision layer found");
		}
		
		
		// add things on the map
		player=new Player();
		
		bullets=new Array<Bullet>();
		entities=new Array<Entity>();

		player.level=this;
		

		// spawn chest
		for(int i=0;i<20;i++){
			entities.add(new Chest(MathUtils.random(mapWidth), MathUtils.random(mapHeight)));
		}
		
		
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
			if(outsideScreen(bullet.x, bullet.y, 8, 8)){
				bullets.removeValue(bullet, true);
			}else{
				bullet.update(delta);
			}
		}

	}
	
	public void render(OrthographicCamera camera,SpriteBatch batch){
		renderer.setView(camera);
		renderer.render();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		player.draw(batch);
		
		for(Bullet bullet:bullets){
			bullet.draw(batch);
			//batch.draw(bulletT,bullet.x,bullet.y);
		}
		
		for(Entity entity:entities){
			//batch.draw(enemyT,entity.x,entity.y);
			entity.draw(batch);
		}
		
		batch.end();
		debugrenderer.setProjectionMatrix(camera.combined);
		debugrenderer.begin(ShapeType.Line);
		debugrenderer.setColor(1,0,0,1);
		for(Entity entity:entities){
			debugrenderer.rect(entity.x,entity.y,entity.width,entity.height);
		}
		debugrenderer.end();
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
}
