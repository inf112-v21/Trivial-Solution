package GUIMain.Screens;

import GUIMain.GUI;
import NetworkMultiplayer.Messages.PreGameMessages.RobotInfo;
import NetworkMultiplayer.Messages.PreGameMessages.SetupRobotNameDesign;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

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
                if (textField.getText().equals("")){
                    gui.showPopUp("Please choose a nickname for you robot!", stage);
                    return;
                }
                gui.getClient().sendToServer(new RobotInfo(textField.getText(),design));
            }

        });
        table.add(confirm);
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void render(float i) {
        super.render(i);
        SetupRobotNameDesign msg = gui.getClient().getState();
        gui.getClient().resetState();
        if (msg == null) return;
        switch (msg) {
            case UNAVAILABLE_DESIGN:
                gui.showPopUp("That robot has already been taken, please choose another one", stage);
                return;
            case UNAVAILABLE_NAME:
                gui.showPopUp("That name has already been taken, please be more original", stage);
                return;
            case ROBOT_DESIGN_AND_NAME_ARE_OKEY:
                gui.setScreen(new WaitingForHostScreen(gui));
                return;
        }
    }
}
