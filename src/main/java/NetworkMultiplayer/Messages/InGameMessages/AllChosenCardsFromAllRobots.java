package NetworkMultiplayer.Messages.InGameMessages;

import GameBoard.Cards.ICard;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.IMessage;

import java.io.Serializable;
import java.util.HashMap;

public class AllChosenCardsFromAllRobots implements IMessage, Serializable {

    final private HashMap<Robot,IMessage> allDesicions;
    AllChosenCardsFromAllRobots(HashMap<Robot,IMessage> allDesicions){
        this.allDesicions = allDesicions;
    }

    public HashMap<Robot, IMessage> getAllDesicions() {
        return allDesicions;
    }
}
