package GUIMain;

        import GUIMain.Screens.MenuScreen;
        import com.badlogic.gdx.Game;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.GL30;
        import com.badlogic.gdx.scenes.scene2d.ui.Skin;

        import javax.swing.*;

public class GUI extends Game {

    private Skin skin;
    private static final String SKIN_NAME = "assets/comic/skin/comic-ui.json";

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal(SKIN_NAME));
        setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    /**
     * Metode som viser et popup-vindu med en valgt beskjed.
     * @param message meldingen som skal vises på skjermen
     */
    public void showPopUp(String message, String windowTitle){
        JOptionPane.showMessageDialog(null, message, windowTitle, JOptionPane.INFORMATION_MESSAGE);
    }

    public Skin getSkin(){ return skin; }
}