package GUIMain.Screens;

import GUIMain.GUI;
import GUIMain.Screens.GameScreen;
import Player.Robot;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class GameLoadingScreen implements Screen {

    private GUI gui;
    private Stage stage;
    private ArrayList<Robot> robots;
    private String mapname;
    private boolean hasbeensetup = false;

    public GameLoadingScreen(ArrayList<Robot> robots, String mapname, GUI gui){
        this.gui = gui;
        this.robots = robots;
        this.mapname = mapname;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Table table = new Table();
        Label title = new Label("Loading....", gui.getSkin());
        title.setFontScale(4);
        table.add(title);
        table.setFillParent(true);
        table.padBottom(350);
        stage.addActor(table);

    }

    @Override
    public void render(float v) {
        stage.act();
        stage.draw();
        if(hasbeensetup) gui.setScreen(new GameScreen(robots, mapname, gui));
        else hasbeensetup = true;
    }

    @Override
    public void resize(int i, int i1) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
    @Override
    public void dispose() { }
}
