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
        return new Texture(Gdx.files.internal("Background Images/Youwon.png"));
    }

}
