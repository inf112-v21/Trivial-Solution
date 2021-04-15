package NetworkMultiplayer.Messages.InGameMessages;

import GameBoard.Robot;
import NetworkMultiplayer.Messages.IMessage;

import java.io.Serializable;
import java.util.TreeMap;

public class AllChosenCardsFromAllRobots implements IMessage, Serializable {

    /**
     * Hashmap som mapper robotene til lister med de valgte av kort.
     * Robotene skal bli identifisert hos klientene med navn.
     */

    final private TreeMap<Robot,IMessage> allDesicions;
    AllChosenCardsFromAllRobots(TreeMap<Robot,IMessage> allDesicions){
        this.allDesicions = allDesicions;
    }

    /**
     *
     * @return Hashmap med roboter og deres valgte kort.
     */
    public TreeMap<Robot, IMessage> getAllDesicions() {
        return allDesicions;
    }
}
