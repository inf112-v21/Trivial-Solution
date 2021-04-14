package GUIMain.Screens;

import GUIMain.GUI;
import GameBoard.Robot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.Color.BLACK;
import static com.badlogic.gdx.graphics.Color.WHITE;

public class LobbyScreen extends SimpleScreen {

    private ArrayList<Robot> listOfPlayers = new ArrayList<>();
    private boolean hasTriedToConnectYet = false;
    private TextField robotname;
    private SelectBox robotdesign;
    private Table chooseTable;
    private Table table;



    public LobbyScreen(GUI gui) {
        super(gui);
    }

    @Override
    public void show() {
        table = new Table();
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        super.show();
        chooseRobot();
        stage.addActor(table);
    }


    public void chooseRobot(){
        chooseTable = new Table();
        chooseTable.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Table topTable = new Table();
        parameter.size = 16;
        parameter.borderWidth = 3f;
        parameter.color = WHITE;
        parameter.borderColor = BLACK;
        smoothfont = generator.generateFont(parameter);
        style.font = smoothfont;
        Label choose = new Label("Choose map: ", style);
        SelectBox<String> choosemap = new SelectBox<>(gui.getSkin());
        choosemap.setItems(CreateGameScreen.getMapNames());
        topTable.add(choose).spaceRight(50f);
        topTable.add(choosemap);


        Table midtable = new Table();
        Table bottomtable = new Table();

        midtable.add(new Label("Choose username: ", style)).spaceRight(50f);
        robotname = new TextField("", gui.getSkin());
        midtable.add(robotname);

        bottomtable.add(new Label("Choose your Robot's design: ", style)).spaceRight(50f);
        robotdesign = new SelectBox<>(gui.getSkin());
        robotdesign.setItems(1, 2, 3, 4, 5, 6, 7, 8);
        bottomtable.add(robotdesign);

        chooseTable.add(topTable).spaceBottom(50f);
        chooseTable.row();
        chooseTable.add(midtable).spaceBottom(50f);
        chooseTable.row();
        chooseTable.add(bottomtable).spaceBottom(50f);
        chooseTable.row();

        TextButton send = new TextButton("Confirm", gui.getSkin());
        send.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                // TODO: 09.04.2021 Her skal navn og ID sendes til serveren
            }
        });
        TextButton rtrn = new TextButton("Return", gui.getSkin());
        rtrn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gui.setScreen(new MenuScreen(gui));
            }
        });
        Table buttons = new Table();
        buttons.add(send);
        buttons.add(rtrn);
        chooseTable.add(buttons);
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

    public void addPlayer(Robot bot){
        listOfPlayers.add(bot);
    }

    @Override
    public void render(float v) {
        if ( ! hasTriedToConnectYet){
            gui.startServer();
            hasTriedToConnectYet = true;
        }
        else{
            super.render(v);
        }
    }
}
