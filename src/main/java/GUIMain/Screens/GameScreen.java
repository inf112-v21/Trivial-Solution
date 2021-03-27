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

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private BitmapFont font;
    private final TiledMapTileLayer playerLayer;
    
    private final OrthogonalTiledMapRenderer renderer;
    private final OrthographicCamera camera;
    private final String mapName;
    public static final int CELL_SIZE = 300;
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
    private boolean optionscheck = true;
	private final Viewport smallView;
    private final Viewport largeView;
    private Table buttonTable = new Table();
    private Label label;
    private String playerHealthAndLives;

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

        HEIGHT = backgroundLayer.getHeight()*CELL_SIZE;
        WIDTH = backgroundLayer.getWidth()*CELL_SIZE;

        smallView = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        largeView = new FitViewport(WIDTH*2, HEIGHT);
        largeView.update(WIDTH, HEIGHT,true);

        playerLayer = (TiledMapTileLayer) map.getLayers().get("Robot");
        camera = (OrthographicCamera) largeView.getCamera();//new OrthographicCamera();
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map, 1);

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

    @Override
    public void show() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        //Gdx.gl.glViewport( 0,0,view.getScreenWidth(),view.getScreenHeight());

        //Skin skinLibgdx = new Skin(Gdx.files.internal("assets/default/skin/uiskin.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        chosenTable = new Table();
        availableTable = new Table();
        buttonTable = new Table();
        buttonTable.setBounds(Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/6f),0,Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/5f);
        optionsTable = new Table();
        optionsTable.setBounds(Gdx.graphics.getWidth()*3,Gdx.graphics.getHeight()*3,100,100);

        gameboard = new BoardController(robots, mapName);
        updateRobotPositions();

        batch = new SpriteBatch();
        font = new BitmapFont();
        playerHealthAndLives ="HP: "+playerControlledRobot.getHP()+" Lives: "+playerControlledRobot.getLives();

        label = new Label(playerHealthAndLives, gui.getSkin());
        label.setFontScale(2f);

        powerdown = new TextButton("Powerdown", gui.getSkin());
        powerdown.setSize(Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/15f );
        powerdown.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (optionscheck){
                    playerControlledRobot.togglePowerDown();
                    playerControlledRobot.resetCards();
                    gameboard.playersAreReady();
                }
            }
        });

        ready = new TextButton("  Ready  ", gui.getSkin());
        ready.setSize(Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/15f );
        ready.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(optionscheck){
                    gameboard.playersAreReady();
                }
            }
        });

        clear = new TextButton("  Clear  ", gui.getSkin());
        clear.setSize(Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/15f );
        clear.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(optionscheck){
                    playerControlledRobot.getChosenCards().clear();
                    chosenTable.clear();
                }
            }
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
        optionsTable.row();
        optionsTable.add(quit);
        buttonTable.add(label);
        buttonTable.row();
        buttonTable.add(powerdown);
        buttonTable.row();
        buttonTable.add(ready);
        buttonTable.row();
        buttonTable.add(clear);
        buttonTable.row();
        buttonTable.add(options);

    }
    private float timeSinceLastUpdate = -1;
    private static final float TIME_DELTA = 0.7f;
    private boolean hasDrawnCardsYet = false;

    @Override
    public void render(float v) {
        timeSinceLastUpdate += v;
        renderer.render();
        stage.draw();

        if(timeSinceLastUpdate < TIME_DELTA) return;
        timeSinceLastUpdate = 0;
        gameboard.simulate();
        updateRobotPositions();
        updateLivesAndHP();
        if(gameboard.isWaitingForPlayersToPickCards()){
            if (hasDrawnCardsYet) return;
            renderCards();
            hasDrawnCardsYet = true;
        }
        else hasDrawnCardsYet = false;
    }

    private void updateRobotPositions(){
        for (Position pos : gameboard.getDirtyLocations()){
            Robot bot = gameboard.getRobotAt(pos.getX(), pos.getY());
            if(bot != null){
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(new Sprite(bot.getImage())));

                //Vi regner positiv rotasjon som med klokken, men libgdx sier det er mot klokken. Derfor tar vi τ-θ.
                cell.setRotation(Robot.TAU - bot.getDirection());
                playerLayer.setCell(pos.getX(), gameboard.getHeight() - pos.getY() - 1, cell);
            }
            else playerLayer.setCell(pos.getX(), gameboard.getHeight() - pos.getY() - 1, new TiledMapTileLayer.Cell());
        }
    }
    private void renderCards(){
        availableTable.clear();
        chosenTable.clear();
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

    private void updateLivesAndHP(){
        label.setText("HP: "+playerControlledRobot.getHP()+" Lives: "+playerControlledRobot.getLives());
    }

    @Override
    public void resize(int width, int height) {
        smallView.update(width,height);
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
	    private final int index;
	    public CardListener(int i){ super(); index = i; }

        @Override
        public void clicked(InputEvent event, float x, float y) {
	        if(optionscheck){
                if (playerControlledRobot.getChosenCards().size() >= Math.min(Robot.MAX_CHOSEN_CARDS, playerControlledRobot.getHP())) return;
                ICard card = playerControlledRobot.getAvailableCards().get(index);
                if (!playerControlledRobot.chooseCard(card)) return;
                chosenTable.setBounds((Gdx.graphics.getWidth())/2f,
                        (Gdx.graphics.getHeight()/5f*(5-playerControlledRobot.getChosenCards().size())),
                        Gdx.graphics.getWidth()/6f,
                        Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/5f*(5-playerControlledRobot.getChosenCards().size())));
                chosenTable.add(new Image(card.getCardImage()));
                chosenTable.row();
            }
        }
    }
}
	


