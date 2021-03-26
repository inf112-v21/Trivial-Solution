package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import static com.badlogic.gdx.graphics.Color.*;

public class MenuScreen extends InputAdapter implements Screen {

    private Stage stage;
    protected TextButton singleplayer;
    protected TextButton multiplayer;
    protected TextButton options;
    protected TextButton quit;
    private final GUI gui;
    private Viewport view;

    public MenuScreen(GUI gui){
        super();
        this.gui = gui;
    }

    @Override
    public void show() {
        view = new FitViewport(960,540);
        stage = new Stage(view);
        BitmapFont font = new BitmapFont();
        font.setColor(RED);

        Gdx.input.setInputProcessor(stage);
        Table tabell = new Table();
        tabell.setFillParent(true);
        Label title = new Label("Robo-Rally", gui.getSkin());
        title.setAlignment(Align.top);
        title.setFontScale(3);
        tabell.add(title);
        tabell.row();
        Label undertitle = new Label("A Trivial Solution", gui.getSkin());
        undertitle.setFontScale(2);
        tabell.add(undertitle);
        tabell.row();

        singleplayer = new TextButton("Singleplayer", gui.getSkin());
        multiplayer = new TextButton("Multiplayer", gui.getSkin());
        options = new TextButton("Options", gui.getSkin());
        quit = new TextButton("Quit", gui.getSkin());




        singleplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                //createRobot2();
                gui.setScreen(new CreateGameScreen(gui));
            }
        });
        multiplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gui.showPopUp("You don't have any friends lmao", "");
            }
        });
        //options.addListener(new ChangeListener())

        quit.addListener(new ChangeListener() {
             @Override
             public void changed(ChangeEvent changeEvent, Actor actor) {
                 System.exit(0);
             }
         });

        tabell.add(singleplayer).prefWidth(200.0f).prefHeight(100.0f);
        tabell.row();
        tabell.add(multiplayer).prefWidth(200.0f).prefHeight(100.0f);
        tabell.row();
        tabell.add(options).prefWidth(200.0f).prefHeight(100.0f);
        tabell.row();
        tabell.add(quit).prefWidth(200.0f).prefHeight(100.0f);

        stage.addActor(tabell);

    }

    @Override
    public void render(float v) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public Label getButtonLabel(String text){
        Label l = new Label(text, gui.getSkin());
        l.setAlignment(Align.center);
        return l;
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
