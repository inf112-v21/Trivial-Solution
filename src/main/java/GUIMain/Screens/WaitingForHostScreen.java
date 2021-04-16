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
        Label title = new Label("Waiting for the host to start the game...", gui.getSkin());
        title.setFontScale(3f);
        table.add(title).spaceBottom(200);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(table);
    }

    @Override
    public void render(float i) {

        if(gui.getClient().getSetup() != null){
            gui.setScreen(new GameScreen(gui.getClient().getSetup(),true,false,gui));
        }
        super.render(i);
    }
}
