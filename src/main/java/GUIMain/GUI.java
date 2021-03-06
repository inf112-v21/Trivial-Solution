package GUIMain;

import GUIMain.Screens.EndOfGameScreens.EndScreenBackground;
import GUIMain.Screens.EndOfGameScreens.LastScreen;
import GUIMain.Screens.MenuScreen;
    import NetworkMultiplayer.NetworkClient;
    import NetworkMultiplayer.NetworkServer;
    import com.badlogic.gdx.Game;
    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
    import com.badlogic.gdx.scenes.scene2d.ui.Skin;

    import java.net.InetAddress;

public class GUI extends Game {

    private Skin skin;
    private static final String SKIN_NAME = "assets/comic/skin/comic-ui.json";

    //DEVELOPER_MODE=true mode gjør at SanityCheck blir sendt mellom klienter og server for hver runde,
    // som potensiellt kræsjer spillet. I tillegg får vi se stacktrace om noe kræsjer.
    //DEVELOPER_MODE=false gjør at stacktracen fra et kræsj ikke blir vist, spillet bare stoppes.
    public static final boolean DEVELOPER_MODE = true;
    private Screen currentScreen;
    private NetworkServer server;
    private NetworkClient client;



    /**
     * Standard GUI. Bruk denne.
     */
    public GUI(){
        super();
        currentScreen = new MenuScreen(this);
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

    public void startClient(){ client = new NetworkClient();}

    public NetworkServer getServer() {
        return server;
    }
    public void reSetServer(){server = null;}

    public NetworkClient getClient() {
        return client;
    }
    public void reSetClient(){client = null;}


    public void tryToConnectClientToServer(){
        //Finner Ip-addressen til hosten.
        InetAddress hostIpadress = client.findServer();

        if(hostIpadress != null) {
            //Prøver å koble til hosten/serveren
            client.connect(hostIpadress.getHostName());
        }
        else{
            //Dreper threaden
            client.disconnectAndStopClientThread();

            //Her setter vi klienten til null, slik at vi kan starte en ny klient
            //neste gang en spiller klicker join.
            reSetClient();
        }
    }

    /**
     * Fikser at klienten blir terminert i de ulike screen'sene
     * hvis serveren velger å avbrytte koblingen. Funksjonen blir kun kalt
     * Hvis det er sant at serveren ble stengt. Da sender vi clienten
     * til ServerDisconnectedScreen.
     */
    public void didServerChooseToDisconnectThenTerminateClient(){
            getClient().disconnectAndStopClientThread();
            setScreen(new LastScreen(EndScreenBackground.SERVER_DISCONNECTED, this,true,false));
            reSetClient();
    }

    @Override
    public void render() {
        if (DEVELOPER_MODE) {
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
            super.render();
        }
        else{
            try {
                Gdx.gl.glClearColor(1, 1, 1, 1);
                Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
                super.render();
            }catch (Exception ex){
                //Lukker spillet om vi fikk en run-time error.
                //Dermed får ikke brukeren se noen stack-trace eller noe sånt.
                killServerAndClient();
                Gdx.app.exit();
            }
        }
    }

    private void killServerAndClient(){
        if (server != null) server.stopServerAndDisconnectAllClients();
        if (client != null) client.disconnectAndStopClientThread();
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
            @Override
            public void result(Object obj) {
                boolean b = (boolean) obj;
                if(b){
                    hide(null);
                    remove();
                }
            }
        };
        dialog.text(message);
        dialog.button("OK", true); //sends "true" as the result
        dialog.show(stage);
    }

}