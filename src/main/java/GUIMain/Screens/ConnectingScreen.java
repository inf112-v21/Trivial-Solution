package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
        if (gui.getClient() == null){
            gui.startClient();
            gui.tryToConnectClientToServer();
        }
        try {
            //Hvis klienten er null så er vi åpenbart ikke tilkoblet
            if (gui.getClient().isConnected()) {
                gui.setScreen(new CreateRobotScreen(gui));
            }
        } catch (NullPointerException e){
            //Da sender vi spilleren tilbake til menyscreenen
            Stage stage = new Stage();
            Gdx.input.setInputProcessor(stage);

            // TODO: 19.04.2021 Pop-up vises ikke 
            gui.showPopUp("Couldn't find any online servers :(", stage);

            gui.setScreen(new MenuScreen(gui));
        }
    }
}
