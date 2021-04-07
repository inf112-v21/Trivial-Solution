package GUIMain.Screens;

import GUIMain.GUI;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SinglePlayerLoadingScreen extends SimpleScreen {

    private final GameInfo gameInfo;
    private final boolean isThisMultiPlayer;
    private final boolean amITheHost;
    private boolean hasbeensetup = false;
    private static Sprite backgroundSprite;

    public SinglePlayerLoadingScreen(GameInfo gameInfo, boolean isThisMultiPlayer, boolean amITheHost, GUI gui){
        super(gui);
        this.gameInfo = gameInfo;
        this.isThisMultiPlayer = isThisMultiPlayer;
        this.amITheHost = amITheHost;
    }

    @Override
    public void show() {
        Table table = new Table();
        Label title = new Label("Loading....", gui.getSkin());
        title.setFontScale(4);
        table.add(title);
        table.setFillParent(true);
        table.padBottom(350);
        stage.addActor(table);

    }

    @Override
    public void render(float v) {
        super.render(v);
        if(hasbeensetup) gui.setScreen(new GameScreen(gameInfo, isThisMultiPlayer, amITheHost, gui));
        else hasbeensetup = true;
    }
}
