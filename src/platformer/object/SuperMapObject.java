package platformer.object;

import org.newdawn.slick.Graphics;

public abstract class SuperMapObject{
	protected float x,y;
	protected int width,height;
	public SuperMapObject(float x,float y,int width,int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	public abstract int[] itemEffect(int[] playerstatus);
	public abstract void draw(Graphics g,int offsetX,int offsetY);
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
}
