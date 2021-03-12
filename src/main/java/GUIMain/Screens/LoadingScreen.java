package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import javax.security.auth.callback.TextInputCallback;

public class LoadingScreen implements Screen {

    private GUI gui;
    private Screen nextScreen;
    private Stage stage;

    public LoadingScreen(Screen nextScreen, GUI gui){
        this.gui = gui;
        this.nextScreen = nextScreen;
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
        gui.setScreen(nextScreen);
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
