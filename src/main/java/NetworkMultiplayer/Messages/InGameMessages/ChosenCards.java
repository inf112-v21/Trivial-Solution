package NetworkMultiplayer.Messages.InGameMessages;


import GameBoard.Cards.ProgramCard;
import NetworkMultiplayer.Messages.IMessage;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Listen over kort som spilleren har valgt.
 * Denne klassen er nærmest identisk til DistributedCards,
 * Vi har det som to klasser for å unngå feil og misforståelser.
 */


public class ChosenCards implements IMessage, Serializable {
    private final ArrayList<ProgramCard> chosen;
    public ChosenCards(ArrayList<ProgramCard> chosencards){
        chosen = chosencards;
    }

    public ArrayList<ProgramCard> getChosenCards(){ return chosen; }
}
