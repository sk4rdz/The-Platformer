package platformer.object;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import platformer.map.Map;

public class GoalArea extends SuperMapObject{
	public static final Color COLOR = Color.red;
	public static final int WIDTH = Map.TILESIZE;
	public static final int HEIGHT = Map.TILESIZE;
	public GoalArea(float x,float y){
		super(x,y,HEIGHT,WIDTH);
	}
	@Override
	public int[] itemEffect(int[] status){return null;}
	@Override
	public void draw(Graphics g,int offsetX,int offsetY){
		g.setColor(COLOR);
		g.fillRect((int)x + offsetX,(int)y + offsetY,WIDTH,HEIGHT);
		g.setColor(Color.black);
		g.drawRect((int)x + offsetX,(int)y + offsetY,WIDTH,HEIGHT);
		g.drawOval((int)x + offsetX + 1,(int)y + offsetY + 1,WIDTH - 1,HEIGHT - 1);
	}
}
