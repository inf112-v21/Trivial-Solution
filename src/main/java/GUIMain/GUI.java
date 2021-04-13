package GUIMain;

import GUIMain.Screens.LobbyScreen;
import GUIMain.Screens.MenuScreen;
        import NetworkMultiplayer.Messages.ConfirmationMessages;
        import NetworkMultiplayer.NetworkClient;
        import NetworkMultiplayer.NetworkServer;
        import com.badlogic.gdx.Game;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Screen;
        import com.badlogic.gdx.graphics.GL30;
        import com.badlogic.gdx.scenes.scene2d.Stage;
        import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
        import com.badlogic.gdx.scenes.scene2d.ui.Skin;

        import java.net.InetAddress;

public class GUI extends Game {

    private Skin skin;
    private static final String SKIN_NAME = "assets/comic/skin/comic-ui.json";
    private Screen currentScreen;
    private NetworkServer server;
    private NetworkClient client;

    public NetworkServer getServer() {
        return server;
    }

    public NetworkClient getClient() {
        return client;
    }

    /**
     * Standard GUI. Bruk denne.
     */
    public GUI(){
        super();
        currentScreen = new MenuScreen(this);
    }

    /**
     * GUI kun for testing. Hopper umiddelbart til initialScreen etter konstruktøren er ferdig.
     */
    public GUI(Screen initialScreen){
        super();
        currentScreen = initialScreen;
    }

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal(SKIN_NAME));
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        setScreen(currentScreen);
    }

    @Override
    public void setScreen(Screen nextScreen){
        currentScreen.dispose();
        currentScreen = nextScreen;
        super.setScreen(nextScreen);
    }

    public void startServer(){
        server = new NetworkServer();
    }

    public void startClient(){
        try {
            client = new NetworkClient();

            //Finner Ip-addressen til hosten.
            InetAddress hostIpadress = client.findServer();

            //Connect to client
            client.connect(hostIpadress.getHostName());

        }catch (NullPointerException ex){
            Stage stage = new Stage();
            Gdx.input.setInputProcessor(stage);
            showPopUp("Couldn't find any online servers :(", stage);
            setScreen(new MenuScreen(this));
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        super.render();
    }
    
    public Skin getSkin(){ return skin; }

    /**
     * Metode som viser et dialog-vindu med en valgt beskjed.
     * @param message meldingen som skal vises på skjermen
     * @param stage "stage" fra hver screen hvor denne metoden brukes.
     */
    public void showPopUp(String message, Stage stage){
        Skin uiSkin = new Skin(Gdx.files.internal(SKIN_NAME));
        Dialog dialog = new Dialog("", uiSkin) {
            public void result(Object obj) {
                System.out.println("result "+obj);
            }
        };
        dialog.text(message);
        dialog.button("OK", true); //sends "true" as the result
        dialog.show(stage);
    }
}