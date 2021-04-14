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

    private Table designTable;
    private TextField textField;
    protected int design = 0;

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

        designTable = new Table();
        showRobotDesigns(-1);
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


    protected void showRobotDesigns(int chosenDesign) {
        designTable.clear();
        TextureRegion[][] region = new TextureRegion(new Texture("Robotdesigns/robots.png")).split(GameScreen.CELL_SIZE, GameScreen.CELL_SIZE);
        for (int i = 0; i < Robot.NUMBER_OF_DESIGNS; i++) {
            Image img = new Image(region[0][i]);
            img.addListener(new RobotSpriteListener(i));
            designTable.add(img).size(Gdx.graphics.getWidth()/11f, Gdx.graphics.getHeight()/8f);
        }
    }

    private class RobotSpriteListener extends ClickListener {
        private int i;
        public RobotSpriteListener(int i){ this.i = i; }
        @Override
        public void clicked(InputEvent event, float x, float y) {
            design = i;
            showRobotDesigns(i);
        }
    }
}
