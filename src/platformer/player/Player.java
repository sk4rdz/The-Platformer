package platformer.player;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import platformer.map.Map;
import platformer.map.MapManager;
import platformer.object.SuperMapObject;

public class Player {
	public static final int SIZE = Map.TILESIZE;
	private static final float INITIAL_SPEED = 1.0f;
	private static final float MAXIMUM_SPEED = 2.5f;
	private static final float RUN_MAXIMUM_SPEED = 4.5f;
	private static final float ACCELERATE_VALUE = 0.05f;
	private static final float RUN_ACCELERATE_VALUE = 0.1f;
	private static final float DECELERATE_VALUE = 0.12f;
	private static final float INITIAL_RIZE_SPEED = 12.0f;
	private static final float RIZE_ABILITY_VALUE = 0.4f;
	private static final int INVISIBLETIME_VALUE = 2000;
	private float x,y,vx,vy;
	private float maximumSpeed = MAXIMUM_SPEED;
	private float accelerateValue = ACCELERATE_VALUE;
	private boolean death,invisible;
	private boolean onGround,rize,stop;
	private int invisibleTime;
	private Map map;
	public Player(float x, float y, Map map) {
		this.x = x;
		this.y = y;
		this.map = map;
	}
	public void stop() {
		if (vx > DECELERATE_VALUE)
			vx -= DECELERATE_VALUE;
		else if (vx < -DECELERATE_VALUE)
			vx += DECELERATE_VALUE;
		else {
			vx = 0;
			stop = true;
		}
	}
	public void accelerateLeft() {
		if (stop)
			vx = -INITIAL_SPEED;
		else if (vx > DECELERATE_VALUE)
			vx -= DECELERATE_VALUE;
		if (vx > -maximumSpeed)
			vx -= accelerateValue;
		else vx = -maximumSpeed;
		stop = false;
	}
	public void accelerateRight() {
		if (stop)
			vx = INITIAL_SPEED;
		else if (vx < DECELERATE_VALUE)
			vx += DECELERATE_VALUE;
		if (vx < maximumSpeed)
			vx += accelerateValue;
		else vx = maximumSpeed;
		stop = false;
	}
	public void jump() {
		vy = -INITIAL_RIZE_SPEED;
	}
	public void rize() {
		rize = true;
	}
	public void stopRize() {
		rize = false;
	}
	public void dash() {
		maximumSpeed = RUN_MAXIMUM_SPEED;
		accelerateValue = RUN_ACCELERATE_VALUE;
	}
	public void initSpeed() {
		maximumSpeed = MAXIMUM_SPEED;
		accelerateValue = ACCELERATE_VALUE;
	}
	public void invisible() {
		invisibleTime = INVISIBLETIME_VALUE;
		invisible = true;
	}
	public void update() {
		if (rize)
			vy -= RIZE_ABILITY_VALUE;
		vy += MapManager.GRAVITY_VALUE;
		float newX = x + vx;
		float newY = y + vy;
		if (newX < 0) {
			newX = 0;
			vx = 0;
			stop = true;
		} else if (newX + SIZE >= map.getWidth()) {
			newX = map.getWidth() - SIZE;
			vx = 0;
			stop = true;
		}
		if (newY > map.getHeight())
			death = true;
		else if (newY < 0 || newY + SIZE > map.getHeight()) {
			x = newX;
			y = newY;
		} else {
			int collX = map.getTileCollisionX(x,y,newX);
			int collY = map.getTileCollisionY(x,y,newY);
			if (collX == Map.NONE_TILE)
				x = newX;
			else {
				if (vx > 0)
					x = Map.tilesToPixels(collX) - SIZE;
				else if (vx < 0)
					x = Map.tilesToPixels(collX + 1);
				vx = 0;
				stop = true;
			}
			if (collY == Map.NONE_TILE) {
				y = newY;
				onGround = false;
			} else {
				if (vy > 0) {
					y = Map.tilesToPixels(collY) - SIZE;
					vy = 0;
					onGround = true;
				} else if (vy < 0) {
					y = Map.tilesToPixels(collY + 1);
					vy = 0;
				}
			}
		}
	}
	public boolean isCollision(SuperMapObject object){
		Rectangle rectP = new Rectangle((int)x+1,(int)y+1,SIZE-1,SIZE-1);
		Rectangle rectO = new Rectangle((int)object.getX(),(int)object.getY(),object.getWidth(),object.getHeight());
		if(rectP.intersects(rectO))
			return true;
		return false;
	}
	public void timer(int delta){
		if(invisibleTime <= 0)
			invisible = false;
		else
			invisibleTime -= delta;
	}
	public void draw(Graphics g,int offsetX,int offsetY){
		if(invisible)
			g.setColor(new Color(1.0f,1.0f,1.0f,0.2f));
		else
			g.setColor(Color.white);
		g.fillOval((int)x+offsetX,(int)y+offsetY,SIZE,SIZE);
		g.setColor(Color.black);
		g.drawOval((int)x+offsetX,(int)y+offsetY,SIZE,SIZE);
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public boolean getDeath(){
		return death;
	}
	public boolean getonGround(){
		return onGround;
	}
	public boolean getInvisible(){
		return invisible;
	}
}
