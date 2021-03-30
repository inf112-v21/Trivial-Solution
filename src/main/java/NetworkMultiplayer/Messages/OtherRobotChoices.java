package NetworkMultiplayer.Messages;

import GameBoard.Cards.ICard;

import java.util.ArrayList;

public class OtherRobotChoices extends Message{
    /**
     * Listen over lister av hva alle spillerne har valgt av kort.
     * Den første listen er til den første roboten, den andre til den andre, etc.
     * Det gjør at vi må sørge for å aldri endre på rekkefølgen på listen over robotene.
     */
    ArrayList<ArrayList<ICard>> otherChoices;
    public OtherRobotChoices(ArrayList<ArrayList<ICard>> otherChoices){
        this.otherChoices = otherChoices;
    }

    public ArrayList<ArrayList<ICard>> getOtherChoices(){ return otherChoices; }
}
