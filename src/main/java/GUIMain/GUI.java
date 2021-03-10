package GUIMain;


        import Player.Robot;
        import com.badlogic.gdx.Game;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.GL30;

        import javax.swing.*;
        import java.util.ArrayList;

public class GUI extends Game {
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

    public void startGame(ArrayList<Robot> robots, String mapname){
        setScreen(new GameScreen(robots, mapname, this));
    }

    /**
     * Metode som viser et popup-vindu med en valgt beskjed.
     * @param message
     */
    public void showPopUp(String message, String windowTitle){
        JOptionPane.showMessageDialog(null, message, windowTitle, JOptionPane.INFORMATION_MESSAGE);
    }
}