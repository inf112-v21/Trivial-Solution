package GUIMain;

import Player.Robot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


import java.util.ArrayList;
import java.util.Scanner;

import static com.badlogic.gdx.graphics.Color.*;

public class MenuScreen extends InputAdapter implements Screen {

    private Stage stage;
    private Skin skin;
    private Table table;
    private SpriteBatch batch;
    private BitmapFont font;
    private Label title;
    private Label undertitle;
    protected TextButton singleplayer;
    protected TextButton multiplayer;
    protected TextButton options;
    protected TextButton quit;
    private GUI gui;

    public MenuScreen(GUI gui){
        super();
        this.gui = gui;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(RED);

        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("assets/default/skin/uiskin.json"));
        table = new Table();
        table.setFillParent(true);
        title = new Label("Robo-Rally", skin);
        title.setAlignment(Align.top);
        title.setFontScale(3);
        table.add(title);
        table.row();
        undertitle = new Label("A Trivial Solution", skin);
        undertitle.setFontScale(2);
        table.add(undertitle);
        table.row();

        singleplayer = new TextButton("Singleplayer", skin);
        multiplayer = new TextButton("Multiplayer", skin);
        options = new TextButton("Options", skin);
        quit = new TextButton("Quit", skin);

        singleplayer.setLabel(getButtonLabel("Singleplayer"));
        multiplayer.setLabel(getButtonLabel("Multiplayer"));
        options.setLabel(getButtonLabel("Options"));
        quit.setLabel(getButtonLabel("Quit"));



        singleplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                //createRobot2();
                gui.setScreen(new CreateGameScreen(gui));
            }
        });
        //multiplayer.addListener(new MenuListener());
        //options.addListener(new ChangeListener());
        quit.addListener(new ChangeListener() {
             @Override
             public void changed(ChangeEvent changeEvent, Actor actor) {
                 System.exit(0);
             }
         });

        table.add(singleplayer).prefWidth(200.0f).prefHeight(100.0f);
        table.row();
        table.add(multiplayer).prefWidth(200.0f).prefHeight(100.0f);
        table.row();
        table.add(options).prefWidth(200.0f).prefHeight(100.0f);
        table.row();
        table.add(quit).prefWidth(200.0f).prefHeight(100.0f);

        stage.addActor(table);



    }


    @Override
    public void render(float v) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();


    }

    public Label getButtonLabel(String text){
        Label l = new Label(text, skin);
        l.setAlignment(Align.center);
        return l;
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
