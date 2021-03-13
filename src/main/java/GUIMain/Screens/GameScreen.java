package GUIMain.Screens;

import AIs.AI;
import AIs.Randbot;
import Cards.ICard;
import GUIMain.GUI;
import GUIMain.Textures;
import GameBoard.GameBoard;
import GameBoard.Position;
import Player.Register;
import Player.Robot;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private TiledMap map;
    private TiledMapTileLayer backgroundLayer;
    private TiledMapTileLayer middlegroundLayer;
    private TiledMapTileLayer foregroundLayer;
    private TiledMapTileLayer playerLayer;
    
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Stage stage;
    private String mapName;
    private int CELL_SIZE = 300;
    private int HEIGHT;
    private int WIDTH;
	private Sprite sprite;
	private boolean isInDebugMode;
	private GameBoard gameboard;
	private ArrayList<Robot> robots;
	private AI ai = new Randbot();
	private int currentPhase = 0;
	private GUI gui;
	
	private ArrayList<Textures> tes;

    /**
     * @param robots robotene som skal være med å spille
     * @param mapName navnet på filen som mappet skal baseres på. Husk .tmx!
     */
	public GameScreen(ArrayList<Robot> robots, String mapName, GUI gui){ this(robots, mapName, gui, false); }

    /**
     * @param robots robotene som skal være med å spille
     * @param mapName navnet på filen.
     * @param isInDebugMode Om denne er true blir vinduet lukket automatisk ved oppstart. Slik at vi kan kjøre testene.
     */
    public GameScreen(ArrayList<Robot> robots, String mapName, GUI gui, boolean isInDebugMode){
        this.gui = gui;
        this.isInDebugMode = isInDebugMode;
        this.mapName = mapName;
        this.robots = robots;
        tes = new ArrayList<>();
        
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
        if (isInDebugMode) Gdx.app.exit(); //Lukker vinduet, om vi startet GameScreen-en kun for å teste ting.
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addCaptureListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                simulateRound2();
                return true;
            }
        });

        TmxMapLoader tmx = new TmxMapLoader();
        map = tmx.load(mapName);

        backgroundLayer = (TiledMapTileLayer) map.getLayers().get("Background");
        //middlegroundLayer = (TiledMapTileLayer) map.getLayers().get("Middleground");
        //foregroundLayer = (TiledMapTileLayer) map.getLayers().get("Foreground");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Robot");

        HEIGHT = backgroundLayer.getHeight();
        WIDTH = backgroundLayer.getWidth();

        
        
        camera = new OrthographicCamera();
