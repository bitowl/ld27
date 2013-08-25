package de.bitowl.ld27.menus;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.bitowl.ld27.LdGame;
import de.bitowl.ld27.Options;
import de.bitowl.ld27.TestScreen;

public class LevelSelectMenu extends MenuScreen{
	public LevelSelectMenu() {
		Preferences prefs=Gdx.app.getPreferences("level");
		
		table.add(new Label("select level: ",skin));
		
		for(int i=1;i<=Options.LEVEL_COUNT;i++){
			TextButton lvl=new TextButton(""+i,skin);
			if(i==1||prefs.getBoolean("lvl"+i,false)){ // the first level is always enabled
				lvl.addListener(new SelectLevelListener(i));
			}else{
				lvl.setDisabled(true);
				lvl.setColor(0.2f,0.2f,0.2f,1);
				
			}
			table.add(lvl).width(40).height(40).pad(8);
			if(i%10==0){
				table.row();
				table.add(new Label("",skin));
			}
		}
		
		table.row().pad(10);
		
		TextButton back=new TextButton("back",skin);
		back.addListener(new ClickListener(){public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
			LdGame.switchScreen(new MainMenu());
		}});
		table.add(back);
	}
	class SelectLevelListener extends ClickListener{
		int level;
		public SelectLevelListener(int pLevel) {
			level=pLevel;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			LdGame.switchScreen(new TestScreen(level));
		}
	}
}
