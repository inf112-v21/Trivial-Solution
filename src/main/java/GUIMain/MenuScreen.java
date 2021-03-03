package GUIMain;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.graphics.Color.*;

public class MenuScreen extends InputAdapter implements Screen, ApplicationListener {

    private Stage stage;
    private Skin skin;
    private Table table;
    private SpriteBatch batch;
    private BitmapFont font;
    private Label title;
    private Label undertitle;

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(RED);

        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("assets/default/skin/uiskin.json"));
        table = new Table();
        //table.setFillParent(true);
        title = new Label("Robo-Rally", skin);
        undertitle = new Label("A Trivial Solution", skin);
        table.add(title);
        table.add(undertitle);
        table.row();
        stage.addActor(table);

        stage.addActor(new TextButton("Click here", skin, "default"));

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        stage.act(v);
        stage.getBatch().begin();
        //stage.getBatch().draw(tempbackground, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();
        stage.draw();
    }

    @Override
    public void create() {



    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        // TODO: 03.03.2021
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
