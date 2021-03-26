package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameOverScreen implements Screen {

    private final GUI gui;
    private Stage stage;
    private final String message;

    public GameOverScreen(String message, GUI gui){
        this.message = message;
        this.gui = gui;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Table table = new Table();
        Label title = new Label(message, gui.getSkin());
        title.setFontScale(4);
        table.add(title);
        table.setFillParent(true);
        table.padBottom(350);
        table.row();

        TextButton butt = new TextButton("Main menu", gui.getSkin());
        butt.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gui.setScreen(new MenuScreen(gui));
                return true;
            }
        });
        table.add(butt);

        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        stage.act();
        stage.draw();
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
