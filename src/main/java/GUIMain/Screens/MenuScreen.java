package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import static com.badlogic.gdx.graphics.Color.*;

public class MenuScreen extends SimpleScreen {

    protected TextButton singleplayer;
    protected TextButton multiplayer;
    protected TextButton options;
    protected TextButton quit;

    public MenuScreen(GUI gui){
        super(gui);
    }

    @Override
    public void show() {
        super.show();
        Table tabell = new Table();
        tabell.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        parameter.size = 58;
        parameter.borderWidth = 3f;
        parameter.color = WHITE;
        parameter.borderColor = BLACK;
        smoothfont = generator.generateFont(parameter);
        style.font = smoothfont;
        Label title = new Label("Robo-Rally",style);
        tabell.add(title).spaceBottom(20);
        tabell.row();
        parameter.size = 36;
        smoothfont = generator.generateFont(parameter);
        style.font = smoothfont;
        Label undertitle = new Label("A Trivial Solution", style);
        tabell.add(undertitle).spaceBottom(40);
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

        tabell.add(singleplayer).size(300f,80f).spaceBottom(20);
        tabell.row();
        tabell.add(multiplayer).size(300f,80f).spaceBottom(20);
        tabell.row();
        tabell.add(options).size(300f,80f).spaceBottom(20);
        tabell.row();
        tabell.add(quit).size(300f,80f).spaceBottom(20);

        stage.addActor(tabell);

    }
    @Override
    public void render(float i){
        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
