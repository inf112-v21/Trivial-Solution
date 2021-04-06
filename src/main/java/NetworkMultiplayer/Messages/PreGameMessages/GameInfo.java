package NetworkMultiplayer.Messages.PreGameMessages;

import GameBoard.Robot;
import NetworkMultiplayer.Messages.IMessage;

import java.util.List;

public class GameInfo implements IMessage {
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
        try{
            robots.add(null);
            throw new IllegalArgumentException("Please make sure you give me an unmodifiable list of robots");
        }catch (UnsupportedOperationException e){
            //Om vi får en exception er alt bra.
        }
        this.robots = robots;
        this.mapName = mapName;
        this.thisPlayersBotIndex = thisPlayersBotIndex;
    }

    public List<Robot> getRobots() { return robots; }
    public String getMapName() { return mapName; }
    public int getThisPlayersBotIndex() { return thisPlayersBotIndex; }
}
