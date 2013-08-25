package de.bitowl.ld27;

import com.badlogic.gdx.Preferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Options {
	public static final int LEVEL_COUNT=8;
	
	public static int KEYBOARD_UP=Keys.UP;
	public static int KEYBOARD_DOWN=Keys.DOWN;
	public static int KEYBOARD_LEFT=Keys.LEFT;
	public static int KEYBOARD_RIGHT=Keys.RIGHT;
	public static int KEYBOARD_SHOOT=Keys.SPACE;
	public static int KEYBOARD_BACK=Keys.ESCAPE;
	
	public static int HORIZONTAL_AXIS_POS=0;public static boolean HORIZONTAL_AXIS_POS_V=true;
	public static int HORIZONTAL_AXIS_NEG=0;public static boolean HORIZONTAL_AXIS_NEG_V=false;
	
	public static int VERTICAL_AXIS_POS=1;public static boolean VERTICAL_AXIS_POS_V=false;
	public static int VERTICAL_AXIS_NEG=1;public static boolean VERTICAL_AXIS_NEG_V=true;
	
	public static int CONTROLLER_SHOOT=1;
	public static int CONTROLLER_BACK=0;
	
	
	public static void restore(){
		Preferences prefs=Gdx.app.getPreferences("game");
		
		
		KEYBOARD_UP=prefs.getInteger("kup",Keys.UP);
		KEYBOARD_DOWN=prefs.getInteger("kdown",Keys.DOWN);
		KEYBOARD_LEFT=prefs.getInteger("kleft",Keys.LEFT);
		KEYBOARD_RIGHT=prefs.getInteger("kright",Keys.RIGHT);
		KEYBOARD_SHOOT=prefs.getInteger("kshoot",Keys.SPACE);
		KEYBOARD_BACK=prefs.getInteger("kback",Keys.ESCAPE);
		
		HORIZONTAL_AXIS_POS=prefs.getInteger("cvp",0);
		HORIZONTAL_AXIS_NEG=prefs.getInteger("cvn",0);
		VERTICAL_AXIS_POS=prefs.getInteger("chp",0);
		VERTICAL_AXIS_NEG=prefs.getInteger("chn",0);
		CONTROLLER_SHOOT=prefs.getInteger("csh",1);
		CONTROLLER_BACK=prefs.getInteger("cbk",0);
		HORIZONTAL_AXIS_POS_V=prefs.getBoolean("cvpv",true);
		HORIZONTAL_AXIS_NEG_V=prefs.getBoolean("cvnv",false);
		VERTICAL_AXIS_POS_V=prefs.getBoolean("chpv",false);
		VERTICAL_AXIS_NEG_V=prefs.getBoolean("chnv",true);
		
		
	}
	public static void save(){
		Preferences prefs=Gdx.app.getPreferences("game");
		
		prefs.putInteger("kup", KEYBOARD_UP);
		prefs.putInteger("kdown", KEYBOARD_DOWN);
		prefs.putInteger("kleft", KEYBOARD_LEFT);
		prefs.putInteger("kright", KEYBOARD_RIGHT);
		prefs.putInteger("kshoot", KEYBOARD_SHOOT);
		prefs.putInteger("kback", KEYBOARD_BACK);
		
		prefs.putInteger("cvp",HORIZONTAL_AXIS_POS);
		prefs.putInteger("cvn",HORIZONTAL_AXIS_NEG);
		prefs.putInteger("chp",VERTICAL_AXIS_POS);
		prefs.putInteger("chn",VERTICAL_AXIS_NEG);
		prefs.putInteger("csh",CONTROLLER_SHOOT);
		prefs.putInteger("cbk",CONTROLLER_BACK);
		prefs.putBoolean("cvpv",HORIZONTAL_AXIS_POS_V);
		prefs.putBoolean("cvnv",HORIZONTAL_AXIS_NEG_V);
		prefs.putBoolean("chpv",VERTICAL_AXIS_POS_V);
		prefs.putBoolean("chnv",VERTICAL_AXIS_NEG_V);
		
		prefs.flush();
	}
	
}
