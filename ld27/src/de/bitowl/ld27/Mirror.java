package de.bitowl.ld27;

import java.nio.file.attribute.UserPrincipalLookupService;

public class Mirror extends Entity {
	
	final static int UPLEFT=0;
	final static int UPRIGHT=1;
	final static int DOWNLEFT=2;
	final static int DOWNRIGHT=3;
	
	int type;
	public Mirror(int pX,int pY,int pType){
		x=pX*level.tileWidth;y=pY*level.tileHeight;
		width=32;
		height=32;
		collidable=true;
		blocking=true;
		type=pType;
		switch(type){
			case UPLEFT:
				texture=TestScreen.mirrorUpLeftT;
				break;
			case UPRIGHT:
				texture=TestScreen.mirrorUpRightT;
				break;
			case DOWNLEFT:
				texture=TestScreen.mirrorDownLeftT;
				break;
			case DOWNRIGHT:
				texture=TestScreen.mirrorDownRightT;
				break;
		}
	}
}
