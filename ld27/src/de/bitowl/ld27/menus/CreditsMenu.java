package de.bitowl.ld27.menus;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.bitowl.ld27.LdGame;

public class CreditsMenu extends MenuScreen {
	public CreditsMenu() {
		Label text=new Label("code & graphics:",skin,"title");
		table.add(text).row();
		
		Label bitowl=new Label("bitowl",skin);
		table.add(bitowl).row();
		
		Label website=new Label("http://bitowl.de",skin);
		table.add(website).row().padTop(30);
		
		
		Label library=new Label("library:",skin,"title");
		table.add(library).row();
		
		Label libgdx=new Label("libgdx",skin);
		table.add(libgdx).row();
		
		Label libgdxsite=new Label("http://libgdx.badlogicgames.com",skin);
		table.add(libgdxsite).row().padTop(30);
		
		Label astar=new Label("a-star library:",skin,"title");
		table.add(astar).row();
		
		Label aweb=new Label("http://code.google.com/p/a-star-java/",skin);
		table.add(aweb).row();
		
		
		
		
		table.addAction(Actions.moveTo(0, -40));
		table.addAction(Actions.sequence(Actions.moveTo(0, 400,10f),
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
