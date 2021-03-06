package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


public class WaitingForHostScreen extends SimpleScreen{

    public WaitingForHostScreen(GUI gui) {
        super(gui);
    }

    public void show(){
        super.show();

        Table table = new Table();
        parameter.size = Gdx.graphics.getHeight()/19;
        parameter.borderWidth = 2f;
        style.font = generator.generateFont(parameter);
        Label title = new Label("Waiting for the host to start the game...", style);
        table.add(title).spaceBottom(200);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(table);
    }

    @Override
    public void render(float i) {

        if(gui.getClient().getSetup() != null){
            gui.setScreen(new LoadingScreen(gui.getClient().getSetup(), true, false, gui));
            //gui.setScreen(new GameScreen(gui.getClient().getSetup(),true,false,gui));
        }
        if(gui.getClient().getServerIsDown() != null){
            gui.didServerChooseToDisconnectThenTerminateClient();
        }
        super.render(i);
    }
}
