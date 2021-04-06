package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;


import static com.badlogic.gdx.graphics.Color.*;

public class MenuScreen extends InputAdapter implements Screen {

    private Stage stage;
    protected TextButton singleplayer;
    protected TextButton multiplayer;
    protected TextButton options;
    protected TextButton quit;
    private final GUI gui;
    private FitViewport view;
    private static Sprite backgroundSprite;
    private static Texture backgroundTexture;
    private SpriteBatch spriteBatch;

    public MenuScreen(GUI gui){
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
        tabell.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Label title = new Label("Robo-Rally", gui.getSkin());
        title.setFontScale(3);
        title.getStyle().fontColor = WHITE;
        title.setAlignment(Align.top);
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
                gui.setScreen(new CreateGameScreen(gui));
            }
        });
        multiplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gui.setScreen(new HostOrJoinScreen(gui));
            }
        });
        options.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gui.setScreen(new OptionScreen(gui));
            }
        });
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                System.exit(0);
            }
        });

        tabell.add(singleplayer).size(300f,80f);
        tabell.row();
        tabell.add(multiplayer).size(300f,80f);
        tabell.row();
        tabell.add(options).size(300f,80f);
        tabell.row();
        tabell.add(quit).size(300f,80f);

        stage.addActor(tabell);

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
    public void dispose() {
    }
}
