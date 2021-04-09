package GUIMain.Screens;

import GUIMain.GUI;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MultiplayerLoadingScreen extends SimpleScreen {
    private GameInfo gameInfo;
    private boolean isThisMultiPlayer;
    private boolean hasbeensetup = false;

    public MultiplayerLoadingScreen(GameInfo gameInfo, boolean isThisMultiPlayer, GUI gui) {
        super(gui);
        this.gameInfo = gameInfo;
        this.isThisMultiPlayer = isThisMultiPlayer;
    }

    //TODO 31.03.2021. Lag en metode for multiplayer slik at den connecter med Serveren

    @Override
    public void show() {
        super.show();
        Table table = new Table();
        Label title = new Label("Connecting....", gui.getSkin());
        title.setFontScale(4);
        table.add(title);
        table.setFillParent(true);
        table.padBottom(350);
        stage.addActor(table);

    }

    @Override
    public void render(float v) {
        super.render(v);
        if(hasbeensetup) gui.setScreen(new GameScreen(gameInfo, isThisMultiPlayer, false, gui));
        else hasbeensetup = true;
    }
}
