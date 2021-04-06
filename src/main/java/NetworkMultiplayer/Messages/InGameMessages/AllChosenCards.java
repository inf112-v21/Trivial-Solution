package NetworkMultiplayer.Messages.InGameMessages;

import GameBoard.Cards.ICard;
import NetworkMultiplayer.Messages.IMessage;

import java.util.ArrayList;

public class AllChosenCards implements IMessage {
    /**
     * Listen over lister av hva alle spillerne har valgt av kort.
     * Den første listen er til den første roboten, den andre til den andre, etc.
     * Det gjør at vi må sørge for å aldri endre på rekkefølgen på listen over robotene.
     */
    ArrayList<ArrayList<ICard>> otherChoices;
    public AllChosenCards(ArrayList<ArrayList<ICard>> otherChoices){
        this.otherChoices = otherChoices;
    }

    public ArrayList<ArrayList<ICard>> getOtherChoices(){ return otherChoices; }
}