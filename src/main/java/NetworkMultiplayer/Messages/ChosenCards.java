package NetworkMultiplayer.Messages;


import GameBoard.Cards.ICard;

import java.util.ArrayList;

/**
 * Listen over kort som spilleren har valgt.
 * Denne klassen er nærmest identisk til DistributedCards,
 * Vi har det som to klasser for å unngå feil og misforståelser.
 */


public class ChosenCards implements IMessage{
    private final ArrayList<ICard> chosen;
    public ChosenCards(ArrayList<ICard> chosencards){
        chosen = chosencards;
    }

    public ArrayList<ICard> getChosenCards(){ return chosen; }
}
