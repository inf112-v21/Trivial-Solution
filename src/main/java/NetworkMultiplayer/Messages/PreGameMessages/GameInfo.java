package NetworkMultiplayer.Messages.PreGameMessages;

import GameBoard.Robot;
import NetworkMultiplayer.Messages.IMessage;
import java.io.Serializable;
import java.util.List;

public class GameInfo implements IMessage,Serializable {
    /**
     * Dette objektet tilsvarer all informasjon en klient
     * trenger for å kunne starte en lokal simulasjon av spillet.
     * Det vil si navnet på mappet, listen over alle robotene som er med,
     * og hvor i den listen denne klienten har roboten sin.
     *
     * Det er viktig at listen over roboter er immutable, for å garantere
     *  at vi alltid har samme rekkefølge på robotene på alle maskinene.
     */
    private final List<Robot> robots;
    private final String mapName;
    private final int thisPlayersBotIndex;



    public GameInfo(List<Robot> robots, String mapName, int thisPlayersBotIndex){
        this.robots = robots;
        this.mapName = mapName;
        this.thisPlayersBotIndex = thisPlayersBotIndex;
    }

    public List<Robot> getRobots() { return robots; }
    public String getMapName() { return mapName; }
    public int getThisPlayersBotIndex() { return thisPlayersBotIndex; }
}
