package GUIMain.Screens.EndOfGameScreens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Skjerm som vises når man taper
 */
public class GameOverScreen extends LastScreen {


    public GameOverScreen(GUI gui) {
        super(gui);
    }


    @Override
    public Texture chooseTexture(){
        return new Texture(Gdx.files.internal("Background Images/Gameover.png"));
    }
}
