package GUIMain.Screens;

import GUIMain.GUI;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.MinorErrorMessage;
import NetworkMultiplayer.Messages.PreGameMessages.RobotInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.TreeSet;

public class LobbyScreen extends SimpleScreen {

    private final TreeSet<String> listOfPlayers = new TreeSet<>();
    private TextField robotName;
    private Table chooseRobotTable;
    private Table playerListTable;

    public LobbyScreen(GUI gui) {
        super(gui);
    }

    @Override
    public void show() {
        super.show();
        Table table = new Table();
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        Label title = new Label("Lobby", gui.getSkin());
        table.add(title).row();
        Table settingtable = new Table(gui.getSkin());
        playerListTable = new Table(gui.getSkin());

        settingtable.add(new Label("Choose map: ", gui.getSkin()));
        SelectBox<String> map = new SelectBox<>(gui.getSkin());
        map.setItems(CreateGameScreen.getMapNames());
        settingtable.add(map).row();
        table.add(settingtable).row();

        playerListTable = new Table(gui.getSkin());
        playerListTable.add(new Label("Currently connected players: ", gui.getSkin())).row();
        table.add(playerListTable).row();

        chooseRobotTable = new Table(gui.getSkin());
        chooseRobotTable.add(new Label("Choose nickname: ", gui.getSkin()));
        robotName = new TextField("", gui.getSkin());
        chooseRobotTable.add(robotName).row();

        chooseRobotTable.add(new Label("Choose robot: ", gui.getSkin())).row();
        showRobotDesigns();
        chooseRobotTable.add(designTable).row();

        TextButton confirm = new TextButton("Confirm robot", gui.getSkin());
        confirm.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (robotName.getText().equals("")){
                    gui.showPopUp("Please choose a nickname for you robot!", stage);
                    return;
                }
                MinorErrorMessage msg = gui.getServer().setHostRobot(new RobotInfo(robotName.getText(), design));
                if (msg == null){
                    chooseRobotTable.clear();
                }
                else {
                    switch (msg) {
                        case UNAVAILABLE_DESIGN:
                            gui.showPopUp("That robot has already been taken, please choose another one", stage);
                        case UNAVAILABLE_NAME:
                            gui.showPopUp("That name has already been taken, please be more original", stage);
                    }
                }
            }
        });
        chooseRobotTable.add(confirm);
        table.add(chooseRobotTable);

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
            playerListTable.add(name).row();
            listOfPlayers.add(name);
        }
        // TODO: 14.04.2021 Sjekk om serveren har mottatt hostens navn og design, så det kan legges i listen
        // TODO: 14.04.2021 Sjekk resten av serverens tilkoblede roboter, og legg dem til i listOfPlayers
        // TODO: 14.04.2021 Legg til en sjekk for om spillet er klart for å begynne, og bytt deretter til GameScreen.

            super.render(v);
    }
}
