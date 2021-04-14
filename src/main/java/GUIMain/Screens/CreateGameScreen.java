package GUIMain.Screens;

import GUIMain.GUI;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.badlogic.gdx.graphics.Color.BLACK;
import static com.badlogic.gdx.graphics.Color.WHITE;

public class CreateGameScreen extends SimpleScreen {

    private static final String MAP_LOCATION = "assets/maps";
    private SelectBox<Integer> numberOfRobots;
    private SelectBox<String> choosemapbox;
    private TextField textField;

    public CreateGameScreen(GUI gui){
        super(gui);
    }

    @Override
    public void show() {
        super.show();
        Table table = new Table();
        table.setFillParent(true);
        Table temp = new Table();


        parameter.borderWidth = 3f;
        parameter.color = WHITE;
        parameter.borderColor = BLACK;
        style.font = generator.generateFont(parameter);;
        Label title = new Label("Create Game",style);
        table.add(title).spaceBottom(80);
        table.row();

        parameter.size = 14;
        style.font = generator.generateFont(parameter);
        Label numberplayerlabel = new Label("Number of players: ", style);
        temp.add(numberplayerlabel).spaceBottom(50);

        numberOfRobots = new SelectBox<>(gui.getSkin());
        numberOfRobots.setItems(2, 3, 4, 5, 6, 7, 8);
        temp.add(numberOfRobots).spaceBottom(50f);
        temp.row();

        Label yourName = new Label("Your robot's name: ", style);
        temp.add(yourName).spaceBottom(50);

        textField = new TextField("", gui.getSkin());
        temp.add(textField).spaceBottom(40f);
        temp.row();

        Label chooseMapLabel = new Label("Choose map: ", style);
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

    public static String[] getMapNames(){
        File f = new File(MAP_LOCATION);
        String[] maplist = Arrays.stream(f.list()).filter(n -> !n.equals("TestMap.tmx")).toArray(String[]::new);
        for (int i = 0; i < maplist.length; i++) {
            maplist[i] = maplist[i].substring(0, maplist[i].length()-4);
        }
        return maplist;
    }
}
