package de.bitowl.ld27.menus;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
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
	OptionKeys listeningController=OptionKeys.NOTHING;
	
	HashMap<OptionKeys, TextButton> keyboardButtons;
	HashMap<OptionKeys, TextButton> controllerButtons;
	
	public OptionsMenu() {
		keyboardButtons=new HashMap<OptionKeys, TextButton>();
		controllerButtons=new HashMap<OptionKeys, TextButton>();
		
		
		Label upL=new Label("up",skin);
		table.add(upL);
		
		TextButton upK=new TextButton(Options.KEYBOARD_UP+"",skin);
		upK.addListener(new KeyboardBinder(OptionKeys.UP));
		keyboardButtons.put(OptionKeys.UP, upK);
		table.add(upK);
		
		TextButton upC=new TextButton(Options.HORIZONTAL_AXIS_POS+" "+(Options.HORIZONTAL_AXIS_POS_V?"+":"-"),skin);
		upC.addListener(new ControllerBinder(OptionKeys.UP));
		controllerButtons.put(OptionKeys.UP, upC);
		table.add(upC);
		
		table.row();
		
		
		Label downL=new Label("down",skin);
		table.add(downL);
		
		TextButton downK=new TextButton(Options.KEYBOARD_DOWN+"",skin);
		downK.addListener(new KeyboardBinder(OptionKeys.DOWN));
		keyboardButtons.put(OptionKeys.DOWN, downK);
		table.add(downK);
		
		TextButton downC=new TextButton(Options.HORIZONTAL_AXIS_NEG+" "+(Options.HORIZONTAL_AXIS_NEG_V?"+":"-"),skin);
		downC.addListener(new ControllerBinder(OptionKeys.DOWN));
		controllerButtons.put(OptionKeys.DOWN, downC);
		table.add(downC);
		
		table.row();
		
		
		Label leftL=new Label("left",skin);
		table.add(leftL);
		
		TextButton leftK=new TextButton(Options.KEYBOARD_LEFT+"",skin);
		leftK.addListener(new KeyboardBinder(OptionKeys.LEFT));
		keyboardButtons.put(OptionKeys.LEFT, leftK);
		table.add(leftK);
		
		TextButton leftC=new TextButton(Options.VERTICAL_AXIS_NEG+" "+(Options.VERTICAL_AXIS_NEG_V?"+":"-"),skin);
		leftC.addListener(new ControllerBinder(OptionKeys.LEFT));
		controllerButtons.put(OptionKeys.LEFT, leftC);
		table.add(leftC);
		
		table.row();
		
		
		Label rightL=new Label("right",skin);
		table.add(rightL);
		
		TextButton rightK=new TextButton(Options.KEYBOARD_RIGHT+"",skin);
		rightK.addListener(new KeyboardBinder(OptionKeys.RIGHT));
		keyboardButtons.put(OptionKeys.RIGHT, rightK);
		table.add(rightK);
		
		TextButton rightC=new TextButton(Options.VERTICAL_AXIS_POS+" "+(Options.VERTICAL_AXIS_POS_V?"+":"-"),skin);
		rightC.addListener(new ControllerBinder(OptionKeys.RIGHT));
		controllerButtons.put(OptionKeys.RIGHT, rightC);
		table.add(rightC);
		
		table.row();
		
		
		Label shootL=new Label("shoot",skin);
		table.add(shootL);
		
		TextButton shootK=new TextButton(Options.KEYBOARD_SHOOT+"",skin);
		shootK.addListener(new KeyboardBinder(OptionKeys.SHOOT));
		keyboardButtons.put(OptionKeys.SHOOT, shootK);
		table.add(shootK);
		
		TextButton shootC=new TextButton(Options.CONTROLLER_SHOOT+"",skin);
		shootC.addListener(new ControllerBinder(OptionKeys.SHOOT));
		controllerButtons.put(OptionKeys.SHOOT, shootC);
		table.add(shootC);
		
		table.row();
		
		
		Label backL=new Label("back",skin);
		table.add(backL);
		
		TextButton backK=new TextButton(Options.KEYBOARD_BACK+"",skin);
		backK.addListener(new KeyboardBinder(OptionKeys.BACK));
		keyboardButtons.put(OptionKeys.BACK, backK);
		table.add(backK);
		
		TextButton backC=new TextButton(Options.CONTROLLER_BACK+"",skin);
		backC.addListener(new ControllerBinder(OptionKeys.BACK));
		controllerButtons.put(OptionKeys.BACK, backC);
		table.add(backC);
		
		table.row().pad(8);
		
		
		TextButton back=new TextButton("back",skin);
		back.addListener(new ClickListener(){@Override
		public void clicked(InputEvent event, float x, float y) {
			//save settings
			Options.save();
			
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
	
	class ControllerBinder extends ClickListener{
		OptionKeys type;
		public ControllerBinder(OptionKeys pType) {
			type=pType;
			
		}
		@Override
		public void clicked(InputEvent event, float x, float y) {
			listeningController=type;
			
		}
	}
	class ControllerListener extends ControllerAdapter{
		@Override
		public boolean buttonDown(Controller controller, int buttonIndex) {
			if(listeningController==OptionKeys.SHOOT||listeningController==OptionKeys.BACK){
				switch(listeningController){
					case SHOOT:
						Options.CONTROLLER_SHOOT=buttonIndex;
						break;
					case BACK:
						Options.CONTROLLER_BACK=buttonIndex;
						break;
				}
				controllerButtons.get(listeningController).setText(buttonIndex+"");
				listeningController=OptionKeys.NOTHING;
				return true;
			}else{
				return false;
			}
		}
		@Override
		public boolean axisMoved(Controller controller, int axisIndex,
				float value) {
			if(listeningController==OptionKeys.SHOOT||listeningController==OptionKeys.BACK||listeningController==OptionKeys.NOTHING){
				

				return false;
			}else{
				if(Math.abs(value)<0.3f){return false;} //dead zone
				
				switch(listeningController){
					case UP:
						Options.VERTICAL_AXIS_POS=axisIndex;
						Options.VERTICAL_AXIS_POS_V=value>0;
						break;
					case DOWN:
						Options.VERTICAL_AXIS_NEG=axisIndex;
						Options.VERTICAL_AXIS_NEG_V=value>0;
						break;	
					case RIGHT:
						Options.HORIZONTAL_AXIS_POS=axisIndex;
						Options.HORIZONTAL_AXIS_POS_V=value>0;
						break;
					case LEFT:
						Options.HORIZONTAL_AXIS_NEG=axisIndex;
						Options.HORIZONTAL_AXIS_NEG_V=value>0;
						break;
				}
			controllerButtons.get(listeningController).setText(axisIndex+" "+(value>0?"+":"-"));
			listeningController=OptionKeys.NOTHING;
				return true;
			}
		}
	}
	
	ControllerListener listener;
	@Override
	public void hide() {
		Controllers.removeListener(listener);
	}
	@Override
	public void show() {
		listener=new ControllerListener();
		Controllers.addListener(listener);
	}
}
