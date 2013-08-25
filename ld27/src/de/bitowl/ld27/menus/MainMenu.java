package de.bitowl.ld27.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.bitowl.ld27.LdGame;

public class MainMenu extends MenuScreen {
	Label title;
	public MainMenu() {
		
		title=new Label("",skin,"title");
		table.add(title).pad(20).row();
		TextButton start=new TextButton("start game", skin);
		start.addListener(new ClickListener(){public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
		//	LdGame.switchScreen(new TestScreen());
			LdGame.switchScreen(new LevelSelectMenu());
		}
		}
		);
		table.add(start.pad(7)).row().pad(3);
		
		TextButton options=new TextButton("options", skin);
		options.addListener(new ClickListener(){public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
			LdGame.switchScreen(new OptionsMenu());
		}
		}
		);
		table.add(options.pad(7)).row().pad(3);
		
		TextButton credits=new TextButton("credits",skin);
		credits.addListener(new ClickListener(){public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
			LdGame.switchScreen(new CreditsMenu());
		}});
		table.add(credits.pad(7)).row().pad(3);
		
		TextButton quit=new TextButton("quit",skin);
		quit.addListener(new ClickListener(){public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
			Gdx.app.exit();
		}});
		table.add(quit.pad(7)).row();
	}
	@Override
	public void render(float delta) {
		super.render(delta);
		if(colorTimer>9){
			title.setText(10-(int)colorTimer+" second");
		}else{
			title.setText(10-(int)colorTimer+" seconds");
		}
	}
}
