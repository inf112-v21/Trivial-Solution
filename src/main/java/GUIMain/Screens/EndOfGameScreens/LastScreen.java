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

public class LastScreen extends SimpleScreen {

    private SpriteBatch spriteBatch;
    private String background;
    
    public LastScreen(EndScreenBackground back, GUI gui) {
        super(gui);
        switch (back){
            case WIN:
                background = "Background Images/EndOfGame/Youwon.png";
                break;
            case LOSE:
                background = "Background Images/EndOfGame/Gameover.png";
                break;
            case SERVER_DISCONNECTED:
                background = "Background Images/EndOfGame/Serverdc.png";
                break;
            case ALL_CLIENTS_DC:
                background = "Background Images/EndOfGame/allClientsdc.png";
            default:
                throw new UnsupportedOperationException("Could not recognize background: " + back + ", please go to LastScreen and add that case to the list");
        }
    }
    

    @Override
    public void show() {
        super.show();
        spriteBatch = new SpriteBatch();
        Texture backgroundTexture = new Texture(Gdx.files.internal(background));
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
}
