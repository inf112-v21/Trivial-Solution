package NetworkMultiplayer.Messages.InGameMessages;
import GameBoard.Cards.ProgramCard;
import NetworkMultiplayer.Messages.IMessage;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Listen over kort som spilleren har valgt.
 * Denne klassen er nærmest identisk til ChosenCards,
 * Vi har det som to klasser for å unngå feil og misforståelser.
 */
public class DistributedCards implements IMessage, Serializable {
    private final ArrayList<ProgramCard> cards;
    public DistributedCards(ArrayList<ProgramCard> cards){
        this.cards = cards;
    }
    public ArrayList<ProgramCard> getChosenCards(){ return cards; }
}
