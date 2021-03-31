package GUIMain.Screens;

import GUIMain.GUI;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
    private SpriteBatch batch;
    private final GUI gui;
    private SelectBox<Integer> numberOfRobots;
    private SelectBox<String> choosemapbox;
    private TextField textField;
    private Viewport view;

    public CreateGameScreen(GUI gui){
        super();
        this.gui = gui;
    }

    @Override
    public void show() {
        view = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(view);
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);


        Label title = new Label("Create Game", gui.getSkin());
        title.setFontScale(4);
        title.setAlignment(Align.top);
        table.add(title).spaceBottom(80);
        table.row();

        Label numberplayerlabel = new Label("Number of players: ", gui.getSkin());
        numberplayerlabel.setFontScale(2);
        table.add(numberplayerlabel);
        
        numberOfRobots = new SelectBox<>(gui.getSkin());
        numberOfRobots.setItems(2, 3, 4, 5, 6, 7, 8);
        numberOfRobots.scaleBy(3);
        table.add(numberOfRobots).spaceBottom(50);
        table.row();

        Label yourname = new Label("Your robot's name: ", gui.getSkin());
        table.add(yourname);

        textField = new TextField("", gui.getSkin());
        table.add(textField).spaceBottom(50);
        table.row();

        Label choosemaplabel = new Label("Choose map: ", gui.getSkin());
        choosemaplabel.setFontScale(2);
        table.add(choosemaplabel);

        choosemapbox = new SelectBox<>(gui.getSkin());
        choosemapbox.setItems(getMapNames());
        table.add(choosemapbox).spaceBottom(50);
        table.row();
        TextButton start = new TextButton("Start game", gui.getSkin());
        start.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (textField.getText().equals("")){
                    showPopUp("Please enter a name for your robot.");
                    return false;
                }
                ArrayList<Robot> robots = Robot.getDefaultRobots(numberOfRobots.getSelected()-1); // -1, siden spilleren inngår i disse robotene
                robots.add(new Robot(textField.getText(), 3, false));
                String map = MAP_LOCATION + "/" + choosemapbox.getSelected() + ".tmx";
                gui.setScreen(new GameLoadingScreen(new GameInfo(Collections.unmodifiableList(robots), map, numberOfRobots.getSelected()-1), false, gui));

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

    /**
     * Metode som viser et dialog-vindu med en valgt beskjed.
     * @param message meldingen som skal vises på skjermen
     */
    public void showPopUp(String message){
        Skin uiSkin = new Skin(Gdx.files.internal(gui.getSkinString()));
        Dialog dialog = new Dialog("", uiSkin) {
            public void result(Object obj) {
                System.out.println("result "+obj);
            }
        };
        dialog.text(message);
        dialog.button("OK", true); //sends "true" as the result
        dialog.show(stage);
    }
}
