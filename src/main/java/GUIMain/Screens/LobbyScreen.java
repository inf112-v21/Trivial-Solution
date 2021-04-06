package GUIMain.Screens;

import GUIMain.GUI;
import NetworkMultiplayer.Messages.ConfirmationMessages;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import NetworkMultiplayer.NetworkClient;
import NetworkMultiplayer.NetworkServer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.kryonet.Connection;

import java.net.InetAddress;
import java.util.ArrayList;

public class LobbyScreen implements Screen {

    private ArrayList<String> listOfPlayers = new ArrayList<>();
    private final GUI gui;
    private Stage stage;
    private Table table;
    private final boolean isHost;
    private boolean hasBeenSetup = false;
    private Connection[] connections;
    private GameInfo gameInfo;



    public LobbyScreen(GUI gui, boolean isHost) {
        this.gui = gui;
        this.isHost = isHost;
        if(isHost) startMultiplayer();
    }

    private void startMultiplayer() {
        if (isHost){
            //Dette er en host/
            NetworkServer server = new NetworkServer();


        } else {
            //Dette er en klient
            NetworkClient client = new NetworkClient();

            //Finner Ip-addressen til hosten.
            InetAddress hostIpadress = client.findServer();

            //Connect to client
            client.connect(hostIpadress.toString());

            if (client.isConnected()){
                client.sendToServer(ConfirmationMessages.CONNECTION_WAS_SUCCESSFUL);
            }
        }

    }

    @Override
    public void show() {
        FitViewport view = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(view);
        table = new Table();
        Gdx.input.setInputProcessor(stage);
        if(isHost) {
            Label title = new Label("Currently connected to:", gui.getSkin());
            title.setFontScale(2f);
            table.add(title);
            table.row();
            // TODO: 06.04.2021 Finn ut av hvordan vi kan vise en liste over tilkoblede klienter
        } else {
            stage = new Stage(new ScreenViewport());
            Label title = new Label("Connecting....", gui.getSkin());
            title.setFontScale(4);
            table.add(title);
            table.row();
            table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            TextButton rtrn = new TextButton("Return", gui.getSkin());
            rtrn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    gui.setScreen(new MenuScreen(gui));
                }
            });
            table.add(rtrn).spaceTop(400);
        }
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(table);
    }

    /** Det klienten skal se når han joiner en lobby. Da må han velge navn og robot. */
    public void connected(){
        if (isHost) throw new IllegalStateException("I am the host, I am always connected by default. This method is meant for clients only!");
        stage.clear();
        table = new Table();
        Label title = new Label("Lobby", gui.getSkin());
        title.setFontScale(2.5f);
        table.add(title).spaceBottom(50);

        // TODO: 06.04.2021 Vis de andre som er med i spillet her

        TextButton rtrn = new TextButton("Return", gui.getSkin());
        rtrn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gui.setScreen(new MenuScreen(gui));
            }
        });
        table.add(rtrn).spaceTop(400);
    }

    public void setGameInfo(GameInfo gameInfo){
        this.gameInfo = gameInfo;
        hasBeenSetup = true;
    }

    public void startTheGame(){
        if (! hasBeenSetup) throw new IllegalStateException("Cannot start the game before receiving game information");
        gui.setScreen(new MultiplayerLoadingScreen(gameInfo, true, gui));
    }

    @Override
    public void render(float v) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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

    @Override
    public void dispose() {

    }
}
