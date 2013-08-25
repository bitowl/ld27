package de.bitowl.ld27.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.bitowl.ld27.LdGame;
import de.bitowl.ld27.TestScreen;

public class MainMenu extends MenuScreen {
	public MainMenu() {
		TextButton start=new TextButton("start game", skin);
		start.addListener(new ClickListener(){public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
			LdGame.switchScreen(new TestScreen());
		}
		}
		);
		table.add(start.pad(7)).row();
		
		TextButton options=new TextButton("options", skin);
		options.addListener(new ClickListener(){public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
			LdGame.switchScreen(new OptionsMenu());
		}
		}
		);
		table.add(options.pad(7)).row();
		
		TextButton credits=new TextButton("credits",skin);
		table.add(credits.pad(7)).row();
		
		TextButton quit=new TextButton("quit",skin);
		quit.addListener(new ClickListener(){public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
			Gdx.app.exit();
		}});
		table.add(quit.pad(7)).row();
	}
}
