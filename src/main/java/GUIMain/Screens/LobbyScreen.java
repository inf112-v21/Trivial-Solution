package GUIMain.Screens;

import GUIMain.GUI;
import NetworkMultiplayer.Messages.ConfirmationMessages;
import NetworkMultiplayer.NetworkClient;
import NetworkMultiplayer.NetworkServer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
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
    boolean hasBeenSetup;
    private Connection[] connections;



    public LobbyScreen(GUI gui, boolean isHost) {
        this.gui = gui;
        this.isHost = isHost;
        startMultiplayer();

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
        if(isHost) {
            Table table = new Table();
            stage = new Stage();
            stage.addActor(table);
            Gdx.input.setInputProcessor(stage);
            this.table = new Table(gui.getSkin());
            table.add(this.table);

            this.table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Label title = new Label("Currently connected to:", gui.getSkin());
            title.setAlignment(Align.top);
            title.setFontScale(4);
            this.table.row();
            Label undertitle = new Label("A Trivial Solution", gui.getSkin());
            undertitle.setFontScale(2);
            this.table.add(undertitle);
            this.table.row();
            table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        } else {
            stage = new Stage(new ScreenViewport());
            Table table = new Table();
            Label title = new Label("Loading....", gui.getSkin());
            title.setFontScale(4);
            table.add(title);
            table.setFillParent(true);
            table.padBottom(350);
            stage.addActor(table);
            table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    @Override
    public void render(float v) {
        stage.act();
        stage.draw();
        //if(hasBeenSetup) gui.setScreen(new GameScreen(gameInfo, true, gui));
        //TODO Lag en egen connecting screen til mulitplayer

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
