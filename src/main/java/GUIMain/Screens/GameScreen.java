package GUIMain.Screens;

import GameBoard.Cards.ICard;
import GUIMain.GUI;
import GameBoard.BoardController;
import GameBoard.Position;
import GameBoard.Robot;

import java.util.ArrayList;
import java.util.List;

import NetworkMultiplayer.Messages.GameInfo;
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

public class GameScreen implements Screen {

    private final float TIME_DELTA = OptionScreen.delta;
    public static final int CELL_SIZE = 300;

    private SpriteBatch batch;
    private BitmapFont font;
    private final TiledMapTileLayer playerLayer;
    private final OrthogonalTiledMapRenderer renderer;
    private final OrthographicCamera camera;
    private final String mapName;
    private final int HEIGHT;
    private final int WIDTH;
	private BoardController gameBoard;
	private final List<Robot> robots;
	private final GUI gui;
	private Stage stage;
	private Table availableTable;
	private Table chosenTable;
    private Table optionsTable;
	protected Robot playerControlledRobot;
    protected TextButton powerDown;
    protected TextButton ready;
    protected TextButton clear;
    protected TextButton options;
    protected TextButton resume;
    protected TextButton quit;
    private boolean optionsCheck = true;
	private final Viewport smallView;
    private Label label;
    private final boolean isThisMultiPlayer;

    private float timeSinceLastUpdate = -1; //Denne holder styr på hvor lenge det er siden forrige gang brettet ble tegnet.
    private boolean hasDrawnCardsYet = false;


    public GameScreen(GameInfo gameInfo, boolean isThisMultiPlayer, GUI gui){
        this.gui = gui;
        this.mapName = gameInfo.getMapName();
        this.robots = gameInfo.getRobots();
        this.isThisMultiPlayer = isThisMultiPlayer;
        TiledMap map = new TmxMapLoader().load(mapName);

        TiledMapTileLayer backgroundLayer = (TiledMapTileLayer) map.getLayers().get("Background");

        HEIGHT = backgroundLayer.getHeight()*CELL_SIZE;
        WIDTH = backgroundLayer.getWidth()*CELL_SIZE;

        smallView = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Viewport largeView = new FitViewport(WIDTH * 2, HEIGHT);
        largeView.update(WIDTH, HEIGHT,true);

        playerLayer = (TiledMapTileLayer) map.getLayers().get("Robot");
        camera = (OrthographicCamera) largeView.getCamera();
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map, 1);
        playerControlledRobot = robots.get(gameInfo.getThisPlayersBotIndex());
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
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        chosenTable = new Table();
        availableTable = new Table();

        gameBoard = new BoardController(robots, mapName);
        updateRobotPositions();

        batch = new SpriteBatch();
        font = new BitmapFont();
        String playerHealthAndLives = "HP: " + playerControlledRobot.getHP() + " Lives: " + playerControlledRobot.getLives();

        label = new Label(playerHealthAndLives, gui.getSkin());
        label.setFontScale(2f);

