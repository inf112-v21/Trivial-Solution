package NetworkMultiplayer.Messages.InGameMessages;

import GameBoard.Robot;
import NetworkMultiplayer.Messages.IMessage;

import java.io.Serializable;

public class ChosenRobot implements Serializable, IMessage {

    private final Robot robot;

    public ChosenRobot(Robot robot){
        this.robot = robot;
    }

    public Robot getRobot() {
        return robot;
    }
}
