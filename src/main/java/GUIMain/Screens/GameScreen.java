package GUIMain.Screens;

import AIs.AI;
import AIs.Randbot;
import Cards.ICard;
import GUIMain.GUI;
import GameBoard.GameBoard;
import Player.Robot;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Scanner;

public class GameScreen extends Game implements Screen {
    private SpriteBatch batch;
    private BitmapFont font;
    private final TiledMapTileLayer playerLayer;
    
    private final OrthogonalTiledMapRenderer renderer;
    private final OrthographicCamera camera;
    private final String mapName;
    private final int CELL_SIZE = 300;
    private final int HEIGHT;
    private final int WIDTH;
	private GameBoard gameboard;
	private final ArrayList<Robot> robots;
	private final AI ai = new Randbot();
	private int currentPhase = 0;
	private final GUI gui;

    /**
     * @param robots robotene som skal være med å spille
     * @param mapName navnet på filen.
     */
    public GameScreen(ArrayList<Robot> robots, String mapName, GUI gui){
        this.gui = gui;
        this.mapName = mapName;
        this.robots = robots;
        TmxMapLoader tmx = new TmxMapLoader();
        TiledMap map = tmx.load(mapName);

        TiledMapTileLayer backgroundLayer = (TiledMapTileLayer) map.getLayers().get("Background");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Robot");

        HEIGHT = backgroundLayer.getHeight();
        WIDTH = backgroundLayer.getWidth();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH*CELL_SIZE, HEIGHT*315);

        camera.position.x = CELL_SIZE * WIDTH / 2;
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map, 1);
        renderer.setView(camera);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public GameBoard getGameBoard(){ return gameboard; }

    @Override
    public void show() {
        gameboard = new GameBoard(robots, mapName);
        Stage stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addCaptureListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                simulateRound2();
                return true;
            }
        });


        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    public void simulateRound2(){
        
    	renderer.render();
    	
        gameboard.startRound();
        for (Robot bot : gameboard.getBots()) {
            if (bot.isControlledByAI()) ai.chooseCards(bot, gameboard.getBoard());
            else pickCardsFromTerminal(bot);
        }
        for (int i = 0; i < 5; i++) {
            gameboard.phase(i);
            
        }
        gameboard.endRound();
    }

    @Override
    public void render(float v) {
        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport( Gdx.graphics.getWidth()-CELL_SIZE,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() );
        Gdx.gl.glViewport( 0,0,Gdx.graphics.getWidth()-CELL_SIZE,Gdx.graphics.getHeight() );
        renderer.render();

        for (int y = 0; y < gameboard.getHeight(); y++) {
            for (int x = 0; x < gameboard.getWidth(); x++) {
                Robot bot = gameboard.getRobotAt(x, gameboard.getHeight() - y - 1);
                if(bot != null){
                    Sprite sprt = new Sprite(bot.getImage());
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    cell.setTile(new StaticTiledMapTile(sprt));

                    //Vi regner positiv rotasjon som med klokken, men libgdx sier det er mot klokken. Derfor tar vi 4-θ.
                    cell.setRotation(4 - bot.getDirection());
                    playerLayer.setCell(x, y, cell);
                }
                else playerLayer.setCell(x, y, new TiledMapTileLayer.Cell());
            }
        }
        batch.end();
        //delay();
    }

    public static void pickCardsFromTerminal(Robot bot){
        System.out.println("\n" + bot);
        ArrayList<ICard> availableCards = bot.getAvailableCards();
        if (availableCards.size() == 0) throw new IllegalStateException("This register has no available cards");
        System.out.println("Please type a line of ints to choose cards.");
        System.out.println("If you want card number 1, 4, 7, 5, 2 in that order, type '1 4 7 5 2'.\n");
        for (int i = 0; i < availableCards.size(); i++) {
            System.out.println(i+1 + ": " + availableCards.get(i));
        }
        System.out.println();
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < Math.min(bot.getHP(), 5); i++) {
            int pick = in.nextInt() - 1;
            if (pick < 0 || pick >= availableCards.size()) {
                System.err.println("Please choose one of the available cards.");
                i--;
            }
            else bot.chooseCard(availableCards.get(pick));
        }
    }
    
    @Override
    public void create() {
        // TODO: 04.03.2021 Vetsje hva som skulle vært her? Kanskje bare show()?
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    	
    }

    @Override
    public void hide() {

    }
	
	public void delay() {
		try {
			Thread.sleep(999);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Maybe an alternative for showPopUp()
	 * prints message at the top of GUI
	 * @param msg
	 */
	public void printMessage(String msg) {batch.begin();
		batch.setProjectionMatrix(camera.combined);
		font.setColor(Color.RED);
		font.draw(batch, msg, WIDTH,  HEIGHT*310);
		font.getData().setScale(5, 5);
		batch.end();
	}
}
	


