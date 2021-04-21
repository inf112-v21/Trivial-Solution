package GUIMain.Screens;

import GUIMain.GUI;
import GUIMain.Screens.EndOfGameScreens.GameOverScreen;
import GUIMain.Screens.EndOfGameScreens.ServerDisconnectedScreen;
import GUIMain.Screens.EndOfGameScreens.WinScreen;
import GameBoard.BoardController;
import GameBoard.Cards.ProgramCard;
import GameBoard.Position;
import GameBoard.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import NetworkMultiplayer.Messages.ClientDisconnected;
import NetworkMultiplayer.Messages.InGameMessages.ChosenCards;
import NetworkMultiplayer.Messages.InGameMessages.ConfirmationMessage;
import NetworkMultiplayer.Messages.InGameMessages.SanityCheck.UnequalSimulationException;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import NetworkMultiplayer.NetworkServer;
import com.badlogic.gdx.Gdx;
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

public class GameScreen extends SimpleScreen {

    public static float TIME_DELTA = 0.6f;
    public static final int CELL_SIZE = 300;
    private double timeSinceLastBlink = -1;
    private static final float BLINK_DELTA = 0.1f;
    public static boolean shouldLasersBeDrawn = false;
    public static boolean roundFinished;
    private final TreeSet<Position> previousLaserPositions = new TreeSet<>();

    private final TiledMapTileLayer playerLayer;
    private final TiledMapTileLayer laserLayer;
    private final OrthogonalTiledMapRenderer renderer;
    private final OrthographicCamera camera;
    private final String mapName;
    private final int HEIGHT;
    private final int WIDTH;
	private BoardController gameBoard;
	private final List<Robot> robots;
	private Stage stage;
	private Table availableTable;
	private Table chosenTable;
    protected final ArrayList<Integer> chosenIndices = new ArrayList<>();
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

    private boolean isWaitingForCards = true;

    private TreeSet<Position> damagedPositions = new TreeSet<>();
    private int blinkturns = 0;


