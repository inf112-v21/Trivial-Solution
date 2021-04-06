package GUIMain.Screens;

import GUIMain.GUI;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SinglePlayerLoadingScreen implements Screen {

    private final GameInfo gameInfo;
    private final boolean isThisMultiPlayer;
    private final boolean amITheHost;
    private final GUI gui;
    private Stage stage;
    private boolean hasbeensetup = false;

    public SinglePlayerLoadingScreen(GameInfo gameInfo, boolean isThisMultiPlayer, boolean amITheHost, GUI gui){
        this.gameInfo = gameInfo;
        this.isThisMultiPlayer = isThisMultiPlayer;
        this.amITheHost = amITheHost;
        this.gui = gui;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
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
        stage.act();
        stage.draw();
        if(hasbeensetup) gui.setScreen(new GameScreen(gameInfo, isThisMultiPlayer, amITheHost, gui));
        else hasbeensetup = true;
    }

    @Override
    public void resize(int i, int i1) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
    @Override
    public void dispose() { }
}