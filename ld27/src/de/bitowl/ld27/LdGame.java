package de.bitowl.ld27;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LdGame extends Game {

	/**
	 * just to make stuff easier
	 */
	public static LdGame current;
	
	@Override
	public void create() {
		current=this;
		setScreen(new TestScreen());
	}
	
	
	public static void switchScreen(AbstractScreen pScreen){
		current.setScreen(pScreen);
	}
}
