package GUIMain.Screens;

import GUIMain.GUI;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import NetworkMultiplayer.Messages.PreGameMessages.SetupRobotNameDesign;
import NetworkMultiplayer.Messages.PreGameMessages.RobotInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.TreeSet;

import static com.badlogic.gdx.graphics.Color.BLACK;
import static com.badlogic.gdx.graphics.Color.WHITE;

public class LobbyScreen extends SimpleScreen {

    private final TreeSet<String> listOfPlayers = new TreeSet<>();
    private TextField robotname;
    private Table chooserobottable;
    private Table playerlisttable;
    private Table buttonTable;
    private Table botDesign;
    private SelectBox<String> map;
    public static int fontsize = 25;


    public LobbyScreen(GUI gui) {
        super(gui);
    }

    @Override
    public void show() {
        super.show();
        Table table = new Table();
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        parameter.borderWidth = 3f;
        parameter.color = WHITE;
        parameter.borderColor = BLACK;
        style.font = generator.generateFont(parameter);
        Label title = new Label("Lobby", style);
        table.add(title).spaceBottom(50f).row();
        Table settingtable = new Table(gui.getSkin());
        playerlisttable = new Table(gui.getSkin());

        parameter.size = 25;
        style.font = generator.generateFont(parameter);
        Label choosenick = new Label("Choose nickname: ", style);
        Label choosebot = new Label("Choose robot: ", style);
        settingtable.add(new Label("Choose map: ", style));
        parameter.size = fontsize;
        style.font = generator.generateFont(parameter);
        Label players = new Label("Currently connected players: ", style);

        map = new SelectBox<>(gui.getSkin());
        parameter.size = 20;
        parameter.borderWidth = 1f;
        parameter.color = WHITE;
        parameter.borderColor = BLACK;
        map.getStyle().font=generator.generateFont(parameter);
        map.getStyle().listStyle.font = generator.generateFont(parameter);
        map.setItems(CreateGameScreen.getMapNames());
        settingtable.add(map).row();
        table.add(settingtable).spaceBottom(25f).row();

        playerlisttable = new Table(gui.getSkin());
        playerlisttable.setBounds(Gdx.graphics.getWidth()-(Gdx.graphics.getWidth()/3f),Gdx.graphics.getHeight()/2f, Gdx.graphics.getWidth()/3f,Gdx.graphics.getHeight()/2f);
        playerlisttable.add(players).row();
        stage.addActor(playerlisttable);

        chooserobottable = new Table(gui.getSkin());
        chooserobottable.add(choosenick);
        robotname = new TextField("", gui.getSkin());
        robotname.getStyle().font = generator.generateFont(parameter);
        chooserobottable.add(robotname);
        table.add(chooserobottable).spaceBottom(25f).row();

        botDesign = new Table();
        botDesign.add(choosebot).row();
        showRobotDesigns();
        botDesign.add(designTable);
        table.add(botDesign).spaceBottom(50f).row();
        TextButton confirm = new TextButton("Confirm robot", gui.getSkin());
        buttonTable = new Table();
        buttonTable.add(confirm);
        table.add(buttonTable);
        confirm.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (robotname.getText().equals("")){
                    gui.showPopUp("Please choose a nickname for you robot!", stage);
                    return;
                }
                SetupRobotNameDesign msg = gui.getServer().setHostRobot(new RobotInfo(robotname.getText(), design));
                switch (msg) {
                    case ROBOT_DESIGN_AND_NAME_ARE_OKEY:
                        chooserobottable.clear();
                        botDesign.clear();
                        buttonTable.clear();
                        TextButton butt = new TextButton("Start game", gui.getSkin());
                        butt.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent changeEvent, Actor actor) {
                                GameInfo info = gui.getServer().startTheGame(CreateGameScreen.MAP_LOCATION + "/" + map.getSelected() + ".tmx");
                                gui.setScreen(new GameScreen(info, true, true, gui));
                            }
                        });
                        buttonTable.add(butt);
                        return;
                    case UNAVAILABLE_DESIGN:
                        gui.showPopUp("That robot has already been taken, please choose another one", stage);
                        return;
                    case UNAVAILABLE_NAME:
                        gui.showPopUp("That name has already been taken, please be more original", stage);
                        return;
                }
            }
        });

        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        if (gui.getServer() == null){
            gui.startServer();
            return;
        }
        for (Robot bot : gui.getServer().getRobotActions().keySet()){
            String name = bot.getName();
            if (listOfPlayers.contains(name)) continue;
            parameter.size = 20;
            parameter.borderWidth = 1f;
            parameter.color = WHITE;
            parameter.borderColor = BLACK;
            style.font = generator.generateFont(parameter);
            Label named = new Label(name,style);
            playerlisttable.add(named).row();
            listOfPlayers.add(name);
        }


        super.render(v);
    }
}
