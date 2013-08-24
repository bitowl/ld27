package de.bitowl.ld27;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class TestScreen extends AbstractScreen {


	
	Level level;
	
	float spawnCounter;
	
	BitmapFont font;
	// TODO handle them somewhere else
	static Texture playerT;
	static Texture bulletT;
	static Texture enemyT;
	static Texture chestT;
	static Texture chestOpenT;
	static Texture barrelT;
	static Texture barrelOpenT;
	static Texture triggerT;
	static Texture trigger2T;
	
	public TestScreen() {

		//TODO handle textures somewhere else
		playerT=new Texture(Gdx.files.internal("images/player.png"));
		bulletT=new Texture(Gdx.files.internal("images/bullet.png"));
		enemyT=new Texture(Gdx.files.internal("images/enemy.png"));
		chestT=new Texture(Gdx.files.internal("images/chest.png"));
		chestOpenT=new Texture(Gdx.files.internal("images/chest_open.png"));
		barrelT=new Texture(Gdx.files.internal("images/barrel.png"));
		barrelOpenT=new Texture(Gdx.files.internal("images/barrel_open.png"));
		triggerT=new Texture(Gdx.files.internal("images/trigger.png"));
		trigger2T=new Texture(Gdx.files.internal("images/trigger_triggered.png"));
		
		playerT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		level=new Level();
		
		Gdx.input.setInputProcessor(new KeyboardControl());
		Controllers.addListener(new GamepadControl());
		
		font=new BitmapFont();
		

	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		spawnCounter+=delta;
		if(spawnCounter>10){
			spawnCounter-=10;
			// SPAWN DA ENEMIES
			level.spawnEnemy(MathUtils.random(level.width),MathUtils.random(level.height));
			
			
			// TODO only spawn on free tiles
			// TODO maybe only spawn on a tile? I mean like centered in a tile like :D
		}
		
		
		Gdx.graphics.setTitle(Gdx.graphics.getFramesPerSecond()+" fps");
		level.update(delta);

		// "scroll" with the player :P
		camera.position.x=level.player.x;
		camera.position.y=level.player.y;
		
		camera.update();
		
		
		
		level.render(camera,batch);
		

		
		
		// HUD
//		camera.position.x=0;
	//	camera.position.y=0;
		//camera.update();
		//batch.setProjectionMatrix(camera.combined);
		batch.begin();
		if(spawnCounter<9){
			font.draw(batch, (10-(int)spawnCounter)+" seconds", 0,0);
		}else{
			font.draw(batch, (10-(int)spawnCounter)+" second", 0,0);
		}
		
		font.draw(batch,"life: "+level.player.life,100,0);
		batch.end();
		
	}

	@Override
	public void dispose() {
		super.dispose();
		
		// TODO handle textures somewhere else
		playerT.dispose();
		bulletT.dispose();
		enemyT.dispose();
		chestT.dispose();
		chestOpenT.dispose();
		barrelT.dispose();
		barrelOpenT.dispose();
		triggerT.dispose();
		trigger2T.dispose();
		
		level.dispose();
	}
	
	public void shoot(){
		// TODO shoot them from center of the player?
		// TODO put this code somewhere else where it fits better
		Bullet bullet=new Bullet(level.player.x+17,level.player.y+17,MathUtils.cos(level.player.angle),MathUtils.sin(level.player.angle));
		bullet.level=level;
		level.bullets.add(bullet);
	}
	

	
	
	class KeyboardControl extends InputAdapter{
		@Override
		public boolean keyDown(int keycode) {
			switch(keycode){
				case Keys.LEFT:
					level.player.speedX=-1;
					break;
				case Keys.RIGHT:
					level.player.speedX=1;
					break;
				case Keys.DOWN:
					level.player.speedY=-1;
					break;
				case Keys.UP:
					level.player.speedY=1;
					break;
				case Keys.SPACE:
					shoot();
				break;
			}
			return false;
		}
		@Override
		public boolean keyUp(int keycode) {
			switch(keycode){
				case Keys.LEFT:
				case Keys.RIGHT:
					level.player.speedX=0;
					break;
				case Keys.DOWN:
				case Keys.UP:
					level.player.speedY=0;
					break;
			}
			return false;
		}
	}
	
	/**
	 * epic controls via gamepad
	 * just because I now have one
	 * 
	 * TODO make an even more epic option screen to support every gamepad :D
	 *  
	 * @author bitowl
	 *
	 */
	class GamepadControl extends ControllerAdapter{
		// just inserted my values here
		// TODO add an option screen for this
		final int HORIZONTAL_AXIS_POS=0;boolean HORIZONTAL_AXIS_POS_V=true;
		final int HORIZONTAL_AXIS_NEG=0;boolean HORIZONTAL_AXIS_NEG_V=false;
		
		final int VERTICAL_AXIS_POS=1;boolean VERTICAL_AXIS_POS_V=false;
		final int VERTICAL_AXIS_NEG=1;boolean VERTICAL_AXIS_NEG_V=true;
		
		int SHOOT_BULLET=1;

		@Override
		public boolean axisMoved(Controller controller, int axisIndex,
				float value) {
			
			// add a dead zone
			if(Math.abs(value)<0.1){value=0;}
			
			if(axisIndex==HORIZONTAL_AXIS_POS){
				if(HORIZONTAL_AXIS_POS_V==value>0 || value==0){
					level.player.speedX=value*(HORIZONTAL_AXIS_POS_V?1:-1);
				}
			}
				
			if(axisIndex==HORIZONTAL_AXIS_NEG){
				if(HORIZONTAL_AXIS_NEG_V==value>0 || value==0){
					level.player.speedX=value*(HORIZONTAL_AXIS_NEG_V?-1:1);
				}
			}
					
			
			if(axisIndex==VERTICAL_AXIS_POS){
				if(VERTICAL_AXIS_POS_V==value>0 || value==0){
					level.player.speedY=value*(VERTICAL_AXIS_POS_V?1:-1);
				}
			}
				
			if(axisIndex==VERTICAL_AXIS_NEG){
				if(VERTICAL_AXIS_NEG_V==value>0 || value==0){
					level.player.speedY=value*(VERTICAL_AXIS_NEG_V?-1:1);
				}
			}
			return false;
		}
		@Override
		public boolean buttonDown(Controller controller, int buttonIndex) {
			
			if(buttonIndex==SHOOT_BULLET){
				shoot();
			}
			return false;
		}
	}
	
	
}
