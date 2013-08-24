package de.bitowl.ld27;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

/**
 * the current level
 * 
 * @author bitowl
 *
 */
public class Level {

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
	
	// TODO handle them somewhere else
	Texture playerT;
	Texture bulletT;
	Texture enemyT;
	
	public Level(){
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
		
		//TODO handle textures somewhere else
		playerT=new Texture(Gdx.files.internal("images/player.png"));
		bulletT=new Texture(Gdx.files.internal("images/bullet.png"));
		enemyT=new Texture(Gdx.files.internal("images/enemy.png"));
		
		playerT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	
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
		batch.draw(playerT, player.x, player.y);
		
		for(Bullet bullet:bullets){
			batch.draw(bulletT,bullet.x,bullet.y);
		}
		
		for(Entity entity:entities){
			batch.draw(enemyT,entity.x,entity.y);
		}
		
		batch.end();
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
		
		
		// TODO handle textures somewhere else
		playerT.dispose();
		bulletT.dispose();
		enemyT.dispose();
	}
}
