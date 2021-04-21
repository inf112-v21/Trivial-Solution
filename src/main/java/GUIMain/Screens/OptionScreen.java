package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import static com.badlogic.gdx.graphics.Color.BLACK;
import static com.badlogic.gdx.graphics.Color.WHITE;

public class OptionScreen extends SimpleScreen {

    public OptionScreen(GUI gui){
        super(gui);
    }

    @Override
    public void show() {
        super.show();
        parameter.borderWidth = 3f;
        parameter.color = WHITE;
        parameter.borderColor = BLACK;
        style.font = generator.generateFont(parameter);
        Table tabell = new Table();
        Table speed = new Table();
        Table screenMode = new Table();
        tabell.setFillParent(true);
        Label title = new Label("Options", style);

        parameter.size = Gdx.graphics.getHeight()/45;
        style.font = generator.generateFont(parameter);
        Label display = new Label("Display", style);
        Label gameSpeed = new Label("Game speed", style);

        parameter.size = Gdx.graphics.getHeight()/60;
        style.font = generator.generateFont(parameter);
        tabell.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        tabell.add(title).spaceBottom(Gdx.graphics.getHeight()/27f);
        tabell.row();
        tabell.add(gameSpeed).spaceBottom(Gdx.graphics.getHeight()/22f);
        tabell.row();
        TextButton slow = new TextButton("Slow", gui.getSkin());
        TextButton medium = new TextButton("Medium", gui.getSkin());
        TextButton fast = new TextButton("Fast", gui.getSkin());
        TextButton fullscreen = new TextButton("Fullscreen", gui.getSkin());
        TextButton windowed = new TextButton("Windowed", gui.getSkin());
        Label deltaInfo = new Label("default: Medium", style);
        TextButton returnButton = new TextButton("Return", gui.getSkin());
        speed.add(slow).size(200f,50f);
        speed.add(medium).size(200f,50f);
        speed.add(fast).size(200f,50f);
        screenMode.add(fullscreen).size(300f,50f);
        screenMode.add(windowed).size(300f,50f);
        tabell.add(speed).spaceBottom(Gdx.graphics.getHeight()/44f);
        tabell.row();
        tabell.add(deltaInfo).spaceBottom(Gdx.graphics.getHeight()/22f);
        tabell.row();
        tabell.add(display).spaceBottom(Gdx.graphics.getHeight()/44f);
        tabell.row();
        tabell.add(screenMode).spaceBottom(Gdx.graphics.getHeight()/22f);
        tabell.row();
        tabell.add(returnButton).size(200f,50f);

        stage.addActor(tabell);
        fullscreen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        });
        windowed.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(Gdx.graphics.isFullscreen()){
                    float height = Gdx.graphics.getHeight();
                    float width = Gdx.graphics.getWidth();
                    int scale = 4;
                    Gdx.graphics.setWindowedMode((int) (width-(width/scale)), (int) (height-(height/scale)));
                    ((Lwjgl3Graphics)Gdx.graphics).getWindow().setPosition((int)(width/(2*scale)),(int)(height/(2*scale)));
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
}