package de.bitowl.ld27;

import java.nio.file.attribute.UserPrincipalLookupService;

public class Mirror extends Entity {
	
	final static int UPLEFT=0;
	final static int UPRIGHT=1;
	final static int DOWNLEFT=3;
	final static int DOWNRIGHT=2;
	
	int type;
	public Mirror(int pX,int pY,int pType){
		tileX=(int)pX;tileY=(int)pY;
		offsetX=3;
		offsetY=2;
		x=pX*level.tileWidth+offsetX;y=pY*level.tileHeight+offsetY;
		width=27;
		height=27;
		collidable=true;
		blocking=true;
		type=pType;
		updateTexture();
	}
	public void updateTexture(){
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
	@Override
	public void powerByConnection(boolean pOn) {
		if(pOn){
			// rotate :)
			type++;	
			if(type>3){
				type=0;
			}
			updateTexture();
		}
	}
}
