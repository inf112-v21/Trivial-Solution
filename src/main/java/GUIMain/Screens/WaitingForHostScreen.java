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

        Label label = new Label("Waiting for the host to start the game...", gui.getSkin());
        Table table = new Table(gui.getSkin());

        table.add(label);
        stage.addActor(table);
    }


}
