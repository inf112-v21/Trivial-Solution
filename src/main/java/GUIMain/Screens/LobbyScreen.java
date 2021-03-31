package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;

public class LobbyScreen implements Screen {

    private ArrayList<String> listOfPlayers = new ArrayList<>();
    private final GUI gui;
    private Stage stage;
    private Table names;
    private final boolean isHost;




    public LobbyScreen(GUI gui, boolean isHost) {
        this.gui = gui;
        this.isHost = isHost;
    }

    @Override
    public void show() { 
        Table table = new Table();
        stage = new Stage();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        names = new Table(gui.getSkin());
        table.add(names);
        addPlayerName("Steinar");
        addPlayerName("Dusan");
    }

    public void addPlayerName(String name){
        names.add(name);
    }

    @Override
    public void render(float v) {
        stage.draw();

    }
    @Override
    public void resize(int i, int i1) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
    @Override
    public void dispose() { }
}
