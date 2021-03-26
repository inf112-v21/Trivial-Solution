package GUIMain.Screens;

import AIs.AI;
import AIs.Randbot;
import GameBoard.Cards.ICard;
import GUIMain.GUI;
import GameBoard.BoardController;
import GameBoard.Position;
import GameBoard.Robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Scanner;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private BitmapFont font;
    private final TiledMapTileLayer playerLayer;
    
    private final OrthogonalTiledMapRenderer renderer;
    private final OrthographicCamera camera;
    private final String mapName;
    private final int HEIGHT;
    private final int WIDTH;
	private BoardController gameboard;
	private final ArrayList<Robot> robots;
	private final AI ai = new Randbot();
	private final GUI gui;
	private Stage stage;
	private Table availableTable;
	private Table chosenTable;
    private Table optionsTable;
	protected Robot playerControlledRobot;
    protected TextButton powerdown;
    protected TextButton ready;
    protected TextButton clear;
    protected TextButton options;
    protected TextButton resume;
    protected TextButton quit;
    private int counter = 0;
    private boolean optionscheck = true;

	private final Viewport view;

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
        int CELL_SIZE = 300;

        HEIGHT = backgroundLayer.getHeight()*CELL_SIZE;
        WIDTH = backgroundLayer.getWidth()*CELL_SIZE;

        view = new FitViewport(WIDTH*2, HEIGHT);
        view.setScreenPosition(0,0);
        view.update(WIDTH,HEIGHT, true);

        playerLayer = (TiledMapTileLayer) map.getLayers().get("Robot");

        camera = (OrthographicCamera) view.getCamera();//new OrthographicCamera();

        renderer = new OrthogonalTiledMapRenderer(map, 1);
        //renderer.setView(camera);

        for (Robot bot : robots){
            if (!bot.isControlledByAI()){
                playerControlledRobot = bot;
                break; // TODO: 25.03.2021 Denne må erstattes når vi implementerer multiplayer, og dermed har mer enn én spillerstyrt robot
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
        renderer.dispose();

    }

    public BoardController getGameBoard(){ return gameboard; }

    @Override
    public void show() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glViewport( 0,0,view.getScreenWidth(),view.getScreenHeight());

        //Skin skinLibgdx = new Skin(Gdx.files.internal("assets/default/skin/uiskin.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        chosenTable = new Table();
        availableTable = new Table();
        Table buttonTable = new Table();
        buttonTable.setBounds(Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/6f),0,Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/5f);
        optionsTable = new Table();
        optionsTable.setBounds(Gdx.graphics.getWidth()*3,Gdx.graphics.getHeight()*3,100,100);

        gameboard = new BoardController(robots, mapName);
        updateRobotPositions();

        batch = new SpriteBatch();
        font = new BitmapFont();

        powerdown = new TextButton("Powerdown", gui.getSkin());
        powerdown.setSize(Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/15f );
        powerdown.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (optionscheck){
                    playerControlledRobot.togglePowerDown();
                    playerControlledRobot.resetCards();
                    isDoneChoosing = true;
                }
            }

            /**@Override
            public void clicked(InputEvent event, float x, float y) {
                playerControlledRobot.togglePowerDown();
                playerControlledRobot.resetCards();
                isDoneChoosing = true;
                super.clicked(event, x, y);
            }**/
        });

        ready = new TextButton("  Ready  ", gui.getSkin());
        ready.setSize(Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/15f );
        ready.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(optionscheck){
                    isDoneChoosing = true;
                }
            }

            /**@Override
            public void clicked(InputEvent event, float x, float y) {
                isDoneChoosing = true;
                super.clicked(event, x, y);
            }**/
        });

        clear = new TextButton("  Clear  ", gui.getSkin());
        clear.setSize(Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/15f );
        clear.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(optionscheck){
                    playerControlledRobot.getChosenCards().clear();
                    chosenTable.clear();
                    counter = 0;
                }
            }

            /**@Override
            public void clicked(InputEvent event, float x, float y) {
                playerControlledRobot.getChosenCards().clear();
                chosenTable.clear();
                counter = 0;
                //super.clicked(event, x, y);
            }**/
        });

        options = new TextButton("  options  ", gui.getSkin());
        options.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(optionscheck){
                    optionscheck = false;
                    optionsTable.setBounds(Gdx.graphics.getWidth()/2f-(Gdx.graphics.getWidth()/20f),(Gdx.graphics.getHeight()/2f)-(Gdx.graphics.getHeight()/10f),Gdx.graphics.getWidth()/10f,Gdx.graphics.getHeight()/5f);
                }
            }
        });
        resume = new TextButton("  Resume  ", gui.getSkin());
        resume.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                optionscheck = true;
                optionsTable.setBounds(Gdx.graphics.getWidth()*3,Gdx.graphics.getHeight()*3,100,100);
            }
        });
        quit = new TextButton("  Quit  ", gui.getSkin());
        quit.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.exit(0);
            }
        });


        stage.addActor(chosenTable);
        stage.addActor(availableTable);
        stage.addActor(buttonTable);
        stage.addActor(optionsTable);
        optionsTable.add(resume);
        optionsTable.add(quit);
        buttonTable.add(powerdown).prefWidth((Gdx.graphics.getWidth())/6f).prefHeight(Gdx.graphics.getHeight()/20f);
        buttonTable.row();
        buttonTable.add(ready).prefWidth((Gdx.graphics.getWidth())/6f).prefHeight(Gdx.graphics.getHeight()/20f);
        buttonTable.row();
        buttonTable.add(clear).prefWidth((Gdx.graphics.getWidth())/6f).prefHeight(Gdx.graphics.getHeight()/20f);
        buttonTable.row();
        buttonTable.add(options).prefWidth((Gdx.graphics.getWidth())/6f).prefHeight(Gdx.graphics.getHeight()/20f);

    }

    private boolean hasStartedYet = false;
    private float timeSinceLastUpdate = -1;
    private static final float TIME_DELTA = 1;

    @Override
    public void render(float v) {
        timeSinceLastUpdate += v;
        renderer.render();
        stage.draw();

        //Denne sørger for at vi får tegnet opp GUI-en ferdig i starten av spillet
        // før spilleren blir bedt om å velge kort.
        if(! hasStartedYet) {
            hasStartedYet = true;
            return;
        }

        if(timeSinceLastUpdate < TIME_DELTA) return;
        timeSinceLastUpdate = 0;
        simulate();
        updateRobotPositions();
        renderCards();
    }

    private void updateRobotPositions(){
        for (Position pos : gameboard.getDirtyLocations()){
            Robot bot = gameboard.getRobotAt(pos.getX(), pos.getY());
            if(bot != null){
                Sprite sprt = new Sprite(bot.getImage());
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(sprt));

                //Vi regner positiv rotasjon som med klokken, men libgdx sier det er mot klokken. Derfor tar vi 4-θ.
                cell.setRotation(4 - bot.getDirection());
                playerLayer.setCell(pos.getX(), gameboard.getHeight() - pos.getY() - 1, cell);
            }
            else playerLayer.setCell(pos.getX(), gameboard.getHeight() - pos.getY() - 1, new TiledMapTileLayer.Cell());
        }
    }

    /**
     * Simulerer spillet. Hver gang denne blir kalt, blir ett trekk gjort.
     * Denne husker på hvilket trekk fra hvilken fase som skal blir gjort,
     *  slik at denne kan vite nøyaktig hvor i runden vi er.
     */
    private int phase = 0;
    private int move = 0;
    private void simulate(){
        if (! isDoneChoosing) return;
        if(phase + move == 0){
            gameboard.startRound();
            isDoneChoosing = false;
            for (Robot bot : gameboard.getBots()) {
                if (bot.isControlledByAI()) ai.chooseCards(bot, gameboard.getBoard());
                //else pickCardsFromTerminal(bot);
            }
        }
        if (move == gameboard.getBots().size()){
            move = 0;
            phase++;
            gameboard.endPhase();
            return;
        }
        if(phase == 5){
            phase = 0;
            gameboard.endRound();
            chosenTable.clear();
            counter = 0;
            return;
        }
        gameboard.moveRobot(phase, move);
        move++;
    }

    protected boolean isDoneChoosing = true;
    public void renderCards(){
        availableTable.clear();
        renderer.getBatch().begin();
        boolean odd = false;
        int yscale;
        yscale = (playerControlledRobot.getAvailableCards().size()+1)/2;
        availableTable.setBounds((2*Gdx.graphics.getWidth())/3f,(Gdx.graphics.getHeight()/5f*(5-yscale)),Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/5f*(5-yscale)));
        for (int i = 0; i < playerControlledRobot.getAvailableCards().size(); i++) {
            ICard card = playerControlledRobot.getAvailableCards().get(i);
            Image img = new Image(card.getCardImage()); //må bare konvertere dette til å funke med knapper
            img.setSize(Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/5f);
            img.addListener(new CardListener(i));

            availableTable.add(img);
            if(odd){
                availableTable.row();
                odd = false;
            }
            else odd = true;
        }
        renderer.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        view.update(width,height);
        camera.update();
        renderer.setView(camera);
    }

    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
	
	/**
	 * Maybe an alternative for showPopUp()
	 * prints message at the top of GUI
	 * @param msg meldingen som skal vises
	 */
	public void printMessage(String msg) {batch.begin();
		batch.setProjectionMatrix(camera.combined);
		font.setColor(Color.RED);
		font.draw(batch, msg, WIDTH,  HEIGHT*310);
		font.getData().setScale(5, 5);
		batch.end();
	}

	private class CardListener extends ClickListener{
	    int index;
	    public CardListener(int i){ super(); index = i; }

        @Override
        public void clicked(InputEvent event, float x, float y) {
	        if(optionscheck){
                if (isDoneChoosing) return;
                if (playerControlledRobot.getChosenCards().size() >= Math.min(5, playerControlledRobot.getHP())) return;
                ICard card = playerControlledRobot.getAvailableCards().get(index);
                if (!playerControlledRobot.chooseCard(card)) return;
                counter +=1;
                chosenTable.setBounds((Gdx.graphics.getWidth())/2f,(Gdx.graphics.getHeight()/5f*(5-counter)),Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/5f*(5-counter)));
                chosenTable.add(new Image(card.getCardImage()));
                chosenTable.row();
            }
        }
    }
}
	


