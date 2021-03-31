package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class HostOrJoinScreen implements Screen {

    private final GUI gui;
    private Stage stage;
    private FitViewport view;

    public HostOrJoinScreen(GUI gui){
        this.gui = gui;
    }

    @Override
    public void show() {
        view = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage();
        Table table = new Table();
        Label multiplayer = new Label("Multiplayer", gui.getSkin());
        table.add(multiplayer).spaceBottom(50);
        multiplayer.setFontScale(5f);
        table.row();

        TextButton join = new TextButton("Join Game", gui.getSkin());
        table.add(join).spaceBottom(40);
        table.row();
        TextButton host = new TextButton("Host Game", gui.getSkin());
        table.add(host).spaceBottom(20);
        table.row();
        TextButton goback = new TextButton("Go Back", gui.getSkin());

        table.add(goback);

        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );

        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {
        view.update(width, height);
    }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
    @Override
    public void dispose() { }
}
