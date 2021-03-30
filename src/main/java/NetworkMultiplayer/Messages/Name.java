package NetworkMultiplayer.Messages;

public class Name extends Message {
    /**
     * Navnet og nummeret på design som bruker har valgt.
     * Serveren er nødt til å sjekke at begge disse er unike.
     */

    private final String botName;
    private final int botDesignNr;

    public Name(String botName, int botDesignNr){
        this.botName = botName;
        this.botDesignNr = botDesignNr;
    }

    public String getBotName() { return botName; }
    public int getBotDesignNr() { return botDesignNr; }
}
