package de.bitowl.ld27;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import de.bitowl.ld27.menus.MainMenu;

public class LdGame extends Game {

	/**
	 * just to make stuff easier
	 */
	public static LdGame current;
	
	@Override
	public void create() {
		current=this;
		
		Options.restore();
		
		new TestScreen(); // TODO load assets somewhere else
		setScreen(new MainMenu());
	}
	
	
	public static void switchScreen(AbstractScreen pScreen){
		current.getScreen().dispose();
		current.setScreen(pScreen);
	}
	public void dispose() {
		getScreen().dispose();
	};
}
