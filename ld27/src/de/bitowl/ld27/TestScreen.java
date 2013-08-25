package de.bitowl.ld27;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class TestScreen extends AbstractScreen {


	
	Level level;
	
	float spawnCounter;
	
	BitmapFont font;
	// TODO handle them somewhere else
	// TODO put them in a texture atlas
	static Texture playerT;
	static Texture bulletT;
	static Texture enemyT;
	static Texture chestT;
	static Texture chestOpenT;
	static Texture barrelT;
	static Texture barrelOpenT;
	static Texture triggerT;
	static Texture trigger2T;
	static Texture exitT;
	static Texture exitOpenT;
	static Texture wallT;
	static Texture wallOpenT;
	static Texture crateT;
	static Texture crateDestroyedT;
	static Texture pressurePlateT;
	static Texture pressurePlateDownT;
	static Texture pressurePlatehT;
	static Texture pressurePlatehDownT;
	static Texture mirrorUpLeftT;
	static Texture mirrorUpRightT;
	static Texture mirrorDownLeftT;
	static Texture mirrorDownRightT;
	static Texture spikeT;
	static TextureAtlas atlas;
	
	public int levelNr;
	
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
		exitT=new Texture(Gdx.files.internal("images/exit.png"));
		exitOpenT=new Texture(Gdx.files.internal("images/exit_open.png"));
		wallT=new Texture(Gdx.files.internal("images/wall.png"));
		wallOpenT=new Texture(Gdx.files.internal("images/wall_down.png"));
		crateT=new Texture(Gdx.files.internal("images/crate.png"));
		crateDestroyedT=new Texture(Gdx.files.internal("images/crate_destroyed.png"));
		pressurePlateT=new Texture(Gdx.files.internal("images/pressureplate.png"));
		pressurePlateDownT=new Texture(Gdx.files.internal("images/pressureplate_down.png"));
		mirrorUpLeftT=new Texture(Gdx.files.internal("images/mirror_lu.png"));
		mirrorUpRightT=new Texture(Gdx.files.internal("images/mirror_ru.png"));
		mirrorDownLeftT=new Texture(Gdx.files.internal("images/mirror_ld.png"));
		mirrorDownRightT=new Texture(Gdx.files.internal("images/mirror_rd.png"));
		pressurePlatehT=new Texture(Gdx.files.internal("images/pressureplateh.png"));
		pressurePlatehDownT=new Texture(Gdx.files.internal("images/pressureplateh_down.png"));
		spikeT=new Texture(Gdx.files.internal("images/spikes.png"));
		
		atlas=new TextureAtlas(Gdx.files.internal("images/textures.atlas"));
		
		//playerT.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		levelNr=0;
		level=new Level(levelNr);
		
		Gdx.input.setInputProcessor(new KeyboardControl());
		Controllers.addListener(new GamepadControl());
		
		Texture texture = new Texture(Gdx.files.internal("fonts/first.png"));
		//texture.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear); 
		texture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		
		font=new BitmapFont(Gdx.files.internal("fonts/first.fnt"),new TextureRegion(texture),false);
		//font=new BitmapFont();
		//font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		

	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		spawnCounter+=delta;
		if(spawnCounter>10){
			spawnCounter-=10;
			// SPAWN DA ENEMIES
			int x;
			int y;
			do{
			x=MathUtils.random(level.mapWidth-1);
			y=MathUtils.random(level.mapHeight-1);
			}while(level.collisionLayer.getCell(x, y)!=null&&level.collisionLayer.getCell(x, y).getTile().getId()!=0);// search for a free space
			level.spawnEnemy(x,y);
		}
		
		
		Gdx.graphics.setTitle(Gdx.graphics.getFramesPerSecond()+" fps");
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
//		camera.position.x=0;
	//	camera.position.y=0;
		//camera.update();
		//batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//Gdx.gl10.glEnable(GL10.GL_ALPHA_TEST);
		//Gdx.gl10.glAlphaFunc(GL10.GL_GREATER, 0.45f);
		if(spawnCounter<9){
			font.draw(batch, (10-(int)spawnCounter)+" seconds", 0,0);
		}else{
			font.draw(batch, (10-(int)spawnCounter)+" second", 0,0);
		}
		
		font.draw(batch,"status: still alive",130,0);
		
		
		
		//if(level.description!=null){
			font.draw(batch,level.description,0,level.height+font.getLineHeight());
		//	font.draw(batch,level.description,0,level.height+font.getLineHeight());
			
		//}
			//batch.flush();
			//Gdx.gl10.glDisable(GL10.GL_ALPHA_TEST);
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
		exitT.dispose();
		exitOpenT.dispose();
		wallT.dispose();
		wallOpenT.dispose();
		crateT.dispose();
		crateDestroyedT.dispose();
		pressurePlateT.dispose();
		pressurePlateDownT.dispose();
		mirrorDownLeftT.dispose();
		mirrorDownRightT.dispose();
		mirrorUpLeftT.dispose();
		mirrorUpRightT.dispose();
		pressurePlatehT.dispose();
		pressurePlatehDownT.dispose();
		spikeT.dispose();
		
		
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

	public void nextLevel() {
		levelNr++;
		level=new Level(levelNr);
		spawnCounter=0;
		throw new RuntimeException("interrupted by level change"); // I know you don't do this like this, but it's easy and works :P
	}

	public void restartLevel() {
		level=new Level(levelNr);
		spawnCounter=0;
		throw new RuntimeException("interrupted by level change"); // I know you don't do this like this, but it's easy and works :P		
	}
	
	
}
