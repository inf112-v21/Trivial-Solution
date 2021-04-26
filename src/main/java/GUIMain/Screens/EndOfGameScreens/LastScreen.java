package GUIMain.Screens.EndOfGameScreens;

import GUIMain.GUI;
import GUIMain.Screens.MenuScreen;
import GUIMain.Screens.SimpleScreen;
import NetworkMultiplayer.NetworkClient;
import NetworkMultiplayer.NetworkServer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class LastScreen extends SimpleScreen {

    private SpriteBatch spriteBatch;
    private final String background;
    private final boolean isThisMultiplayer;
    private final boolean amITheHost;
    private GUI gui;

    
    public LastScreen(EndScreenBackground back, GUI gui, boolean isThisMultiplayer, boolean amITheHost) {
        super(gui);
        switch (back){
            case WIN:
                background = "Background Images/EndOfGame/Youwon.png";
                break;
            case LOSE:
                background = "Background Images/EndOfGame/Gameover.png";
                break;
            case SERVER_DISCONNECTED:
                background = "Background Images/EndOfGame/Serverdc.png";
                break;
            case ALL_CLIENTS_DC:
                background = "Background Images/EndOfGame/allClientsdc.png";
            default:
                throw new UnsupportedOperationException("Could not recognize background: " + back + ", please go to LastScreen and add that case to the list");
        }

        this.isThisMultiplayer = isThisMultiplayer;
        this.amITheHost = amITheHost;
        this.gui = gui;
    }
    

    @Override
    public void show() {
        super.show();
        spriteBatch = new SpriteBatch();
        Texture backgroundTexture = new Texture(Gdx.files.internal(background));
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/3f));

        TextButton butt = new TextButton("Main menu", gui.getSkin());
        table.add(butt);
        stage.addActor(table);
        butt.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gui.setScreen(new MenuScreen(gui));
            }
        });
    }

    @Override
    public void render(float i) {

        if(isThisMultiplayer){
            if(amITheHost){
                if(gui.getServer() != null){
                    NetworkServer host = gui.getServer();
                    host.resetAllGameData();
                    host.stopServerAndDisconnectAllClients();
                    gui.reSetServer();
                }
            }
            else{
                if (gui.getClient() != null) {
                    NetworkClient client = gui.getClient();
                    client.disconnectAndStopClientThread();
                    gui.reSetClient();
                }
            }
        }

        spriteBatch.begin();
        backgroundSprite.draw(spriteBatch);
        spriteBatch.end();
        stage.draw();
    }
}
