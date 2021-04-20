package GUIMain.Screens.EndOfGameScreens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Skjermen som kommer opp når man vinner
 */
public class WinScreen extends LastScreen {


    public WinScreen(GUI gui) {
        super(gui);
    }

    /**
     * @return - Du vant grafikken.
     */
    public Texture chooseTexture(){
        return new Texture(Gdx.files.internal("Background Images/EndOfGame/Youwon.png"));
    }

}
