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

public class MultiplayerLoadingScreen implements Screen {
    private GameInfo gameInfo;
    private boolean isThisMultiPlayer;
    private final GUI gui;
    private Stage stage;
    private boolean hasbeensetup = false;
    private static Sprite backgroundSprite;
    private SpriteBatch spriteBatch;

    public MultiplayerLoadingScreen(GameInfo gameInfo, boolean isThisMultiPlayer, GUI gui) {
        this.gameInfo = gameInfo;
        this.isThisMultiPlayer = isThisMultiPlayer;
        this.gui = gui;
    }

    //TODO 31.03.2021. Lag en metode for multiplayer slik at den connecter med Serveren

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        Texture backgroundTexture = new Texture(Gdx.files.internal("Aesthetic files/roborally1.png"));
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(new ScreenViewport());
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
        spriteBatch.begin();
        backgroundSprite.draw(spriteBatch);
        spriteBatch.end();
        stage.act();
        stage.draw();
        if(hasbeensetup) gui.setScreen(new GameScreen(gameInfo, isThisMultiPlayer, false, gui));
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
