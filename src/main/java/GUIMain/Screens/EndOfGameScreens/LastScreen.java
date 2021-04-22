package GUIMain.Screens.EndOfGameScreens;

import GUIMain.GUI;
import GUIMain.Screens.MenuScreen;
import GUIMain.Screens.SimpleScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public abstract class LastScreen extends SimpleScreen {

    private SpriteBatch spriteBatch;
    
    public LastScreen(GUI gui) {
        super(gui);
    }
    

    @Override
    public void show() {
        super.show();
        spriteBatch = new SpriteBatch();
        Texture backgroundTexture = chooseTexture();
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/3f));

        TextButton butt = new TextButton("Main menu", gui.getSkin());
        table.add(butt);
        stage.addActor(table);
        butt.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gui.setScreen(new MenuScreen(gui));
            }
        });
    }

    @Override
    public void render(float i) {
        spriteBatch.begin();
        backgroundSprite.draw(spriteBatch);
        spriteBatch.end();
        stage.draw();
    }

    /**
     * Abstrakt metode som blir overskrevet i subklassene
     * @return - Den riktige grafikken
     */
    public abstract Texture chooseTexture();
}
