package platformer.state;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

import platformer.Game;

public class NextStageDisplay extends BasicGameState{
	public static final int ID = 3;
	private int counter;
	private Font font;
	private Game game;
	public NextStageDisplay(Game game){
		this.game = game;
	}
	@Override
	public void init(GameContainer container,StateBasedGame state)throws SlickException{
		font = new AngelCodeFont("data/fonts/font1.fnt","data/fonts/font1_0.tga");
	}
	@Override
	public void render(GameContainer container,StateBasedGame state,Graphics g)throws SlickException{
		g.setFont(font);
		g.setColor(Color.black);
		g.fillRect(0,0,container.getWidth(),container.getHeight());
		g.setColor(Color.white);
		g.drawString("STAGE " + game.getStage(),container.getWidth()/2-(font.getWidth("STAGE " + game.getStage())/2),280);
	}
	@Override
	public void update(GameContainer container,StateBasedGame state,int delta)throws SlickException{
		counter += delta;
		if(counter >= 3000){
			counter = 0;
			game.initStage();
			state.enterState(Game.ID,null,new HorizontalSplitTransition(Color.black));
		}
	}
	@Override
	public int getID(){
		return ID;
	}
}
