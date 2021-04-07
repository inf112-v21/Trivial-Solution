package GUIMain.Screens;

import GUIMain.GUI;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CreateGameScreen implements Screen {

    private static final String MAP_LOCATION = "assets/maps";
    private Stage stage;
    private final GUI gui;
    private SelectBox<Integer> numberOfRobots;
    private SelectBox<String> choosemapbox;
    private TextField textField;
    private Viewport view;
    private static Sprite backgroundSprite;
    private SpriteBatch spriteBatch;

    public CreateGameScreen(GUI gui){
        super();
        this.gui = gui;
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        Texture backgroundTexture = new Texture(Gdx.files.internal("Background Images/roborally1.png"));
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        view = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(view);
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        Table leftTable = new Table();
        Table rightTable = new Table();
        Table temp = new Table();


        Label title = new Label("Create Game", gui.getSkin());
        title.setFontScale(4);
        title.setAlignment(Align.top);
        table.add(title).spaceBottom(80);
        table.row();

        Label numberplayerlabel = new Label("Number of players: ", gui.getSkin());
        numberplayerlabel.setFontScale(2);
        temp.add(numberplayerlabel).spaceBottom(50);

        numberOfRobots = new SelectBox<>(gui.getSkin());
        numberOfRobots.setItems(2, 3, 4, 5, 6, 7, 8);
        temp.add(numberOfRobots).spaceBottom(50f);
        temp.row();

        Label yourName = new Label("Your robot's name: ", gui.getSkin());
        yourName.setFontScale(2);
        temp.add(yourName).spaceBottom(50);

        textField = new TextField("", gui.getSkin());
        temp.add(textField).spaceBottom(40f);
        temp.row();

        Label chooseMapLabel = new Label("Choose map: ", gui.getSkin());
        chooseMapLabel.setFontScale(2);
        temp.add(chooseMapLabel).spaceBottom(50);

        choosemapbox = new SelectBox<>(gui.getSkin());
        choosemapbox.setItems(getMapNames());
        temp.add(choosemapbox).spaceBottom(50);
        TextButton start = new TextButton("Start game", gui.getSkin());
        start.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (textField.getText().equals("")){
                    gui.showPopUp("Please enter a name for your robot.", stage);
                    return false;
                }
                ArrayList<Robot> robots = Robot.getDefaultRobots(numberOfRobots.getSelected()-1); // -1, siden spilleren inngår i disse robotene
                robots.add(new Robot(textField.getText(), 3, false));
                String map = MAP_LOCATION + "/" + choosemapbox.getSelected() + ".tmx";
                gui.setScreen(new SinglePlayerLoadingScreen(new GameInfo(Collections.unmodifiableList(robots), map, numberOfRobots.getSelected()-1), false, true, gui));

                return true;
            }
        });
        TextButton back = new TextButton("Back", gui.getSkin());
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gui.setScreen(new MenuScreen(gui));
            }
        });

        temp.padBottom(50);
        table.add(temp);
        table.row();
        Table bottomButtons = new Table();
        bottomButtons.add(start);
        bottomButtons.add(back);
        table.add(bottomButtons);

        stage.addActor(table);

    }

    @Override
    public void render(float v) {
        spriteBatch.begin();
        backgroundSprite.draw(spriteBatch);
        spriteBatch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
    }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
    @Override
    public void dispose() { }

    private String[] getMapNames(){
        File f = new File(MAP_LOCATION);
        String[] maplist = Arrays.stream(f.list()).filter(n -> !n.equals("TestMap.tmx")).toArray(String[]::new);
        for (int i = 0; i < maplist.length; i++) {
            maplist[i] = maplist[i].substring(0, maplist[i].length()-4);
        }
        return maplist;
    }
}
