package de.bitowl.ld27;


import com.badlogic.gdx.Game;

import de.bitowl.ld27.menus.CreditsMenu;
import de.bitowl.ld27.menus.MainMenu;
import de.bitowl.ld27.menus.MenuScreen;

public class LdGame extends Game {

	/**
	 * just to make stuff easier
	 */
	public static LdGame current;
	
	@Override
	public void create() {
		current=this;
		new TestScreen(); // TODO load assets somewhere else
		setScreen(new CreditsMenu());
	}
	
	
	public static void switchScreen(AbstractScreen pScreen){
		current.setScreen(pScreen);
	}
}
