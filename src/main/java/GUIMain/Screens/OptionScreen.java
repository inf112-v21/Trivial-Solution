package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import static com.badlogic.gdx.graphics.Color.*;

public class OptionScreen extends InputAdapter implements Screen {
    private Stage stage;
    private final GUI gui;
    private Viewport view;
    private boolean window;
    private static Sprite backgroundSprite;
    private static Texture backgroundTexture;
    private SpriteBatch spriteBatch;

    public OptionScreen(GUI gui){
        super();
        this.gui = gui;
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        backgroundTexture = new Texture(Gdx.files.internal("Aesthetic files/roborally1.jpg"));
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        view = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(view);
        BitmapFont font = new BitmapFont();
        font.setColor(RED);

        Gdx.input.setInputProcessor(stage);
        Table tabell = new Table();
        Table speed = new Table();
        Table screenMode = new Table();
        tabell.setFillParent(true);
        Label title = new Label("Options", gui.getSkin());
        Label gameSpeed = new Label("Game speed", gui.getSkin());
        tabell.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        title.setFontScale(4f);
        gameSpeed.setFontScale(2f);
        tabell.add(title).spaceBottom(40f);
        tabell.row();
        tabell.add(gameSpeed).spaceBottom(10f);
        tabell.row();
        TextButton slow = new TextButton("Slow", gui.getSkin());
        TextButton medium = new TextButton("Medium", gui.getSkin());
        TextButton fast = new TextButton("Fast", gui.getSkin());
        TextButton fullscreen = new TextButton("Fullscreen", gui.getSkin());
        TextButton windowed = new TextButton("Windowed", gui.getSkin());
        Label deltaInfo = new Label("default: Medium", gui.getSkin());
        TextButton returnButton = new TextButton("Return", gui.getSkin());
        speed.add(slow).size(200f,50f);
        speed.add(medium).size(200f,50f);
        speed.add(fast).size(200f,50f);
        screenMode.add(fullscreen).size(300f,50f);
        screenMode.add(windowed).size(300f,50f);
        Label display = new Label("Display", gui.getSkin());
        display.setFontScale(2f);
        window = false;
        tabell.add(speed).spaceBottom(10f);
        tabell.row();
        tabell.add(deltaInfo).spaceBottom(40f);
        tabell.row();
        tabell.add(display).spaceBottom(10f);
        tabell.row();
        tabell.add(screenMode).spaceBottom(40f);
        tabell.row();
        tabell.add(returnButton).size(200f,50f);

        stage.addActor(tabell);
        fullscreen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                window = false;
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        });
        windowed.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(!window){
                    Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/3),Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/3));
                    ((Lwjgl3Graphics)Gdx.graphics).getWindow().setPosition((Gdx.graphics.getWidth()/4),(Gdx.graphics.getHeight()/4));
                    window = true;
                }
            }
        });
        slow.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                GameScreen.TIME_DELTA = 0.9f;
            }
        });
        medium.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                GameScreen.TIME_DELTA = 0.6f;
            }
        });
        fast.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                GameScreen.TIME_DELTA = 0.3f;
            }
        });
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gui.setScreen(new MenuScreen(gui));
            }
        });

    }

    @Override
    public void render(float v) {
        spriteBatch.begin();
        backgroundSprite.draw(spriteBatch);
        spriteBatch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {
        view.update(i,i1);
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