//        camera.setToOrtho(false, CELL_SIZE * WIDTH, CELL_SIZE * HEIGHT);
        camera.setToOrtho(false, WIDTH*CELL_SIZE, HEIGHT*315);
        
        camera.position.x = CELL_SIZE * WIDTH / 2;
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map, 1);
        renderer.setView(camera);

        batch = new SpriteBatch();
        font = new BitmapFont();
        
     
      
    }

    public void simulateRound2(){
        
    	renderer.render();
    	
        gameboard.startRound();
        for (Register reg : gameboard.getRegisters()) {
            if (reg.getRobot().isControlledByAI()) ai.chooseCards(reg, gameboard.getBoard());
            else pickCardsFromTerminal(reg);
        }
        for (int i = 0; i < 5; i++) {
            gameboard.phase(i);
            
        }
        gameboard.endRound();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.render();
//        displayRobots(getPlayerImage1("alive"), getPlayerCell(getPlayerImage("alive")));

                
         for(Robot rob: robots) {
        	Position pos = gameboard.getBoard().getRobotPosition(rob);
        	
        	tes.add(new Textures(rob, camera, pos.getX(), pos.getY(),
        			rob.getColor())); for(Textures rob1: tes) {
        		rob1.drawRobot();
        	}  
         }
        Gdx.gl.glViewport( Gdx.graphics.getWidth()-CELL_SIZE,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() );
        Gdx.gl.glViewport( 0,0,Gdx.graphics.getWidth()-CELL_SIZE,Gdx.graphics.getHeight() );
         
        delay();
    }

    public static void pickCardsFromTerminal(Register reg){
        System.out.println("\n" + reg.getRobot());
        ArrayList<ICard> availableCards = reg.getRegisterCards();
        if (availableCards.size() == 0) throw new IllegalStateException("This register has no available cards");
        System.out.println("Please type a line of ints to choose cards.");
        System.out.println("If you want card number 1, 4, 7, 5, 2 in that order, type '1 4 7 5 2'.\n");
        for (int i = 0; i < availableCards.size(); i++) {
            System.out.println(i+1 + ": " + availableCards.get(i));
        }
        System.out.println();
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < Math.min(reg.getDamageTokens(), 5); i++) {
            int pick = in.nextInt() - 1;
            if (pick < 0 || pick >= availableCards.size()) {
                System.err.println("Please choose one of the available cards.");
                i--;
            }
            else reg.addCardToRegister(availableCards.get(pick));
        }
    }
    int phaseNr = 0;
    public void simulateRound(){
        // 1. Vise alle registerkortene samtidig. showAllRegisterCards() eller noe.
        GameBoard gb = getGameBoard();
        gb.startRound();
        if(gb.hasWon() != null) {
            gb.endRound();
            phaseNr = 0;
            gui.showPopUp("The winner of this round is: " + gb.hasWon(), "Round finished!");
            return;
        }
        if(phaseNr == 5){
            gui.showPopUp("This round is finished with no winner.", "Round finished");
            return;
        }
        gb.phase(phaseNr);
        phaseNr++;
        //her må GUI oppdateres
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

    public void setPlayerCell(int x, int y, TiledMapTileLayer.Cell cell) {
		playerLayer.setCell(x, y, cell);
	}
	
	
	public TiledMapTileLayer.Cell getPlayerCell(StaticTiledMapTile PlayerTile){
		return new TiledMapTileLayer.Cell().setTile(PlayerTile);
	}
    
    /**
	 * Displays robot on screen in the given cell
	 * @param t robot image
	 * @param cell 
	 */
	public void displayRobots(TextureRegion t, TiledMapTileLayer.Cell cell) {


		for(int y = 0; y < gameboard.getHeight(); y++) {
			for(int x = 0; x < gameboard.getWidth(); x++) {
                Robot r = gameboard.getRobotAt(x, y);
                if(r != null) {

                    batch = new SpriteBatch();
                    sprite = new Sprite(t.getTexture());

                    sprite.setColor(r.getColor());
                    batch.begin();
                    batch.draw(t.getTexture(), x, HEIGHT - y - 1);
                    sprite.draw(batch);

                  
                    setPlayerCell(x,HEIGHT - y - 1, cell);

                    batch.end();
				
				}
                else{
                    TiledMapTileLayer.Cell c = playerLayer.getCell(x, HEIGHT-y-1);
                    if (c != null) playerLayer.getCell(x, HEIGHT-y-1).setTile(null);
                }
			}
		}
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
		System.out.println(Gdx.graphics.getWidth());
		System.out.println("Widht: "+WIDTH);
		
		font.draw(batch, msg, WIDTH,  HEIGHT*310);
		font.getData().setScale(5, 5);
		batch.end();
	}
	
	
	
	/**
	 * 
	 * @param state
	 * @return the players state as a tile
	 */
	public StaticTiledMapTile getPlayerImage(String state) {

        Texture t = new Texture("player.png");
        TextureRegion[][] tmp = new TextureRegion(t).split(300,300);
		
		if(state.equals("alive")) {
			return  new StaticTiledMapTile(tmp[0][0]);
		}
		else if (state.equals("dead")) {
			return  new StaticTiledMapTile(tmp[0][1]);
		}
		else if (state.equals("victory")) {
			return new StaticTiledMapTile(tmp[0][2]);
		}
		return null;
	}
	
	/**
	 * 
	 * @param state either dead, alive or victory
	 * @return the players state as a texture which could be used as players image
	 */
	public TextureRegion getPlayerImage1(String state) {
        Texture t = new Texture("player.png");
        TextureRegion[][] tmp = new TextureRegion(t).split(300,300);
        switch (state) {
        case "dead":
        	return tmp[0][1];
        case "victory":
        	return tmp[0][2];
        default:
        	return tmp[0][0];
        }
	}
}
	


