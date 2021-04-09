package NetworkMultiplayer.Messages.PreGameMessages;

import NetworkMultiplayer.Messages.IMessage;

public class RobotInfo implements IMessage {
    /**
     * Navnet og nummeret på design som bruker har valgt.
     * Serveren er nødt til å sjekke at begge disse er unike.
     */

    private final String botName;
    private final int botDesignNr;

    public RobotInfo(String botName, int botDesignNr){
        this.botName = botName;
        this.botDesignNr = botDesignNr;
    }

    public String getBotName() { return botName; }
    public int getBotDesignNr() { return botDesignNr; }
}
