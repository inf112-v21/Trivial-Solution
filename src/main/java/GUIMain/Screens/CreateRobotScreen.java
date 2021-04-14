package GUIMain.Screens;

import GUIMain.GUI;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.PreGameMessages.RobotInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CreateRobotScreen extends SimpleScreen{

    private TextField textField;

    public CreateRobotScreen(GUI gui) {
        super(gui);
    }

    @Override
    public void show() {
        super.show();

        Table table = new Table();

        Table nametable = new Table();
        Label chooseName = new Label("Choose nickname: ", gui.getSkin());
        chooseName.setFontScale(2f);
        nametable.add(chooseName);
        textField = new TextField("", gui.getSkin());
        nametable.add(textField).row();
        table.add(nametable).row();

        Label chooseRobot = new Label("Choose robot: ", gui.getSkin());
        chooseRobot.setFontScale(2f);
        table.add(chooseRobot).row();

        showRobotDesigns();
        table.add(designTable).row();

        TextButton confirm = new TextButton("Confirm", gui.getSkin());
        confirm.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                gui.getClient().sendToServer(new RobotInfo(textField.getText(), design));
            }
        });
        table.add(confirm);
        table.setFillParent(true);
        stage.addActor(table);
    }
}
