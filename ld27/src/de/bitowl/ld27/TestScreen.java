package de.bitowl.ld27;


import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import de.bitowl.ld27.menus.CreditsMenu;
import de.bitowl.ld27.menus.MainMenu;

public class TestScreen extends AbstractScreen {

	
	Level level;
	
	float spawnCounter; // when do the enemies spawn?
	int spawnAmount=1; // how many enemies spawn this time
	
	BitmapFont font;
	
	static TextureAtlas atlas;
	
	public int levelNr;
	float levelTime;
	
	
	public TestScreen(int pLevel) {
		
		atlas=new TextureAtlas(Gdx.files.internal("images/textures.atlas"));
		
		
		levelNr=pLevel;
		level=new Level(levelNr);
		levelTime=0;
		Gdx.input.setInputProcessor(new KeyboardControl());
		
		
		Texture texture = new Texture(Gdx.files.internal("fonts/first.png"));
		texture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		
		font=new BitmapFont(Gdx.files.internal("fonts/first.fnt"),new TextureRegion(texture),false);

	}
	
	public TestScreen(){
		this(0);
	}
	@Override
	public void render(float delta) {
		super.render(delta);
		
		levelTime+=delta;
		spawnCounter+=delta;
		if(spawnCounter>10){
			spawnCounter-=10;
			// SPAWN DA ENEMIES
			int x;
			int y;
			for(int i=0;i<spawnAmount;i++){
				do{
					x=MathUtils.random(level.mapWidth-1);
					y=MathUtils.random(level.mapHeight-1);
				}while(level.collisionLayer.getCell(x, y)!=null&&level.collisionLayer.getCell(x, y).getTile().getId()!=0  && !new Rectangle(x*level.tileWidth,y*level.tileHeight,32,32).overlaps(level.player.getRectangle()));// search for a free space
				level.spawnEnemy(x,y);
			}
			spawnAmount++;
		}
		
		
		try{
			level.update(delta);
		}catch(RuntimeException e){
			if(!e.getMessage().equals("interrupted by level change")){
				throw e;
			}
			// do not render now, switching levels
			return;
		}

		// "scroll" with the player :P
		camera.position.x=level.player.x;
		camera.position.y=level.player.y;
		
		camera.update();
		
		
		
		level.render(camera,batch);
		

		
		
		// HUD
		batch.begin();
		if(spawnCounter<9){
			font.draw(batch, (10-(int)spawnCounter)+" seconds", 0,0);
		}else{
			font.draw(batch, (10-(int)spawnCounter)+" second", 0,0);
		}
		
		font.draw(batch,"status: still alive",130,0);
		
		font.draw(batch,level.description,0,level.height+font.getLineHeight());

		batch.end();
		
	}

	@Override
	public void dispose() {
		super.dispose();
		atlas.dispose();
		
		
		level.dispose();
	}
	
	public void shoot(){
		Bullet bullet=new Bullet(level.player.x+13,level.player.y+12,MathUtils.cos(level.player.angle),MathUtils.sin(level.player.angle));
		bullet.level=level;
		level.bullets.add(bullet);
	}
	

	
	
	class KeyboardControl extends InputAdapter{
		public KeyboardControl() {
			System.err.println("-\n–\n-\nKEYBOARD CONTROLL");
		}
		@Override
		public boolean keyDown(int keycode) {
			System.err.println("KEY DOWN: "+keycode);
			if(keycode==Options.KEYBOARD_LEFT){
				level.player.speedX=-1;
			}else if(keycode==Options.KEYBOARD_RIGHT){
				level.player.speedX=1;
			}else if(keycode==Options.KEYBOARD_DOWN){
				level.player.speedY=-1;
			}else if(keycode==Options.KEYBOARD_UP){
				level.player.speedY=1;
			}else if(keycode==Options.KEYBOARD_SHOOT){
				shoot();
			}else if(keycode==Options.KEYBOARD_BACK){
				LdGame.switchScreen(new MainMenu());
			}
			return false;
		}
		@Override
		public boolean keyUp(int keycode) {
			if(keycode==Options.KEYBOARD_LEFT || keycode==Options.KEYBOARD_RIGHT){
				level.player.speedX=0;
			}else if(keycode==Options.KEYBOARD_DOWN || keycode==Options.KEYBOARD_UP){
				level.player.speedY=0;
			}
			return false;
		}
	}
	
	/**
	 * epic controls via gamepad
	 * just because I now have one
	 *   
	 * @author bitowl
	 *
	 */
	class GamepadControl extends ControllerAdapter{

		@Override
		public boolean axisMoved(Controller controller, int axisIndex,
				float value) {
			
			// add a dead zone
			if(Math.abs(value)<0.1){value=0;}
			
			if(axisIndex==Options.HORIZONTAL_AXIS_POS){
				if(Options.HORIZONTAL_AXIS_POS_V==value>0 || value==0){
					level.player.speedX=value*(Options.HORIZONTAL_AXIS_POS_V?1:-1);
				}
			}
				
			if(axisIndex==Options.HORIZONTAL_AXIS_NEG){
				if(Options.HORIZONTAL_AXIS_NEG_V==value>0 || value==0){
					level.player.speedX=value*(Options.HORIZONTAL_AXIS_NEG_V?-1:1);
				}
			}
					
			
			if(axisIndex==Options.VERTICAL_AXIS_POS){
				if(Options.VERTICAL_AXIS_POS_V==value>0 || value==0){
					level.player.speedY=value*(Options.VERTICAL_AXIS_POS_V?1:-1);
				}
			}
				
			if(axisIndex==Options.VERTICAL_AXIS_NEG){
				if(Options.VERTICAL_AXIS_NEG_V==value>0 || value==0){
					level.player.speedY=value*(Options.VERTICAL_AXIS_NEG_V?-1:1);
				}
			}
			return false;
		}
		@Override
		public boolean buttonDown(Controller controller, int buttonIndex) {
			
			if(buttonIndex==Options.CONTROLLER_SHOOT){
				shoot();
			}else if(buttonIndex==Options.CONTROLLER_BACK){
				// TODO pause menu  what for? there is no loss in 10 seconds :P
				LdGame.switchScreen(new MainMenu());
			}
			return false;
		}
	}

	public void nextLevel() {
		levelNr++;
		
		if(levelNr>Options.LEVEL_COUNT){
			// TODO win message NOPE. 10 seconds credits have to be enough :P
			LdGame.switchScreen(new CreditsMenu());
			throw new RuntimeException("interrupted by level change"); // I know you don't do this like this, but it's easy and works :P
		}
		
		Preferences prefs=Gdx.app.getPreferences("level");
		prefs.putBoolean("lvl"+levelNr, true);
		prefs.flush();
		
		level=new Level(levelNr);
		spawnCounter=0;
		spawnAmount=1;
		levelTime=0;
		throw new RuntimeException("interrupted by level change"); // I know you don't do this like this, but it's easy and works :P
	}

	public void restartLevel() {
		level=new Level(levelNr);
		spawnCounter=0;
		spawnAmount=1;
		levelTime=0;
		throw new RuntimeException("interrupted by level change"); // I know you don't do this like this, but it's easy and works :P		
	}
	
	GamepadControl control;
	@Override
	public void show() {
		control=new GamepadControl();
		Controllers.addListener(control);
	}
	@Override
	public void hide() {
		Controllers.removeListener(control);
	}
}
