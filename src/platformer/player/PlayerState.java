package platformer.player;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import platformer.map.Map;
import platformer.object.SuperMapObject;

public class PlayerState{
	public static final int REM_NUM = 2;
	public static final int TIMER_SPEED = 1000;
	public static final int TIME_VALUE = 500*TIMER_SPEED;
	public static final int RESET_COIN_NUM = 99;
	public static final int STAGE_NUM = 2;
	public static final int FIRST_STAGE_NUM = 1;
	public static final int LIFE_NUM = 3;
	private int[] status = new int[6];
	private boolean death;
	public void initGameState(){
		status[REM] = REM_NUM;
		status[LIFE] = LIFE_NUM;
		status[STAGE] = FIRST_STAGE_NUM;
		status[SCORE] = 0;
		status[COIN] = 0;
	}
	public void initTime(){
		status[TIME] = TIME_VALUE;
		death = false;
	}
	public boolean damage(){
		if(status[LIFE] > 1){
			status[LIFE]--;
			return false;
		}else
			return true;
	}
	public boolean clear(){
		if(status[STAGE] < STAGE_NUM){
			status[STAGE]++;
			status[SCORE] += status[TIME]/TIMER_SPEED;
			status[LIFE] = LIFE_NUM;
			return false;
		}
		else
			return true;
	}
	public boolean miss(){
		if(status[REM] > 1){
			status[REM]--;
			status[LIFE] = LIFE_NUM;
			return false;
		}
		else
			return true;
	}
	public void getItem(SuperMapObject obj){
		status = obj.itemEffect(status);
	}
	public void timer(int delta){
		if(status[TIME] <= 0)
			death = true;
		else
			status[TIME] -= delta;
	}
	public void Draw(Graphics g,int width) throws SlickException{
		AngelCodeFont font = new AngelCodeFont("data/fonts/font2.fnt","data/fonts/font2_0.tga");
		g.setColor(new Color(0.0f,0.0f,0.0f,0.4f));
		g.fillRect(0,0,width,35);
		g.setColor(Color.white);
		g.fillOval(130,6,Map.TILESIZE*3/4,Map.TILESIZE*3/4);
		g.setColor(Color.yellow);
		g.fillOval(220,6,Map.TILESIZE*3/4,Map.TILESIZE*3/4);
		for(int i = 0; i < status[LIFE]; i++){
			g.setColor(Color.magenta);
			g.fillOval(375+i*30,6,Map.TILESIZE*3/4,Map.TILESIZE*3/4);
		}
		g.setFont(font);
		font.drawString(10,5,"STAGE " + String.valueOf(status[STAGE]),Color.white);
		font.drawString(160,5,"×" + String.valueOf(status[REM]),Color.white);
		font.drawString(250,5,"×" + String.valueOf(status[COIN]),Color.white);
		font.drawString(315,5,"LIFE ",Color.white);
		font.drawString(width - 160,5,"SCORE  " + String.valueOf(status[SCORE]),Color.white);
		font.drawString(width - 280,5,"TIME  " + String.valueOf(status[TIME] / TIMER_SPEED),Color.white);
	}
	public boolean getDeath(){
		return death;
	}
	public int getStage(){
		return status[STAGE];
	}
	public int getRem(){
		return status[REM];
	}
	public static final int REM = 0;
	public static final int STAGE = 1;
	public static final int SCORE = 2;
	public static final int COIN = 3;
	public static final int LIFE = 4;
	public static final int TIME = 5;
}
