package platformer.map;

import static platformer.map.MapConstants.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.Log;

import platformer.object.GoalArea;
import platformer.object.SuperMapObject;
import platformer.object.item.Coin;
import platformer.object.item.Heal;
import platformer.object.item.Oneup;
import platformer.player.Player;

public class MapManager{
	public static final float GRAVITY_VALUE = 0.8f;
	private int row,col;
	private float x,y;
	private Color backGroundColor;
	private Player player;
	private Map map;
	private String fileName;
	private LinkedList<SuperMapObject> objects;
	public MapManager(String filename){
		this.fileName = filename;
	}
	public void initMap(){
		try (BufferedReader br = new BufferedReader(new FileReader("data/stages/" + fileName +".dat"))) {
			map = new Map(col,row,br);
			if (map.load()) {
				Log.error("Mapfile is corrupted: " + fileName + ".dat");
				System.exit(1);
			}
			objects = new LinkedList<SuperMapObject>();
			player = new Player(x,y,map);
			addMapObject(map);
		} catch (FileNotFoundException e) {
			Log.error("File Not Found: " + fileName + ".dat");
			System.exit(1);
		} catch (IOException e) {
			Log.error(e);
		}
	}
	public void loadStageData(){
		try (BufferedReader br = new BufferedReader(new FileReader("data/stages/" + fileName + "data.csv"))) {
			String[] data = br.readLine().split("[,]");
			row = Integer.parseInt(data[0]);
			col = Integer.parseInt(data[1]);
			data = br.readLine().split("[,]");
			backGroundColor = new Color(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[1]));
			data = br.readLine().split("[,]");
			x = Map.tilesToPixels(Integer.parseInt(data[0])-1);
			y = Map.tilesToPixels(Integer.parseInt(data[1])-1);
			br.close();
		} catch (FileNotFoundException e) {
			Log.error("File Not Found: " + fileName + "data.csv");
			System.exit(1);
		} catch (StringIndexOutOfBoundsException e) {
			Log.error("Mapdatafile is corrupted: " + fileName + "data.csv");
			System.exit(1);
		} catch (IOException e) {
			Log.error(e);
		}
	}
	private void addMapObject(Map map){
		char[][] stagemap = map.getmap();
		for (int i = 0; i < stagemap.length; i++) {
			for (int j = 0; j < stagemap[0].length; j++) {
				switch (map.getmap()[i][j]) {
				case COIN:
					objects.add(new Coin(Map.tilesToPixels(j),Map.tilesToPixels(i)));
					break;
				case HEAL:
					objects.add(new Heal(Map.tilesToPixels(j),Map.tilesToPixels(i)));
					break;
				case ONEUP:
					objects.add(new Oneup(Map.tilesToPixels(j),Map.tilesToPixels(i)));
					break;
				case GOALAREA:
					objects.add(new GoalArea(Map.tilesToPixels(j),Map.tilesToPixels(i)));
					break;
				}
			}
		}
	}
	public void draw(Graphics g,int offsetX,int offsetY){
		map.draw(g,offsetX,offsetY);
		Iterator<SuperMapObject> iterator = objects.iterator();
		while(iterator.hasNext()){
			SuperMapObject object = (SuperMapObject)iterator.next();
			object.draw(g,offsetX,offsetY);
		}
	}
	public int getMapWidth() {
		return map.getWidth();
	}
	public int getMapHeight() {
		return map.getHeight();
	}
	public Color getBackGroundColor() {
		return backGroundColor;
	}
	public Map getMap() {
		return map;
	}
	public Player getPlayer() {
		return player;
	}
	public LinkedList<SuperMapObject> getObjects(){
		return objects;
	}
}