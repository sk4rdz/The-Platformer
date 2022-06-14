package platformer;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;

import platformer.map.MapManager;
import platformer.player.Player;
import platformer.player.PlayerAction;
import platformer.player.PlayerState;
import platformer.state.GameClearMenu;
import platformer.state.GameOverMenu;
import platformer.state.NextStageDisplay;
import platformer.state.TitleMenu;

public class Game extends BasicGameState{
	public static final int ID = 1;
	public static final String CLEARPANELMESSAGE = "CLEAR!";
	public static final String MISSPANELMESSAGE = "MISS";
	private boolean escape;
	private int gameState,counter;
	private Font font;
	private MapManager mapManager;
	private Player player;
	private PlayerState status;
	private PlayerAction action;
	@Override
	public void init(GameContainer gc,StateBasedGame sbg)throws SlickException{
		font = new AngelCodeFont("data/fonts/font1.fnt","data/fonts/font1_0.tga");
	}
	public void initGameState(){
		status = new PlayerState();
		status.initGameState();
		initStage();
	}
	public void initStage(){
		mapManager = new MapManager("stage" + status.getStage());
		mapManager.loadStageData();
		mapManager.initMap();
		player = mapManager.getPlayer();
		action = new PlayerAction(player,status,mapManager);
		status.initTime();
		escape = false;
		gameState = GAME;
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException{
		if(escape)
			sbg.enterState(TitleMenu.ID,null,new FadeInTransition(Color.black));
		switch(gameState){
		case MISS:
			counter += delta;
			if(counter >= 1500){
				counter = 0;
				if(status.miss())
					sbg.enterState(GameOverMenu.ID,null,new FadeInTransition(Color.black));
				else
					initStage();
			}
			break;
		case CLEAR:
			counter += delta;
			if(counter >= 2000){
				counter = 0;
				if(status.clear())
					sbg.enterState(GameClearMenu.ID,null,new FadeInTransition(Color.black));
				else{
					sbg.enterState(NextStageDisplay.ID,null,new FadeInTransition(Color.black));
				}
			}
			break;
		case GAME:
			action.update();
			if(player.getDeath() || action.getDeath() ||  status.getDeath())
				gameState = MISS;
			if(action.getClear())
				gameState = CLEAR;
			status.timer(delta);
			if(player.getInvisible()){
				player.timer(delta);
			}
		}
	}
	@Override
	public void render(GameContainer gc,StateBasedGame sbg,Graphics g)throws SlickException{
		g.setAntiAlias(true);
		g.setColor(mapManager.getBackGroundColor());
		g.fillRect(0, 0, gc.getWidth() ,gc.getHeight());
		int offsetX = Math.max(Math.min(gc.getWidth()/2-(int)player.getX(),0)
				,gc.getWidth()-mapManager.getMapWidth());
		int offsetY = Math.max(Math.min(gc.getHeight()/2-(int)player.getY(),0)
				,gc.getHeight()-mapManager.getMapHeight());
		mapManager.draw(g,offsetX,offsetY);
		player.draw(g,offsetX,offsetY);
		status.Draw(g,gc.getWidth());
		if(gameState == MISS){
			g.setFont(font);
			g.setColor(new Color(0.0f,0.0f,0.0f,0.7f));
			g.fillRect(0,0,gc.getWidth(),gc.getHeight());
			g.setColor(Color.white);
			g.drawString(MISSPANELMESSAGE,gc.getWidth()/2-(font.getWidth(MISSPANELMESSAGE)/2),280);
		}
		else if(gameState == CLEAR){
			g.setFont(font);
			g.setColor(new Color(0.0f,0.0f,0.0f,0.7f));
			g.fillRect(0,0,gc.getWidth(),gc.getHeight());
			g.setColor(Color.white);
			g.drawString(CLEARPANELMESSAGE,gc.getWidth()/2-(font.getWidth(CLEARPANELMESSAGE)/2),280);
		}
	}
	@Override
	public void keyPressed(int key,char c){
		action.keyPressed(key);
	}
	@Override
	public void keyReleased(int key,char c){
		action.keyReleased(key);
		if(key == Input.KEY_ESCAPE)
			escape = true;
	}
	@Override
	public int getID(){
		return ID;
	}
	public int getStage(){
		return status.getStage();
	}
	public static final int GAME = 0;
	public static final int MISS = 1;
	public static final int CLEAR = 2;
}
