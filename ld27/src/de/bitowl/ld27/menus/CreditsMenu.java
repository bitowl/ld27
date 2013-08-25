package de.bitowl.ld27.menus;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.bitowl.ld27.LdGame;

public class CreditsMenu extends MenuScreen {
	public CreditsMenu() {
		Label text=new Label("everything created by bitowl",skin);
		table.add(text);
		
		
		
		
		table.addAction(Actions.moveTo(0, -table.getHeight()));
		table.addAction(Actions.sequence(Actions.moveTo(0, table.getHeight(),10f),
				new Action() {
					
					@Override
					public boolean act(float delta) {
						LdGame.switchScreen(new MainMenu());
						return false;
					}
				})
				);
		
	}
}
