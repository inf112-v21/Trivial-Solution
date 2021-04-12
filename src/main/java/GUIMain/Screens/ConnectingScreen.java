package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ConnectingScreen extends SimpleScreen{

    private boolean hasTriedToConnectYet = false;
    private boolean waitone = true;

    public ConnectingScreen(GUI gui) {
        super(gui);
    }

    @Override
    public void show(){
        super.show();
        Table table = new Table();
        Label title = new Label("Connecting...", gui.getSkin());
        title.setFontScale(3f);
        table.add(title).spaceBottom(200);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(table);
    }

    @Override
    public void render(float v){
        super.render(v);
        if (waitone){
            waitone = false;
            return;
        }
        if ( ! hasTriedToConnectYet){
            gui.startClient();
            hasTriedToConnectYet = true;
        }
    }

    /** Når en kobling til serveren er etablert kan vi kalle denne får å gå videre til lobbyen. */
    public void connected(){
        gui.setScreen(new LobbyScreen(gui, false));
    }
}
