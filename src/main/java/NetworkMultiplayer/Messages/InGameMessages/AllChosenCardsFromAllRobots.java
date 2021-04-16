package NetworkMultiplayer.Messages.InGameMessages;

import GameBoard.Cards.ICard;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.IMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

public class AllChosenCardsFromAllRobots implements IMessage, Serializable {

    /**
     * Hashmap som mapper robotene til lister med de valgte av kort.
     * Robotene skal bli identifisert hos klientene med navn.
     */

    final private TreeMap<Robot,ArrayList<ICard>> allDesicions;
    AllChosenCardsFromAllRobots(TreeMap<Robot,ArrayList<ICard>> allDesicions){
        this.allDesicions = allDesicions;
    }

    /**
     *
     * @return Hashmap med roboter og deres valgte kort.
     */
    public TreeMap<Robot, ArrayList<ICard>> getAllDesicions() {
        return allDesicions;
    }


}
