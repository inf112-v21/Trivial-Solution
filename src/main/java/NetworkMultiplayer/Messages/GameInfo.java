package NetworkMultiplayer.Messages;

import GameBoard.Robot;

import java.util.ArrayList;

public class GameInfo {
    /**
     * Dette objektet tilsvarer all informasjon en klient
     * trenger for å kunne starte en lokal simulasjon av spillet.
     * Det vil si navnet på mappet, listen over alle robotene som er med,
     * og hvor i den listen denne klienten har roboten sin.
     */
    private final ArrayList<Robot> robots;
    private final String mapName;
    private final int thisPlayersBotIndex;

    public GameInfo(ArrayList<Robot> robots, String mapName, int thisPlayersBotIndex){
        this.robots = robots;
        this.mapName = mapName;
        this.thisPlayersBotIndex = thisPlayersBotIndex;
    }

    public ArrayList<Robot> getRobots() { return robots; }
    public String getMapName() { return mapName; }
    public int getThisPlayersBotIndex() { return thisPlayersBotIndex; }
}
