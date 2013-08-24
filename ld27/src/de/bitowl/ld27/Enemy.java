package de.bitowl.ld27;

public class Enemy extends Entity{
	float speedX;
	float speedY;
	public Enemy(float pX,float pY){
		x=pX;
		y=pY;
		width=32;
		height=32;
		damageOnPlayer=1;
		texture=TestScreen.enemyT;
	}
}
