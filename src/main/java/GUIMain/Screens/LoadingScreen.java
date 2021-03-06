package GUIMain.Screens;

import GUIMain.GUI;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static com.badlogic.gdx.graphics.Color.BLACK;
import static com.badlogic.gdx.graphics.Color.WHITE;

public class LoadingScreen extends SimpleScreen {

    private final GameInfo gameInfo;
    private final boolean isThisMultiPlayer;
    private final boolean amITheHost;
    private boolean hasbeensetup = false;

    public LoadingScreen(GameInfo gameInfo, boolean isThisMultiPlayer, boolean amITheHost, GUI gui){
        super(gui);
        this.gameInfo = gameInfo;
        this.isThisMultiPlayer = isThisMultiPlayer;
        this.amITheHost = amITheHost;
    }

    @Override
    public void show() {
        super.show();
        parameter.size = Gdx.graphics.getHeight()/19;
        parameter.borderWidth = 3f;
        parameter.color = WHITE;
        parameter.borderColor = BLACK;
        style.font = generator.generateFont(parameter);
        Table table = new Table();
        Label title = new Label("Loading....", style);
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
