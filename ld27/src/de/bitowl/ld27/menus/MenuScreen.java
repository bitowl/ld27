package de.bitowl.ld27.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;

import de.bitowl.ld27.AbstractScreen;

public class MenuScreen extends AbstractScreen {
	Stage stage;
	Skin skin;
	Table table;
	
	static float red;
	static float green;
	static float blue;
	static float colorTimer=10;
	public MenuScreen() {
		stage=new Stage();
		//stage.setCamera(camera);
	//	stage.setViewport(800,480, true);
		
		
		skin=new Skin(Gdx.files.internal("defaultskin.json"));
		
		
		table=new Table();
		table.setSize(800, 480);

		
		
		
		stage.addActor(table);		
		Gdx.input.setInputProcessor(stage);
	}
	@Override
	public void render(float delta) {
		colorTimer+=delta;
		if(colorTimer>10){
			red=MathUtils.random();
			green=MathUtils.random();
			blue=MathUtils.random();
			colorTimer-=10;
		}
	//	super.render(delta);
		Gdx.gl.glClearColor(red,green,blue,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
	}
	@Override
	public void dispose() {
		super.dispose();
		stage.dispose();
		skin.dispose();
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		//super.resize(width, height);
		//stage.setCamera(camera); // TODO :&/
		
		Vector2 size = Scaling.fit.apply(800, 480, width, height);
		System.out.println("FIT: "+size.x+" "+size.y);
		int viewportX = (int)(width - size.x) / 2;
		int viewportY = (int)(height - size.y) / 2;
		int viewportWidth = (int)size.x;
		int viewportHeight = (int)size.y;
		
		Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
		stage.setViewport(800, 480, true, viewportX, viewportY, viewportWidth, viewportHeight);
	}
}
