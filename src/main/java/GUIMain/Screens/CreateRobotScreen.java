package GUIMain.Screens;

import GUIMain.GUI;
import NetworkMultiplayer.Messages.PreGameMessages.RobotInfo;
import NetworkMultiplayer.Messages.PreGameMessages.SetupRobotNameDesign;
import NetworkMultiplayer.NetworkClient;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
        parameter.size = Gdx.graphics.getHeight()/44;
        parameter.borderWidth = 2f;
        style.font = generator.generateFont(parameter);
        Label chooseName = new Label("Choose nickname: ", style);
        Label chooseRobot = new Label("Choose robot: ", style);
        nametable.add(chooseName);
        parameter.color = Color.BLACK;
        parameter.size = Gdx.graphics.getHeight()/54;
        parameter.borderWidth = 1f;
        textField = new TextField("", gui.getSkin());
        textField.getStyle().font = generator.generateFont(parameter);
        textField.getStyle().messageFont = generator.generateFont(parameter);
        nametable.add(textField).row();
        table.add(nametable).row();

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
        NetworkClient client = gui.getClient();

        if(client.getServerIsDown() != null){
            gui.didServerChooseToDisconnectThenTerminateClient();
        }

        SetupRobotNameDesign msg = client.getState();
        client.resetState();
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
