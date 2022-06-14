package platformer;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import platformer.state.GameClearMenu;
import platformer.state.GameOverMenu;
import platformer.state.NextStageDisplay;
import platformer.state.TitleMenu;

public class Platformer extends StateBasedGame{
	private static final int WIDTH = 960;
	private static final int HEIGHT = 640;
	private static final int FPS = 60;
	private static final String TITLE = "The Platformer";
	private Game game;
	public Platformer(String title) {
		super(title);
		try {
			System.setOut(new PrintStream("platformer.log"));
		} catch (FileNotFoundException e) {
			Log.error(e);
		}
		game = new Game();
	}
	@Override
	public void initStatesList(GameContainer gc)throws SlickException{
		addState(new TitleMenu(game));
		addState(new NextStageDisplay(game));
		addState(new GameOverMenu(game));
		addState(new GameClearMenu(game));
		addState(game);
	}
	@Override
	public boolean closeRequested(){
		Log.info("Successful program termination");
		System.exit(0);
		return false;
	}
	public static void main(String[] args)throws SlickException{
		AppGameContainer app = new AppGameContainer(new Platformer(TITLE));
		app.setDisplayMode(WIDTH,HEIGHT,false);
		Log.info("setDisplayMode: " + WIDTH + " x " + HEIGHT);
		app.setTargetFrameRate(FPS);
		Log.info("setTargetFrameRate: " + FPS);
		app.setAlwaysRender(true);
		Log.info("setAlwaysRender: " + app.getAlwaysRender());
		app.setShowFPS(false);
		app.start();
	}
}
