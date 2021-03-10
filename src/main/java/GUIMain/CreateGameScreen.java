package GUIMain;

import Player.Robot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class CreateGameScreen implements Screen {

    private Stage stage;
    private Skin skin;
    private Table table;
    private SpriteBatch batch;
    private GUI gui;
    private TextButton start;
    private SelectBox<Integer> numberOfRobots;
    private TextField textField;
    private static String defaultMapName = "TestMap.tmx";

    public CreateGameScreen(GUI gui){
        super();
        this.gui = gui;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("assets/default/skin/uiskin.json"));
        table = new Table();
        table.setFillParent(true);
        Label title = new Label("Create Game", skin);
        title.setFontScale(3);
        title.setAlignment(Align.top);



        table.add(title);
        table.row();

        table.add(new Label("Number of players: ", skin));
        table.row();


        numberOfRobots =new SelectBox<>(skin);
        numberOfRobots.setItems(2, 3, 4, 5, 6, 7, 8);

        table.add(numberOfRobots);
        table.row();

        table.add(new Label("Your name: ", skin));
        table.row();

        textField = new TextField("", skin);
        table.add(textField);
        table.row();

        start = new TextButton("Start game", skin);
        start.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (textField.getText().equals("")){
                    gui.showPopUp("Please enter a name for your robot.", "");
                    return false;
                }
                ArrayList<Robot> robots = Robot.getDefaultRobots(numberOfRobots.getSelected()-1); // -1, siden spilleren inngår i disse robotene
                robots.add(new Robot(textField.getText(), Color.BLUE, false));
                gui.setScreen(new GameScreen(robots, defaultMapName, gui));

                return true;
            }
        });
        table.add(start);

        stage.addActor(table);

    }

    @Override
    public void render(float v) {
        stage.act();
        batch.begin();
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
