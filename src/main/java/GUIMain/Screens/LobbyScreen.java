package GUIMain.Screens;

import GUIMain.GUI;
import GameBoard.Robot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.TreeSet;

public class LobbyScreen extends SimpleScreen {

    private TreeSet<Robot> listOfPlayers = new TreeSet<>();
    private Table table;
    private TextField robotname;
    private Table chooserobottable;
    private Table playerlisttable;





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
        Table settingtable = new Table();
        playerlisttable = new Table();

        settingtable.add(new Label("Choose map: ", gui.getSkin()));
        SelectBox<String> map = new SelectBox<>(gui.getSkin());
        map.setItems(CreateGameScreen.getMapNames());
        settingtable.add(map).row();
        table.add(settingtable).row();

        playerlisttable = new Table();
        playerlisttable.add(new Label("Currently connected players: ", gui.getSkin())).row();
        for (Robot bot : listOfPlayers){
            playerlisttable.add(bot.getName()).row();
        }
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
                // TODO: 14.04.2021 det som skjer når hosten skal prøve å lage en robot
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
        // TODO: 14.04.2021 Sjekk om serveren har mott hostens navn og design, så det kan legges i listen 
        // TODO: 14.04.2021 Sjekk resten av serverens tilkoblede roboter, og legg dem til i listOfPlayers
        // TODO: 14.04.2021 Legg til en sjekk for om spillet er klart for å begynne, og bytt deretter til GameScreen.

        else{
            super.render(v);
        }
    }
}
