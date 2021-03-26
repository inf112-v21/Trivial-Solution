package GUIMain;

        import GUIMain.Screens.MenuScreen;
        import com.badlogic.gdx.Game;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Screen;
        import com.badlogic.gdx.graphics.GL30;
        import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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

    public Skin getSkin(){ return skin; }

    public String getSkinString(){return SKIN_NAME;}
}