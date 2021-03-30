package NetworkMultiplayer.Messages;
import GameBoard.Cards.ICard;

import java.util.ArrayList;


/**
 * Listen over kort som spilleren har valgt.
 * Denne klassen er nærmest identisk til ChosenCards,
 * Vi har det som to klasser for å unngå feil og misforståelser.
 */
public class DistributedCards extends Message {
    private final ArrayList<ICard> cards;
    public DistributedCards(ArrayList<ICard> cards){
        this.cards = cards;
    }
    public ArrayList<ICard> getChosenCards(){ return cards; }
}
