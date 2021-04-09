package GUIMain.Screens;

import GUIMain.GUI;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameOverScreen extends SimpleScreen {
    private final String message;

    public GameOverScreen(String message, GUI gui){
        super(gui);
        this.message = message;
    }

    @Override
    public void show() {
        super.show();
        Table table = new Table();
        Label title = new Label(message, gui.getSkin());
        title.setFontScale(4);
        table.add(title);
        table.setFillParent(true);
        table.padBottom(350);
        table.row();

        TextButton butt = new TextButton("Main menu", gui.getSkin());
        butt.addListener(event -> {
            gui.setScreen(new MenuScreen(gui))
            ;
            table.add(butt);
            stage.addActor(table);
            return false;
        });
    }
}
