package GUIMain.Screens;

import GUIMain.GUI;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CreateGameScreen extends SimpleScreen {

    private static final String MAP_LOCATION = "assets/maps";
    private SelectBox<Integer> numberOfRobots;
    private SelectBox<String> choosemapbox;
    private TextField textField;
    private ShapeRenderer sr;

    public CreateGameScreen(GUI gui){
        super(gui);
    }

    @Override
    public void show() {
        super.show();
        Table table = new Table();
        table.setFillParent(true);
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
                ArrayList<Robot> robots = Robot.getDefaultRobots(numberOfRobots.getSelected()-1, design); // -1, siden spilleren inngår i disse robotene
                robots.add(new Robot(textField.getText(), design, false));
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

        Label choosedesign = new Label("Choose robot:", gui.getSkin());
        choosedesign.setFontScale(2f);
        table.add(choosedesign);
        table.row();

        showRobotDesigns();
        table.add(designTable).row();

        Table bottomButtons = new Table();
        bottomButtons.add(start);
        bottomButtons.add(back);
        table.add(bottomButtons);

        stage.addActor(table);

    }

    public static String[] getMapNames(){
        File f = new File(MAP_LOCATION);
        String[] maplist = Arrays.stream(f.list()).filter(n -> !n.equals("TestMap.tmx")).toArray(String[]::new);
        for (int i = 0; i < maplist.length; i++) {
            maplist[i] = maplist[i].substring(0, maplist[i].length()-4);
        }
        return maplist;
    }
}
