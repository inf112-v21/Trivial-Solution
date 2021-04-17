package GUIMain.Screens;

import GameBoard.Cards.ICard;
import GUIMain.GUI;
import GameBoard.BoardController;
import GameBoard.Position;
import GameBoard.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import NetworkMultiplayer.Messages.InGameMessages.ChosenCards;
import NetworkMultiplayer.Messages.InGameMessages.SanityCheck;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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

import static com.badlogic.gdx.graphics.Color.BLACK;
import static com.badlogic.gdx.graphics.Color.WHITE;

public class GameScreen implements Screen {

    public static float TIME_DELTA = 0.6f;
    public static final int CELL_SIZE = 300;

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
    private final ArrayList<Texture> chosenCards = new ArrayList<>();
    private Table optionsTable;
    private Table buttonTable;
	protected Robot playerControlledRobot;
    protected TextButton powerDown;
    protected TextButton ready;
    protected TextButton clear;
    protected TextButton options;
    private boolean optionsCheck = true;
	private final Viewport smallView;
    private Label label;
    private final boolean isThisMultiPlayer;
    private final boolean amITheHost;

    private float timeSinceLastUpdate = -1; //Denne holder styr på hvor lenge det er siden forrige gang brettet ble tegnet.
    private boolean hasDrawnCardsYet = false;

    private static Sprite backgroundSprite;
    private SpriteBatch spriteBatch;

    private final Label.LabelStyle style;
    public static int fontsize = 30;


    public GameScreen(GameInfo gameInfo, boolean isThisMultiPlayer, boolean amITheHost, GUI gui){
        this.gui = gui;
        this.mapName = gameInfo.getMapName();
        this.robots = gameInfo.getRobots();
        this.isThisMultiPlayer = isThisMultiPlayer;
        this.amITheHost = amITheHost;
        TiledMap map = new TmxMapLoader().load(mapName);

        TiledMapTileLayer backgroundLayer = (TiledMapTileLayer) map.getLayers().get("Background");

        HEIGHT = backgroundLayer.getHeight()*CELL_SIZE;
        WIDTH = backgroundLayer.getWidth()*CELL_SIZE;

        smallView = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Viewport largeView = new FitViewport(WIDTH * 2, HEIGHT);
        largeView.update(WIDTH*2, HEIGHT,true);

        playerLayer = (TiledMapTileLayer) map.getLayers().get("Robot");
        camera = (OrthographicCamera) largeView.getCamera();
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map, 1);
        playerControlledRobot = robots.get(gameInfo.getThisPlayersBotIndex());
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ObliviousFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        style = new Label.LabelStyle();
        parameter.size = fontsize;
        parameter.borderWidth = 3f;
        parameter.color = WHITE;
        parameter.borderColor = BLACK;
        style.font = generator.generateFont(parameter);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        stage.dispose();
        renderer.dispose();
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        Texture backgroundTexture = new Texture(Gdx.files.internal("Background Images/CircuitboardDark.jpg"));
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table cardSlots = new Table();
        Table placements = new Table();
        placements.setBounds(Gdx.graphics.getWidth()/2f,0,Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight() );
        cardSlots.setBounds(Gdx.graphics.getWidth()/2f,0,Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight() );
        for (int i = 0; i<5; i++){
            Texture slots = new Texture(Gdx.files.internal("CardSlots/input.png"));
            Image slot = new Image(slots);
            Texture number = new Texture(Gdx.files.internal("CardSlots/"+i+".png"));
            Image placement = new Image(number);
            placements.add(placement);
            cardSlots.add(slot);
            if( i != 4){
                placements.row();
                cardSlots.row();
            }
        }
        chosenTable = new Table();
        availableTable = new Table();
        buttonTable = new Table();

        gameBoard = new BoardController(robots, mapName, amITheHost);
        updateRobotPositions();
        String playerHealthAndLives = "HP: " + playerControlledRobot.getHP() + " Lives: " + playerControlledRobot.getLives();

        label = new Label(playerHealthAndLives, style);

