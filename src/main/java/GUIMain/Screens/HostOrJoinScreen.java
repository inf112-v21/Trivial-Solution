package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private static Sprite backgroundSprite;
    private SpriteBatch spriteBatch;

    public HostOrJoinScreen(GUI gui){
        this.gui = gui;
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        Texture backgroundTexture = new Texture(Gdx.files.internal("Aesthetic files/roborally1.png"));
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        view = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        Label multiplayer = new Label("Multiplayer", gui.getSkin());
        table.add(multiplayer).spaceBottom(50);
        multiplayer.setFontScale(5f);
        table.row();

        TextButton join = new TextButton("Join Game", gui.getSkin());
        join.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gui.setScreen(new LobbyScreen(gui, false));

            }
        });
        table.add(join).spaceBottom(20);
        table.row();
        TextButton host = new TextButton("Host Game", gui.getSkin());
        host.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gui.setScreen(new LobbyScreen(gui,true));
            }
        });
        table.add(host).spaceBottom(40);
        table.row();
        TextButton back = new TextButton("Return", gui.getSkin());
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gui.setScreen(new MenuScreen(gui));
            }
        });

        table.add(back);

        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );

        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        spriteBatch.begin();
        backgroundSprite.draw(spriteBatch);
        spriteBatch.end();
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
