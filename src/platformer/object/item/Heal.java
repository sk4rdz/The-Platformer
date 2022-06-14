package platformer.object.item;

import static platformer.player.PlayerState.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import platformer.map.Map;
import platformer.object.SuperMapObject;

public class Heal extends SuperMapObject{
	public static final Color COLOR = Color.magenta;
	public static final int WIDTH = Map.TILESIZE*10/11;
	public static final int HEIGHT = Map.TILESIZE*10/11;
	public Heal(float x,float y){
		super(x,y,WIDTH,HEIGHT);
	}
	@Override
	public int[] itemEffect(int[] status){
		if(status[LIFE] < LIFE_NUM)
			status[LIFE]++;
		else
			status[SCORE] += 100;
		return status;
	}
	@Override
	public void draw(Graphics g,int offsetX,int offsetY){
		g.setColor(COLOR);
		g.fillOval((int)x+offsetX,(int)y+offsetY,width,height);
		g.setColor(Color.black);
		g.drawOval((int)x+offsetX,(int)y+offsetY,width,height);
	}
}