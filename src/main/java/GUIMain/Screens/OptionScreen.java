package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    public OptionScreen(GUI gui){
        super();
        this.gui = gui;
    }

    @Override
    public void show() {
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
        title.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        title.setFontScale(3);
        tabell.add(title);
        tabell.row();
        TextButton slow = new TextButton("Slow", gui.getSkin());
        TextButton medium = new TextButton("Medium", gui.getSkin());
        TextButton fast = new TextButton("Fast", gui.getSkin());
        TextButton fullscreen = new TextButton("Fullscreen", gui.getSkin());
        TextButton windowed = new TextButton("Windowed", gui.getSkin());
        Label deltaInfo = new Label("default: Medium", gui.getSkin());
        TextButton returnButton = new TextButton("Return", gui.getSkin());
        speed.add(slow).left();
        speed.add(medium).center();
        speed.add(fast).right();
        screenMode.add(fullscreen);
        screenMode.add(windowed);
        window = false;
        tabell.add(speed);
        tabell.row();
        tabell.add(deltaInfo);
        tabell.row();
        tabell.add(screenMode);
        tabell.row();
        tabell.add(returnButton);

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