    public GameScreen(GameInfo gameInfo, boolean isThisMultiPlayer, boolean amITheHost, GUI gui){
        super(gui);
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
        laserLayer = (TiledMapTileLayer) map.getLayers().get("emptyLaserLayer");

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

                //Hvis noen velger å disconnete så må vi håndtere det
                if(isThisMultiPlayer) ServerOrClientChoseToDisconnect();
            }
        });
        TextButton quit = new TextButton("Quit", gui.getSkin());
        quit.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //Hvis noen velger å disconnete så må vi håndtere det
                if(isThisMultiPlayer) ServerOrClientChoseToDisconnect();
                Gdx.app.exit();
            }
        });

        optionsTable.add(resume).size(150f,50f).spaceBottom(10).row();
        optionsTable.add(menu).size(150f,50f).spaceBottom(10).row();
        optionsTable.add(quit).size(150f,50f).spaceBottom(10);
    }

    /**
     * Denne metode blir kalt når enten serveren eller klienten valgte å disconnecte.
     */
    private void ServerOrClientChoseToDisconnect(){
        if (amITheHost){
            NetworkServer theHost = gui.getServer();
            theHost.sendMessageToAllClients(ConfirmationMessage.SERVER_CHOOSE_TO_DISCONNECTED);
            theHost.stopServerAndDisconnectAllClients();
            theHost.resetAllGameData();
            gui.reSetServer();
        } else {
            gui.getClient().disconnectAndStopClientThread();
            gui.reSetClient();
        }
    }

    private void createButtons(){
        buttonTable.setBounds(Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/6f),0,Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/5f);
        powerDown = new TextButton("Powerdown", gui.getSkin());
        powerDown.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (optionsCheck){
                    playerControlledRobot.setPowerDown(true);
                    playerControlledRobot.resetAllCards();
                    availableTable.clear();
                    chosenIndices.clear();
                    chosenTable.clear();
                    ready.setVisible(false);
                    clear.setVisible(false);
                    powerDown.setVisible(false);
                    if ( ! isThisMultiPlayer) {
                        gameBoard.playersAreReady(); //Om det er singleplayer kan vi bare starte.
                    }
                    else if( ! amITheHost){
                        gui.getClient().sendToServer(new ChosenCards(new ArrayList<>()));
                    }
                    else{
                        gui.getServer().setHostsChosenCards();
                    }
                }
            }
        });

        ready = new TextButton("Ready", gui.getSkin());
        ready.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(optionsCheck){
                    ready.setVisible(false);
                    clear.setVisible(false);
                    powerDown.setVisible(false);
                    availableTable.clear();
                    if( !isThisMultiPlayer) {
                        gameBoard.playersAreReady();
                    }
                    else if (!amITheHost){
                        gui.getClient().sendToServer(gameBoard.getSanityCheck());
                        gui.getClient().sendToServer(new ChosenCards(playerControlledRobot.getChosenCards()));
                    }
                    else{
                        gui.getServer().setHostsChosenCards();
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
        timeSinceLastBlink += v;
        renderer.render();
        stage.draw();

        //Får roboter til å blinke
        if (timeSinceLastBlink > BLINK_DELTA && blinkturns != 0){
            makeDamagedRobotsBlink();
            timeSinceLastBlink = 0;
            return;
        }
        if(timeSinceLastUpdate < TIME_DELTA) return;
        timeSinceLastUpdate = 0;

        //Tegning av lasere på slutten av en fase, og gir signal om at skadede roboter skal blinke
        removeLasers();
        if (gameBoard.isThePhaseOver()){
            drawLasers();
            blinkturns = 6;
            damagedPositions = gameBoard.getDamagedPositions();
            return;
        }

        gameBoard.simulate();

        updateRobotPositions();
        updateLivesAndHP();
        finishedCheck();

        if(isThisMultiPlayer ){
            updateMultiplayerProperties();
        }
        else{
            updateCardsOnScreen();
        }
    }

    private void updateCardsOnScreen(){
        if (gameBoard.isWaitingForPlayers()) {
            if (hasDrawnCardsYet) return;
            clearCards();
            renderCards();
            hasDrawnCardsYet = true;
            ready.setVisible(true);
            powerDown.setVisible(true);
            clear.setVisible(true);
        } else hasDrawnCardsYet = false;
    }

    private void updateMultiplayerProperties(){

        try{
            //Dette er for klienten
            if (!amITheHost) {

                //Her sjekker vi om en klient ble disconnected. I så fall må vi fjerne roboten til den lokalt.
                Robot possibleDisconnectedRobot = gui.getClient().getDisconnectedRobot();
                if(possibleDisconnectedRobot!= null){
                    for(Robot bot: robots){
                        if(bot.equals(possibleDisconnectedRobot)){
                            bot.killRobot();
                            robots.remove(possibleDisconnectedRobot);
                        }
                    }
                }

                //Her gir vi serveren besjked om at vi er klare for å motta kort
                if (isWaitingForCards) {
                    gui.getClient().sendToServer(ConfirmationMessage.GAME_WAS_STARTED_AND_CLIENT_IS_READY_TO_RECEIVE_CARDS);
                    isWaitingForCards = false;
                }

                //Her mottar vi kort fra serveren.
                ArrayList<ProgramCard> cardsToChoseFrom = gui.getClient().getCardsToChoseFrom();
                if (cardsToChoseFrom != null) {
                    playerControlledRobot.setAvailableCards(cardsToChoseFrom);
                    hasDrawnCardsYet = false;
                    updateCardsOnScreen();
                }

                //Her gir vi kortene til de ulike spillerne.
                TreeMap<Robot, ArrayList<ProgramCard>> allChosenCards = gui.getClient().getAllChosenCards();
                if (allChosenCards != null) {

                    //Lopper igjennom alle robotene for å matche de valgte kortene til hver robot,
                    //slik at klienten kan simulere de tatte valgene.
                    for (Robot bot : robots) {
                        if (bot.equals(playerControlledRobot)) {
                            if (!allChosenCards.get(bot).equals(bot.getChosenCards())) {
                                throw new UnequalSimulationException("Are you sure these are the correct cards in the correct order?\n" +
                                        "if I send cards to the server, and get cards back, the cards for my robot should all be the same. But they are not.");
                            }
                        }
                        bot.setChosenCards(allChosenCards.get(bot));
                        if (allChosenCards.get(bot).isEmpty()) bot.setPowerDown(true);
                    }
                    gameBoard.playersAreReady();
                    //Sørger for å gi serveren beskjed om at simuleringen er ferdig nå
                    isWaitingForCards = true;
                }

                //Her sjekker vi om hosten valgte å disconnecte selv. Da gir vi beskjed til klienten om det
                if(gui.getClient().getServerIsDown() != null){
                    gui.getClient().disconnectAndStopClientThread();
                    gui.setScreen(new ServerDisconnectedScreen(gui));
                    gui.reSetClient();
                }

            }

            //Dette er Hosten/Serveren sin kode
            else {

                NetworkServer host = gui.getServer();
                //Hvis alle spillerne er klare kan vi begynne å dele ut kort
                if (host.areAllClientsReady() && gameBoard.isWaitingForPlayers()) {
                    host.distributeCards();
                    hasDrawnCardsYet = false;
                    updateCardsOnScreen();
                }

                //Hvis alle spillerne har sendt kortene sine kan vi begynne simuleringen
                if (host.haveAllClientSentTheirChosenCards()) {
                    host.sendAllChosenCardsToEveryone(gameBoard.getSanityCheck());
                    gameBoard.playersAreReady();
                }

                //Dette skjer hvis alle klientene disconnecter.
                //Vi starter uansett ikke et spill før konneksjonene er >0 så
                //derfor kan vi bruke 0 for å sjekke om alle klientene disconnectet
                if(host.getNumberOfConnections() == 0){
                    host.stopServerAndDisconnectAllClients();
                    host.resetAllGameData();
                    gui.setScreen( new ServerDisconnectedScreen(gui));
                    // TODO: 21.04.2021 Lag en "All Clients disconnected" screen og en "Could not find host screen"
                }

                updateCardsOnScreen();
            }

        } catch (NullPointerException e){
            gui.setScreen(new MenuScreen(gui));
        }
    }

    private void finishedCheck(){
        //Sjekker om en spiller har vunnet og hvilken screen som skal vises.
        Robot winner = gameBoard.hasWon();
        if(winner != null){

            //Hvis multiplayer spillet er ferdig så stenger vi serveren og
            //frakobler klientene.
            if(isThisMultiPlayer) {

                if (amITheHost) {
                    NetworkServer server = gui.getServer();
                    server.resetAllGameData();
                    server.stopServerAndDisconnectAllClients();
                    gui.reSetServer();
                } else {
                    gui.reSetClient();
                }
            }

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

    private void makeDamagedRobotsBlink(){
        if (blinkturns % 2 == 0){
            for (Position pos : damagedPositions){
                playerLayer.setCell(pos.getX(), gameBoard.getHeight() - pos.getY() - 1, new TiledMapTileLayer.Cell());
            }
        }
        else{
            for (Position pos : damagedPositions){
                Robot bot = gameBoard.getRobotAt(pos.getX(), pos.getY());
                if (bot == null) continue; //Dette skjer om roboten døde, da skal den ikke tegnes
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(new Sprite(bot.getImage())));
                cell.setRotation(Robot.TAU - bot.getDirection());
                playerLayer.setCell(pos.getX(), gameBoard.getHeight()- pos.getY()-1, cell);
            }
        }
        blinkturns--;
    }

    private void drawLasers(){
        TreeMap<Position, TiledMapTileLayer.Cell> t = gameBoard.getLaserLocations();
        previousLaserPositions.addAll(t.keySet());
        for (Position pos : t.keySet()){
            laserLayer.setCell(pos.getX(), gameBoard.getHeight()-pos.getY()-1, t.get(pos));
        }
        System.out.println(gameBoard.lasere);
        gameBoard.lasere.clear();
    }

    private void removeLasers(){
        for (Position pos : previousLaserPositions){
            laserLayer.setCell(pos.getX(), gameBoard.getHeight()-pos.getY()-1, new TiledMapTileLayer.Cell());
        }
        previousLaserPositions.clear();
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
        chosenIndices.clear();
    }

    private void renderCards(){
        renderer.getBatch().begin();
        boolean odd = false;
        int yScale = (playerControlledRobot.getAvailableCards().size()+1)/2;
        availableTable.setBounds((2*Gdx.graphics.getWidth())/3f,(Gdx.graphics.getHeight()/5f*(5-yScale)),Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/5f*(5-yScale)));
        for (int i = 0; i < playerControlledRobot.getAvailableCards().size(); i++) {
            ProgramCard card = playerControlledRobot.getAvailableCards().get(i);

            if (chosenIndices.contains(i)){
                availableTable.add(new Image()).size(Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/5f);
            }else{
                Image img = new Image(card.getCardImage());
                img.addListener(new CardListener(i));
                availableTable.add(img).size(Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/5f);
            }

            if(odd) availableTable.row();
            odd = ! odd;

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

	private class CardListener extends ClickListener{
	    private final int index;
	    public CardListener(int i){ super(); index = i; }

        @Override
        public void clicked(InputEvent event, float x, float y) {
	        if(optionsCheck){
                if (playerControlledRobot.getNumberOfChosenCards() >= Math.min(BoardController.PHASES_PER_ROUND, playerControlledRobot.getHP())) return;
                ProgramCard card = playerControlledRobot.getAvailableCards().get(index);

                if (!playerControlledRobot.chooseCard(card)) return;
                chosenTable.setBounds((Gdx.graphics.getWidth())/2f,
                        (Gdx.graphics.getHeight()/5f*(5-playerControlledRobot.getNumberOfChosenCards())),
                        Gdx.graphics.getWidth()/6f,
                        Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/5f*(5-playerControlledRobot.getNumberOfChosenCards())));
                chosenTable.add(new Image(card.getCardImage()));
                chosenTable.row();

                availableTable.clear();
                chosenIndices.add(index);
                renderCards();
            }
        }
    }
}