        createOptions();
        stage.addActor(placements);
        stage.addActor(chosenTable);
        stage.addActor(cardSlots);
        stage.addActor(availableTable);
        createButtons();
        stage.addActor(buttonTable);
        stage.addActor(optionsTable);
    }

    private void createOptions(){
        optionsTable = new Table();
        optionsTable.setVisible(false);

        TextButton resume = new TextButton("Resume", gui.getSkin());
        resume.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                optionsCheck = true;
                optionsTable.setVisible(false);
            }
        });
        TextButton menu = new TextButton("Menu", gui.getSkin());
        menu.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                optionsCheck = false;
                gui.setScreen(new MenuScreen(gui));
            }
        });
        TextButton quit = new TextButton("Quit", gui.getSkin());
        quit.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.exit(0);
            }
        });

        optionsTable.add(resume).size(150f,50f).spaceBottom(10);
        optionsTable.row();
        optionsTable.add(menu).size(150f,50f).spaceBottom(10);
        optionsTable.row();
        optionsTable.add(quit).size(150f,50f).spaceBottom(10);
    }

    private void createButtons(){
        buttonTable.setBounds(Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/6f),0,Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/5f);
        powerDown = new TextButton("Powerdown", gui.getSkin());
        powerDown.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (optionsCheck){
                    playerControlledRobot.togglePowerDown();
                    playerControlledRobot.resetAllCards();

                    if ( ! isThisMultiPlayer) {
                        gameBoard.playersAreReady(); //Om det er singleplayer kan vi bare starte.
                    }
                    else if( ! amITheHost){
                        // TODO: 31.03.2021 Her skal klienten sende en ChosenCards til serveren
                    }
                    else{
                        // TODO: 31.03.2021 Hva skal skje når hosten selv er ferdig med å velge kort? Ingenting? Idk, man
                    }
                }
            }
        });

        ready = new TextButton("Ready", gui.getSkin());
        ready.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(optionsCheck){
                    if( !isThisMultiPlayer) {
                        gameBoard.playersAreReady();
                    }

                    else if (!amITheHost){
                        // TODO: 31.03.2021 Her skal klienten sende en ChooseCArds til serveren
                        // Vi må sjekke at dette virker
                        gui.getClient().sendToServer(new ChosenCards(playerControlledRobot.getChosenCards()));
                    }

                    else{
                        // TODO: 31.03.2021 Hva skal skje når hosten selv er ferdig med å velge kort?
                        // TODO: 17.04.2021 Refactor etterpå.
                        //Vi mp sjekke at dette virker
                        gui.getServer().setHostsChosenCards(playerControlledRobot,playerControlledRobot.getChosenCards());
                    }
                }
            }
        });

        clear = new TextButton("Clear", gui.getSkin());
        clear.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(optionsCheck){
                    playerControlledRobot.resetChosenCards();
                    clearCards();
                    renderCards();
                }
            }
        });

        options = new TextButton("Options", gui.getSkin());
        options.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(optionsCheck){
                    optionsCheck = false;
                    optionsTable.setBounds(Gdx.graphics.getWidth()/2f-(Gdx.graphics.getWidth()/20f),(Gdx.graphics.getHeight()/2f)-(Gdx.graphics.getHeight()/10f),Gdx.graphics.getWidth()/10f,Gdx.graphics.getHeight()/5f);
                    optionsTable.setVisible(true);
                    // TODO: 30.03.2021
                }
            }
        });
        buttonTable.add(label).row();
        buttonTable.add(powerDown).size(Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/25f).row();
        buttonTable.add(ready).size(Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/25f).row();
        buttonTable.add(clear).size(Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/25f).row();
        buttonTable.add(options).size(Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/25f);
    }

    @Override
    public void render(float v) {
        spriteBatch.begin();
        backgroundSprite.draw(spriteBatch);
        spriteBatch.end();
        timeSinceLastUpdate += v;
        renderer.render();
        stage.draw();

        if(timeSinceLastUpdate < TIME_DELTA) return;
        timeSinceLastUpdate = 0;
        gameBoard.simulate();
        updateRobotPositions();
        updateLivesAndHP();

        if(isThisMultiPlayer && ! amITheHost){

            ArrayList<ICard> cardsToChooseFrom = gui.getClient().getCardsToChoseFrom();
            if(cardsToChooseFrom != null){
                playerControlledRobot.setAvailableCards(cardsToChooseFrom);
                clearCards();
                renderCards();
            }
            TreeMap<Robot, ArrayList<ICard>> allChosenCards = gui.getClient().getAllChosenCards();
            if(allChosenCards != null) {

                //Lopper igjennom alle robotene for å matche de valgte kortene til hver robot,
                //slik at klienten kan simulere de tatte valgene.
                for(Robot bot : robots){
                    if(bot.equals(playerControlledRobot)) {
                        if(! allChosenCards.get(bot).equals(bot.getChosenCards())){
                            throw new SanityCheck.UnequalSimulationException("Are you sure these are the correct cards in the correct order?\n" +
                                    "if I send cards to the server, and get cards back, the cards for my robot should all be the same. But they are not.");
                        }
                    }
                    bot.setChosenCards(allChosenCards.get(bot));
                    gameBoard.playersAreReady();

                    //Sørger for å gi serveren beskjed om at simuleringen er ferdig

                }
            }

        }

        else if (isThisMultiPlayer && amITheHost) {

            if(gui.getServer().areAllClientsReady()){
                gui.getServer().distributeCards();
            }
            if (gui.getServer().haveAllClientSentTheirChosenCards()){
                gui.getServer().sendAllChosenCardsToEveryone();
                gameBoard.playersAreReady();
            }
        }

        /**for (Robot bot : gameBoard.getRecentlyDeceasedRobots()){
            gui.showPopUp(bot.getName() + " fucking died, lmao", stage);
            // TODO 06.04.2021: Spillet krasjer når denne blir kalt her
        }**/

        //Dette sørger for at kortene kun blir tegnet én gang per runde. Bedre kjøretid, yay
        if(gameBoard.isWaitingForPlayers()){
            if (hasDrawnCardsYet) return;
            clearCards();
            renderCards();
            hasDrawnCardsYet = true;
        }
        else hasDrawnCardsYet = false;
    }


    private void finishedCheck(){
        //Sjekker om en spiller har vunnet og hvilken screen som skal vises.
        Robot winner = gameBoard.hasWon();
        if(winner != null){
            if(playerControlledRobot.equals(winner)){
                gui.setScreen(new WinScreen(gui));
            }
            else{
                gui.setScreen(new GameOverScreen(gui));
            }
        }

        if(playerControlledRobot.getLives() <= 0){
            gui.setScreen(new GameOverScreen(gui));
        }
        int alive = 0;
        for(Robot bot: robots){
            if (bot.getLives()>0){
                alive++;
            }
        }
        if(alive == 1 && playerControlledRobot.getLives()>0){
            gui.setScreen(new WinScreen(gui));
        }
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
        finishedCheck();
    }
    private void clearCards(){
        availableTable.clear();
        chosenTable.clear();
        chosenCards.clear();
    }

    private void renderCards(){
        renderer.getBatch().begin();
        boolean odd = false;
        int yScale = (playerControlledRobot.getAvailableCards().size()+1)/2;
        availableTable.setBounds((2*Gdx.graphics.getWidth())/3f,(Gdx.graphics.getHeight()/5f*(5-yScale)),Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/5f*(5-yScale)));
        for (int i = 0; i < playerControlledRobot.getAvailableCards().size(); i++) {
            ICard card = playerControlledRobot.getAvailableCards().get(i);
            Image img = new Image(card.getCardImage());
            img.addListener(new CardListener(i));

            if(!chosenCards.contains(card.getCardImage()))
                availableTable.add(img).size(Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/5f);
            else{
                Image emptyImg = new Image();
                availableTable.add(emptyImg).size(Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/5f);
            }

            
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
        smallView.update(width, height);
        camera.update();
        renderer.setView(camera);
    }

    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }


	private class CardListener extends ClickListener{
	    private final int index;
	    public CardListener(int i){ super(); index = i; }

        @Override
        public void clicked(InputEvent event, float x, float y) {
	        if(optionsCheck){
                if (playerControlledRobot.getNumberOfChosenCards() >= Math.min(BoardController.PHASES_PER_ROUND, playerControlledRobot.getHP())) return;
                ICard card = playerControlledRobot.getAvailableCards().get(index);
                chosenCards.add(card.getCardImage());

                if (!playerControlledRobot.chooseCard(card)) return;
                chosenTable.setBounds((Gdx.graphics.getWidth())/2f,
                        (Gdx.graphics.getHeight()/5f*(5-playerControlledRobot.getNumberOfChosenCards())),
                        Gdx.graphics.getWidth()/6f,
                        Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/5f*(5-playerControlledRobot.getNumberOfChosenCards())));
                chosenTable.add(new Image(card.getCardImage()));
                chosenTable.row();

                availableTable.clear();
                renderCards();
            }
        }
    }
}