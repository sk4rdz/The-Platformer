package platformer.map;

import static platformer.map.MapConstants.*;

import java.io.BufferedReader;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.Log;

import platformer.player.Player;

public class Map {
	public static final int TILESIZE = 32;
	private BufferedReader br;
	private int row,col,width,height;
	private char[][] map;
	public Map(int col,int row,BufferedReader br) {
		this.col = col;
		this.row = row;
		this.br = br;
		width = TILESIZE*col;
		height = TILESIZE*row;
	}
	public boolean load(){
		map = new char[row][col];
		try {
			String line;
			for (int i = 0; i < row; i++){
				line = br.readLine();
				for (int j = 0; j < col; j++)
					map[i][j] = line.charAt(j);
			}
		} catch (StringIndexOutOfBoundsException e) {
			return true;
		} catch(IOException e) {
			Log.error(e);
		}
		return false;
	}
	public int getTileCollisionX(float x,float y,float nx){
		int x1 = pixelsToTiles((int)Math.ceil(Math.min(x,nx)));
		int x2 = pixelsToTiles((int)Math.floor(Math.max(x,nx) + Player.SIZE - 1));
		int y1 = pixelsToTiles((int)Math.ceil(y));
		int y2 = pixelsToTiles((int)Math.floor(y) + Player.SIZE - 1);
		for(int i = x1; i <= x2; i++){
			for(int j = y1; j <= y2; j++){
				if (isCollisionTile(map[j][i]))
					return i;
			}
		}
		return NONE_TILE;
	}
	public int getTileCollisionY(float x,float y,float ny){
		int y1 = pixelsToTiles((int)Math.ceil(Math.min(y,ny)));
		int y2 = pixelsToTiles((int)Math.floor(Math.max(y,ny)+Player.SIZE - 1));
		int x1 = pixelsToTiles((int)Math.ceil(x));
		int x2 = pixelsToTiles((int)Math.floor(x) + Player.SIZE - 1);
		for(int i = x1; i <= x2; i++){
			for(int j = y1; j <= y2; j++){
				if (isCollisionTile(map[j][i]))
					return j;
			}
		}
		return NONE_TILE;
	}
	public void draw(Graphics g,int offsetX,int offsetY) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				switch (map[i][j]) {
				case BLOCK:
					g.setColor(Color.gray);
					g.fillRect(tilesToPixels(j)+offsetX,tilesToPixels(i)+offsetY,TILESIZE,TILESIZE);
					g.setColor(Color.black);
					g.drawRect(tilesToPixels(j)+offsetX,tilesToPixels(i)+offsetY,TILESIZE,TILESIZE);
					break;
				case GRASS:
					drawGround(g,offsetX,offsetY,i,j,GRASS);
					break;
				case GROUND:
					drawGround(g,offsetX,offsetY,i,j,GROUND);
					break;
				}
			}
		}
	}
	public void drawGround(Graphics g,int offsetX,int offsetY,int i,int j,int type) {
		switch(type){
		case GRASS:
			g.setColor(Color.green);
			break;
		case GROUND:
			g.setColor(Color.gray);
			break;
		}
		g.fillRect(tilesToPixels(j)+offsetX,tilesToPixels(i)+offsetY,TILESIZE,TILESIZE);
		g.setColor(Color.black);
		if(i > 0 && map[i-1][j] != type){
			g.drawLine(tilesToPixels(j)+offsetX,tilesToPixels(i)+offsetY,
					tilesToPixels(j)+offsetX+TILESIZE,tilesToPixels(i)+offsetY);
		}
		if(i < row-1 && map[i+1][j] != type){
			g.drawLine(tilesToPixels(j)+offsetX,tilesToPixels(i)+offsetY+TILESIZE,
					tilesToPixels(j)+offsetX+TILESIZE,tilesToPixels(i)+offsetY+TILESIZE);
		}
		if(j > 0 && map[i][j-1] != type){
			g.drawLine(tilesToPixels(j)+offsetX,tilesToPixels(i)+offsetY,
					tilesToPixels(j)+offsetX,tilesToPixels(i)+offsetY+TILESIZE);
		}
		if(j < col-1 && map[i][j+1] != type){
			g.drawLine(tilesToPixels(j)+offsetX+TILESIZE,tilesToPixels(i)+offsetY,
					tilesToPixels(j)+offsetX+TILESIZE,tilesToPixels(i)+offsetY+TILESIZE);
		}
	}
	private static boolean isCollisionTile(int tile) {
		if(tile == BLOCK || tile == GRASS || tile == GROUND)
			return true;
		return false;
	}
	public static int pixelsToTiles(int pixels) {
		return pixels / TILESIZE;
	}
	public static float tilesToPixels(int tiles) {
		return tiles * TILESIZE;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public char[][] getmap(){
		return map;
	}
	public static final int NONE_TILE = -1;
}
