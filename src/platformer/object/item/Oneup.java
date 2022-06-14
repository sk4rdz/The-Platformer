package platformer.object.item;

import static platformer.player.PlayerState.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import platformer.map.Map;
import platformer.object.SuperMapObject;

public class Oneup extends SuperMapObject{
	public static final Color COLOR = Color.green;
	public static final int WIDTH = Map.TILESIZE*10/11;
	public static final int HEIGHT = Map.TILESIZE*10/11;
	public Oneup(float x,float y){
		super(x,y,WIDTH,HEIGHT);
	}
	@Override
	public int[] itemEffect(int[] status){
		if(status[REM] < 99)
			status[REM]++;
		else
			status[SCORE] += 1000;
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