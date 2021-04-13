package GUIMain.Screens;

import GUIMain.GUI;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;

public class LobbyScreen extends SimpleScreen {

    private ArrayList<String> listOfPlayers = new ArrayList<>();
    private Table table;
    private final boolean isHost;
    private boolean hasBeenSetup = false;
    private boolean hasTriedToConnectYet = false;
    private GameInfo gameInfo;
    private TextField robotname;
    private SelectBox robotdesign;
    private Table chooseTable;



    public LobbyScreen(GUI gui, boolean isHost) {
        super(gui);
        this.isHost = isHost;
    }

    @Override
    public void show() {
        super.show();
        table = new Table();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if(isHost) {
            Label choose = new Label("Choose map: ", gui.getSkin());
            SelectBox<String> choosemap = new SelectBox<>(gui.getSkin());
            choosemap.setItems(CreateGameScreen.getMapNames());
            table.add(choose);
            table.add(choosemap).spaceBottom(100);

        } else {
            TextButton rtrn = new TextButton("Return", gui.getSkin());
            rtrn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    gui.setScreen(new MenuScreen(gui));
                }
            });
            table.add(rtrn).spaceTop(400);
        }
        table.row();
        chooseRobot();
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(table);
    }


    public void chooseRobot(){
        chooseTable = new Table();


        Table lefttable = new Table();
        Table righttable = new Table();

        lefttable.add(new Label("Choose username: ", gui.getSkin()));
        lefttable.row();
        robotname = new TextField("", gui.getSkin());
        righttable.add(robotname);
        righttable.row();

        lefttable.add(new Label("Choose your Robot's design: ", gui.getSkin()));
        robotdesign = new SelectBox<>(gui.getSkin());
        robotdesign.setItems(1, 2, 3, 4, 5, 6, 7, 8);
        righttable.add(robotdesign);
        lefttable.row();

        chooseTable.add(lefttable);
        chooseTable.add(righttable);
        chooseTable.row();

        TextButton send = new TextButton("Confirm", gui.getSkin());
        send.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                // TODO: 09.04.2021 Her skal navn og ID sendes til serveren
            }
        });
        chooseTable.add(send);
        table.add(chooseTable);
    }

    public void showLobby(){
        stage.clear();
        table = new Table();
        Label title = new Label("Lobby", gui.getSkin());
        title.setFontScale(2.5f);
        table.add(title).spaceBottom(50);
        // TODO: 06.04.2021 Vis de andre som er med i spillet her

        TextButton rtrn = new TextButton("Return", gui.getSkin());
        rtrn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gui.setScreen(new MenuScreen(gui));
            }
        });
        table.add(rtrn).spaceTop(400);
    }

    @Override
    public void render(float v) {
        if ( ! hasTriedToConnectYet){
            if (isHost) gui.startServer();
            else gui.startClient();
            hasTriedToConnectYet = true;
        }
        else{
            super.render(v);
        }
        // TODO: 07.04.2021 Legg til en sjekk om at spillet er klart til å starte her eller noe sånt
    }
}
