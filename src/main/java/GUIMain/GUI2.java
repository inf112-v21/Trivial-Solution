package GUIMain;


import Player.Robot;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;

import java.util.ArrayList;

/**
 * GUI kun for å teste ting
 */
public class GUI2 extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    /*
    public void startGame(ArrayList<Robot> robots, String mapname){
        setScreen(new GUI(robots, mapname));
    }

     */
}
