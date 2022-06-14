package platformer.state;

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
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;

import platformer.Game;

public class TitleMenu extends BasicGameState{
	public static final int ID = 2;
	private int counter;
	private boolean gameStart;
	private Font font1;
	private Font font2;
	private String title = "THE PLATFORMER";
	private String[] options = new String[2];
	private int selected;
	private Game game;
	public TitleMenu(Game game){
		this.game = game;
	}
	@Override
	public void init(GameContainer container, StateBasedGame state) throws SlickException{
		font1 = new AngelCodeFont("data/fonts/font1.fnt","data/fonts/font1_0.tga");
		font2 = new AngelCodeFont("data/fonts/font3.fnt","data/fonts/font3_0.tga");
		options[START_GAME] = "Start Game";
		options[EXIT] = "Exit";
	}
	@Override
	public void render(GameContainer container, StateBasedGame state, Graphics g){
		g.setColor(Color.black);
		g.fillRect(0,0,container.getWidth(),container.getHeight());
		g.setFont(font2);
		g.setColor(Color.gray);
		g.drawString(title,container.getWidth()/2-(font2.getWidth(title)/2),100);
		g.setFont(font1);
		g.setColor(Color.white);
		for (int i=0;i<options.length;i++){
			g.drawString(options[i],container.getWidth()/2-(font1.getWidth(options[i])/2),250+(i*50));
			if (selected == i)
				g.drawRect(container.getWidth()/5,240+(i*50),container.getWidth()/5*3,50);
		}
	}
	@Override
	public void update(GameContainer container, StateBasedGame state, int delta){
		if(gameStart){
			counter += delta;
			if(counter >= 1000){
				counter = 0;
				game.initGameState();
				state.enterState(NextStageDisplay.ID,new FadeOutTransition(Color.black),
						new FadeInTransition(Color.black));
				gameStart = false;
			}
		}
	}
	@Override
	public void keyReleased(int key, char c){
		if(key == Input.KEY_ENTER){
			if(selected == START_GAME)
				gameStart = true;
			if(selected == EXIT){
				Log.info("Successful program termination");
				System.exit(0);
			}
		}
		if (key == Input.KEY_DOWN){
			selected++;
			if (selected >= options.length){
				selected = START_GAME;
			}
		}
		if (key == Input.KEY_UP){
			selected--;
			if (selected < 0){
				selected = options.length - 1;
			}
		}
	}
	@Override
	public int getID(){
		return ID;
	}
	public static final int START_GAME = 0;
	public static final int EXIT = 1;
}