        createOptions();
        stage.addActor(chosenTable);
        stage.addActor(availableTable);
        stage.addActor(createButtons());
        stage.addActor(optionsTable);
    }

    private void createOptions(){
        optionsTable = new Table();
        optionsTable.setBounds(Gdx.graphics.getWidth()*3,Gdx.graphics.getHeight()*3,100,100);

        resume = new TextButton("  Resume  ", gui.getSkin());
        resume.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                optionsCheck = true;
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

        optionsTable.add(resume);
        optionsTable.row();
        optionsTable.add(quit);
    }

    private Table createButtons(){
        Table buttonTable = new Table();
        buttonTable.setBounds(Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/6f),0,Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/5f);
        powerDown = new TextButton("Powerdown", gui.getSkin());
        powerDown.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (optionsCheck){
                    playerControlledRobot.togglePowerDown();
                    playerControlledRobot.resetAllCards();
                    gameBoard.playersAreReady();
                }
            }
        });


        ready = new TextButton("  Ready  ", gui.getSkin());
        ready.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(optionsCheck){
                    gameBoard.playersAreReady();
                }
            }
        });

        clear = new TextButton("  Clear  ", gui.getSkin());
        clear.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(optionsCheck){
                    playerControlledRobot.resetChosenCards();
                    chosenTable.clear();
                }
            }
        });

        options = new TextButton("  Options  ", gui.getSkin());
        options.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(optionsCheck){
                    optionsCheck = false;
                    optionsTable.setBounds(Gdx.graphics.getWidth()/2f-(Gdx.graphics.getWidth()/20f),(Gdx.graphics.getHeight()/2f)-(Gdx.graphics.getHeight()/10f),Gdx.graphics.getWidth()/10f,Gdx.graphics.getHeight()/5f);
                    // TODO: 30.03.2021
                }
            }
        });

        buttonTable.add(label);
        buttonTable.row();
        buttonTable.add(powerDown);
        buttonTable.row();
        buttonTable.add(ready);
        buttonTable.row();
        buttonTable.add(clear);
        buttonTable.row();
        buttonTable.add(options);

        return buttonTable;
    }

    public void setAvailableCards(ArrayList<ICard> cards){
        if (! isThisMultiPlayer) throw new UnsupportedOperationException("There is no reason to use this method in singleplayer, the boardcontroller automatically set all robot's cards.");
        playerControlledRobot.setAvailableCards(cards);
    }

    public ArrayList<ICard> getChosenCards(){
        if (! isThisMultiPlayer) throw new UnsupportedOperationException("There is no reason to use this method in singleplayer, the boardcontroller automatically set all robot's cards.");
        ArrayList<ICard> ret = new ArrayList<>();
        for (int i = 0; i < BoardController.PHASES_PER_ROUND; i++) {
            ret.add(playerControlledRobot.getNthChosenCard(i));
        }
        return ret;
    }

    @Override
    public void render(float v) {
        timeSinceLastUpdate += v;
        renderer.render();
        stage.draw();

        if(timeSinceLastUpdate < TIME_DELTA) return;
        timeSinceLastUpdate = 0;
        gameBoard.simulate();
        updateRobotPositions();
        updateLivesAndHP();
        for (Robot bot : gameBoard.getRecentlyDeceasedRobots()){
            // TODO: 30.03.2021 Når gui.showPopUp() er implementert kan vi si ifra når folk dør her. 
            //gui.showPopUp(bot.getName() + " fucking died, lmao", "Ooops!");
        }

        //Dette sørger for at kortene kun blir tegnet én gang per runde. Bedre kjøretid, yay
        if(gameBoard.isWaitingForPlayersToPickCards()){
            if (hasDrawnCardsYet) return;
            renderCards();
            hasDrawnCardsYet = true;
        }
        else hasDrawnCardsYet = false;
    }

    private void updateRobotPositions(){
        for (Position pos : gameBoard.getDirtyLocations()){
            Robot bot = gameBoard.getRobotAt(pos.getX(), pos.getY());
            if(bot != null){
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(new Sprite(bot.getImage())));

                //Vi regner positiv rotasjon som med klokken, men libgdx sier det er mot klokken. Derfor tar vi τ-θ.
                cell.setRotation(Robot.TAU - bot.getDirection());
                playerLayer.setCell(pos.getX(), gameBoard.getHeight() - pos.getY() - 1, cell);
            }
            else playerLayer.setCell(pos.getX(), gameBoard.getHeight() - pos.getY() - 1, new TiledMapTileLayer.Cell());
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
	        if(optionsCheck){
                if (playerControlledRobot.getNumberOfChosenCards() >= Math.min(BoardController.PHASES_PER_ROUND, playerControlledRobot.getHP())) return;
                ICard card = playerControlledRobot.getAvailableCards().get(index);
                if (!playerControlledRobot.chooseCard(card)) return;
                chosenTable.setBounds((Gdx.graphics.getWidth())/2f,
                        (Gdx.graphics.getHeight()/5f*(5-playerControlledRobot.getNumberOfChosenCards())),
                        Gdx.graphics.getWidth()/6f,
                        Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/5f*(5-playerControlledRobot.getNumberOfChosenCards())));
                chosenTable.add(new Image(card.getCardImage()));
                chosenTable.row();
            }
        }
    }
}
	


