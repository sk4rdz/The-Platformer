package platformer.player;

import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.Input;

import platformer.map.MapManager;
import platformer.object.GoalArea;
import platformer.object.SuperMapObject;
import platformer.object.item.Coin;
import platformer.object.item.Heal;
import platformer.object.item.Oneup;

public class PlayerAction{
	private Player player;
	private PlayerState state;
	private MapManager mapManager;
	private boolean lP,rP,sP,vP;
	private boolean alreadyJump;
	private boolean clear,death;
	public PlayerAction(Player player,PlayerState gameState,MapManager mapManager){
		this.player = player;
		this.state = gameState;
		this.mapManager = mapManager;
	}
	public void update(){
		playerUpdate();
		LinkedList<SuperMapObject> objects = mapManager.getObjects();
		Iterator<SuperMapObject> iterator = objects.iterator();
		while(iterator.hasNext()){
			SuperMapObject object = (SuperMapObject)iterator.next();
			if(player.isCollision(object)){
				if(object instanceof GoalArea)
					clear = true;
				if(object instanceof Coin){
					Coin coin = (Coin)object;
					objects.remove(coin);
					state.getItem(coin);
					break;
				}
				if(object instanceof Heal){
					Heal heal = (Heal)object;
					objects.remove(heal);
					state.getItem(heal);
					break;
				}
				if(object instanceof Oneup){
					Oneup oneup = (Oneup)object;
					objects.remove(oneup);
					state.getItem(oneup);
					break;
				}
			}
		}
	}
	private void playerUpdate() {
		boolean onGround = player.getonGround();
		if (lP && rP)
			player.stop();
		else if (lP)
			player.accelerateLeft();
		else if (rP)
			player.accelerateRight();
		else
			player.stop();
		
		if (sP) {
			if (!alreadyJump && onGround) {
				player.jump();
				alreadyJump = true;
			} else if (alreadyJump && !onGround)
				player.rize();
		} else {
			alreadyJump = false;
			player.stopRize();
		}
		if (onGround) {
			if (vP)
				player.dash();
			if (!vP)
				player.initSpeed();
		}
		player.update();
	}
	public void keyPressed(int key){
		if(key == Input.KEY_LEFT)
			lP = true;
		if(key == Input.KEY_RIGHT)
			rP = true;
		if(key == Input.KEY_SPACE)
			sP = true;
		if(key == Input.KEY_V)
			vP = true;
	}
	public void keyReleased(int key){
		if(key == Input.KEY_LEFT)
			lP = false;
		if(key == Input.KEY_RIGHT)
			rP = false;
		if(key == Input.KEY_SPACE)
			sP = false;
		if(key == Input.KEY_V)
			vP = false;
	}
	public boolean getClear(){
		return clear;
	}
	public boolean getDeath(){
		return death;
	}
}
