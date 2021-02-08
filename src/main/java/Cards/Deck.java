package Cards;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList<ICards> cards = new ArrayList<>();
    public Deck(){
        for (int i=0; i<14; i++){
            cards.add(new ProgramCard(1,0,18));
            cards.add(new ProgramCard(2,0,12));
            cards.add(new ProgramCard(3,0,6));
            cards.add(new ProgramCard(-1,0,6));
            cards.add(new ProgramCard(0,1,18));
            cards.add(new ProgramCard(0,-1,18));
            cards.add(new ProgramCard(0,2,6));
        }
    }
    public ArrayList<ICards> shuffledDeck(){
        Collections.shuffle(cards);
        return cards;
    }
}