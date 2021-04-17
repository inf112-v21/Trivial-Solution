package GUIMain.Screens;

import GUIMain.GUI;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import NetworkMultiplayer.Messages.PreGameMessages.SetupRobotNameDesignMessage;
import NetworkMultiplayer.Messages.PreGameMessages.RobotInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.TreeSet;

public class LobbyScreen extends SimpleScreen {

    private TreeSet<String> listOfPlayers = new TreeSet<>();
    private Table table;
    private TextField robotname;
    private Table chooserobottable;
    private Table playerlisttable;
    private SelectBox<String> map;





    public LobbyScreen(GUI gui) {
        super(gui);
    }

    @Override
    public void show() {
        super.show();
        table = new Table();
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        Label title = new Label("Lobby", gui.getSkin());
        table.add(title).row();
        Table settingtable = new Table(gui.getSkin());
        playerlisttable = new Table(gui.getSkin());

        settingtable.add(new Label("Choose map: ", gui.getSkin()));
        map = new SelectBox<>(gui.getSkin());
        map.setItems(CreateGameScreen.getMapNames());
        settingtable.add(map).row();
        table.add(settingtable).row();

        playerlisttable = new Table(gui.getSkin());
        playerlisttable.add(new Label("Currently connected players: ", gui.getSkin())).row();
        table.add(playerlisttable).row();

        chooserobottable = new Table(gui.getSkin());
        chooserobottable.add(new Label("Choose nickname: ", gui.getSkin()));
        robotname = new TextField("", gui.getSkin());
        chooserobottable.add(robotname).row();

        chooserobottable.add(new Label("Choose robot: ", gui.getSkin())).row();
        showRobotDesigns();
        chooserobottable.add(designTable).row();

        TextButton confirm = new TextButton("Confirm robot", gui.getSkin());
        confirm.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (robotname.getText().equals("")){
                    gui.showPopUp("Please choose a nickname for you robot!", stage);
                    return;
                }
                SetupRobotNameDesignMessage msg = gui.getServer().setHostRobot(new RobotInfo(robotname.getText(), design));
                switch (msg) {
                    case ROBOT_DESIGN_AND_NAME_ARE_OKEY:
                        chooserobottable.clear();
                        TextButton butt = new TextButton("Start game", gui.getSkin());
                        butt.addListener(new ChangeListener() {
                            @Override
                            public void changed(ChangeEvent changeEvent, Actor actor) {
                                GameInfo info = gui.getServer().startTheGame(CreateGameScreen.MAP_LOCATION + "/" + map.getSelected() + ".tmx");
                                gui.setScreen(new GameScreen(info, true, true, gui));
                            }
                        });
                        table.add(butt);
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
        chooserobottable.add(confirm);
        table.add(chooserobottable);

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
            playerlisttable.add(name).row();
            listOfPlayers.add(name);
        }


        super.render(v);
    }
}
