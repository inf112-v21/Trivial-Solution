package GUIMain.Screens;

import GUIMain.GUI;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static com.badlogic.gdx.graphics.Color.BLACK;
import static com.badlogic.gdx.graphics.Color.WHITE;

public class CreateGameScreen extends SimpleScreen {

    public static final String MAP_LOCATION = "assets/maps";
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
        style.font = generator.generateFont(parameter);
        Label title = new Label("Create Game",style);
        table.add(title).spaceBottom(80);
        table.row();

        parameter.size = Gdx.graphics.getHeight()/44;
        style.font = generator.generateFont(parameter);
        Label numberplayerlabel = new Label("Number of players: ", style);
        temp.add(numberplayerlabel).spaceBottom(50);
        Label chooseMapLabel = new Label("Choose map: ", style);
        Label yourName = new Label("Your robot's name: ", style);
        style.font = generator.generateFont(parameter);
        Label choosedesign = new Label("Choose robot:", style);

        numberOfRobots = new SelectBox<>(gui.getSkin());
        numberOfRobots.setItems(2, 3, 4, 5, 6, 7, 8);
        parameter.size = Gdx.graphics.getHeight()/54;
        parameter.borderWidth = 1f;
        parameter.color = BLACK;
        parameter.borderColor = WHITE;
        temp.add(numberOfRobots).spaceBottom(50);
        numberOfRobots.getStyle().listStyle.font = generator.generateFont(parameter);
        numberOfRobots.getStyle().font = generator.generateFont(parameter);
        temp.row();
        temp.add(yourName).spaceBottom(50);

        textField = new TextField("", gui.getSkin());
        textField.setHeight(parameter.size);
        textField.getStyle().font = generator.generateFont(parameter);
        textField.getStyle().messageFont = generator.generateFont(parameter);
        temp.add(textField).spaceBottom(50f).height(parameter.size*3.5f);
        temp.row();
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
                Collections.shuffle(robots);
                String map = MAP_LOCATION + "/" + choosemapbox.getSelected() + ".tmx";
                gui.setScreen(new LoadingScreen(new GameInfo(Collections.unmodifiableList(robots), map, numberOfRobots.getSelected()-1), false, true, gui));

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
        String[] maplist = Arrays.stream(Objects.requireNonNull(f.list())).filter(n -> !n.equals("TestMap.tmx") && !n.equals("TestMapFlags.tmx")).toArray(String[]::new);
        for (int i = 0; i < maplist.length; i++) {
            maplist[i] = maplist[i].substring(0, maplist[i].length()-4);
        }
        return maplist;
    }
}
