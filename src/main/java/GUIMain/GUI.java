package GUIMain;


        import GUIMain.Screens.GameScreen;
        import GUIMain.Screens.MenuScreen;
        import Player.Robot;
        import com.badlogic.gdx.Game;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.GL30;
        import com.badlogic.gdx.scenes.scene2d.ui.Skin;

        import javax.swing.*;
        import java.util.ArrayList;

public class GUI extends Game {

    private Skin skin;
    private static String SKIN_NAME = "assets/default/skin/uiskin.json";

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("assets/default/skin/uiskin.json"));
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

    public Skin getSkin(){ return skin; }
}