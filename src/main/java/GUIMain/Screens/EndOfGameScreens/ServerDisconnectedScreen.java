package GUIMain.Screens.EndOfGameScreens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ServerDisconnectedScreen extends LastScreen {

    public ServerDisconnectedScreen(GUI gui) {
        super(gui);
    }

    @Override
    public Texture chooseTexture() {

        return new Texture(Gdx.files.internal("Background Images/EndOfGame/Serverdc.png"));
    }
}
