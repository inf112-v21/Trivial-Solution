package GUIMain;

        import GUIMain.Screens.MenuScreen;
        import com.badlogic.gdx.Game;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Screen;
        import com.badlogic.gdx.graphics.GL30;
        import com.badlogic.gdx.scenes.scene2d.Stage;
        import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
        import com.badlogic.gdx.scenes.scene2d.ui.Skin;

        import javax.swing.JOptionPane;

public class GUI extends Game {

    private Skin skin;
    private static final String SKIN_NAME = "assets/comic/skin/comic-ui.json";
    private Screen currentScreen;

    /**
     * Standard GUI. Bruk denne.
     */
    public GUI(){
        super();
        currentScreen = new MenuScreen(this);
    }

    /**
     * GUI kun for testing. Hopper umiddelbart til initialScreen etter konstruktøren er ferdig.
     */
    public GUI(Screen initialScreen){
        super();
        currentScreen = initialScreen;
    }

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal(SKIN_NAME));
        setScreen(currentScreen);
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }

    @Override
    public void setScreen(Screen nextScreen){
        currentScreen = nextScreen;
        super.setScreen(nextScreen);
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

    /**
     * Metode som viser et dialog-vindu med en valgt beskjed.
     * @param message meldingen som skal vises på skjermen
     * @param stage "stage" fra hver screen hvor denne metoden brukes.
     */
    public void showPopUp(String message, Stage stage){
        Skin uiSkin = new Skin(Gdx.files.internal(SKIN_NAME));
        Dialog dialog = new Dialog("", uiSkin) {
            public void result(Object obj) {
                System.out.println("result "+obj);
            }
        };
        dialog.text(message);
        dialog.button("OK", true); //sends "true" as the result
        dialog.show(stage);
    }
}