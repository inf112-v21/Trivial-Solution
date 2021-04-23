package GUIMain.Screens;

import GUIMain.GUI;
import GameBoard.Robot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public abstract class SimpleScreen implements Screen {

    protected final GUI gui;
    protected Stage stage;
    protected FitViewport view;
    protected Sprite backgroundSprite;
    protected SpriteBatch batch;
    protected FreeTypeFontGenerator generator;
    protected FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    protected Label.LabelStyle style;
    protected Table designTable; //Kun til bruk for LobbyScreen, CreateRobotScreen og CreateGameScreen.
    protected int design = 3; //    --------------//-----------------------

    public SimpleScreen(GUI gui){
        this.gui = gui;
    }

    @Override
    public void show() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ObliviousFont.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Gdx.graphics.getHeight()/23;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        style = new Label.LabelStyle();
        view = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(view);
        backgroundSprite = new Sprite(new Texture(Gdx.files.internal("Background Images/roborally1.png")));
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        designTable = new Table();
    }

    @Override
    public void render(float i){
        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int x, int y) { view.update(x, y); }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
    @Override
    public void dispose() { }

    protected void showRobotDesigns() {
        designTable.clear();
        TextureRegion[][] region = new TextureRegion(new Texture("Robotdesigns/robots.png")).split(GameScreen.CELL_SIZE, GameScreen.CELL_SIZE);
        TextureRegion[][] border = new TextureRegion(new Texture("Robotdesigns/bordered_robots.png")).split(300, 300);
        for (int i = 0; i < Robot.NUMBER_OF_DESIGNS; i++) {
            Image img;
            if (design != i) img = new Image(region[0][i]);
            else             img = new Image(border[0][i]);
            img.addListener(new RobotSpriteListener(i));
            designTable.add(img).size(Gdx.graphics.getWidth()/13f, Gdx.graphics.getHeight()/7f);
        }
    }

    protected class RobotSpriteListener extends ClickListener {
        private final int i;
        public RobotSpriteListener(int i){ this.i = i; }
        @Override
        public void clicked(InputEvent event, float x, float y) {
            design = i;
            showRobotDesigns();
        }
    }
}
