package de.bitowl.ld27.menus;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.bitowl.ld27.LdGame;
import de.bitowl.ld27.Options;

public class OptionsMenu extends MenuScreen{
	
	enum OptionKeys{
		NOTHING,
		UP,
		DOWN,
		RIGHT,
		LEFT,
		SHOOT,
		BACK
	};
	OptionKeys listeningKeyboard=OptionKeys.NOTHING;
	
	HashMap<OptionKeys, TextButton> keyboardButtons;
	
	public OptionsMenu() {
		keyboardButtons=new HashMap<OptionKeys, TextButton>();
		
		
		Label upL=new Label("up",skin);
		table.add(upL);
		
		TextButton upK=new TextButton(Options.KEYBOARD_UP+"",skin);
		upK.addListener(new KeyboardBinder(OptionKeys.UP));
		keyboardButtons.put(OptionKeys.UP, upK);
		table.add(upK);
		
		table.row();
		
		
		Label downL=new Label("down",skin);
		table.add(downL);
		
		TextButton downK=new TextButton(Options.KEYBOARD_DOWN+"",skin);
		downK.addListener(new KeyboardBinder(OptionKeys.DOWN));
		keyboardButtons.put(OptionKeys.DOWN, downK);
		table.add(downK);
		
		table.row();
		
		
		Label leftL=new Label("left",skin);
		table.add(leftL);
		
		TextButton leftK=new TextButton(Options.KEYBOARD_LEFT+"",skin);
		leftK.addListener(new KeyboardBinder(OptionKeys.LEFT));
		keyboardButtons.put(OptionKeys.LEFT, leftK);
		table.add(leftK);
		
		table.row();
		
		
		Label rightL=new Label("right",skin);
		table.add(rightL);
		
		TextButton rightK=new TextButton(Options.KEYBOARD_RIGHT+"",skin);
		rightK.addListener(new KeyboardBinder(OptionKeys.RIGHT));
		keyboardButtons.put(OptionKeys.RIGHT, rightK);
		table.add(rightK);
		
		table.row();
		
		
		Label shootL=new Label("shoot",skin);
		table.add(shootL);
		
		TextButton shootK=new TextButton(Options.KEYBOARD_SHOOT+"",skin);
		shootK.addListener(new KeyboardBinder(OptionKeys.SHOOT));
		keyboardButtons.put(OptionKeys.SHOOT, shootK);
		table.add(shootK);
		
		table.row();
		
		
		Label backL=new Label("back",skin);
		table.add(backL);
		
		TextButton backK=new TextButton(Options.KEYBOARD_BACK+"",skin);
		backK.addListener(new KeyboardBinder(OptionKeys.BACK));
		keyboardButtons.put(OptionKeys.BACK, backK);
		table.add(backK);
		
		table.row().pad(8);
		
		
		TextButton back=new TextButton("back",skin);
		back.addListener(new ClickListener(){@Override
		public void clicked(InputEvent event, float x, float y) {
			// TODO save settings
			LdGame.switchScreen(new MainMenu());
		}});
		table.add(back.pad(7));
		
		Gdx.input.setInputProcessor(new InputMultiplexer(stage,new KeyboardListener()));
		
		
	}
	
	class KeyboardBinder extends ClickListener{
		OptionKeys type;
		public KeyboardBinder(OptionKeys pType) {
			type=pType;
			
		}
		@Override
		public void clicked(InputEvent event, float x, float y) {
			listeningKeyboard=type;
			
		}
	}
	
	class KeyboardListener extends InputAdapter{
		@SuppressWarnings("incomplete-switch")
		@Override
		public boolean keyDown(int keycode) {
			System.err.println("keydown options: "+keycode);
			if(listeningKeyboard==OptionKeys.NOTHING){
				return false;
			}else{
				switch(listeningKeyboard){
					case UP:
						Options.KEYBOARD_UP=keycode;
						break;
					case DOWN:
						Options.KEYBOARD_DOWN=keycode;
						break;
					case LEFT:
						Options.KEYBOARD_LEFT=keycode;
						break;
					case RIGHT:
						Options.KEYBOARD_RIGHT=keycode;
						break;
					case SHOOT:
						Options.KEYBOARD_SHOOT=keycode;
						break;
					case BACK:
						Options.KEYBOARD_BACK=keycode;
						break;
				}
				keyboardButtons.get(listeningKeyboard).setText(keycode+"");
				listeningKeyboard=OptionKeys.NOTHING;
				return true;
			}
		}
	}
}
