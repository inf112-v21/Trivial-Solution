package Cards;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList cards = new ArrayList<ICards>();
    public void fillDeck(){
        for (int i=0; i<14; i++){
            cards.add(new MoveOne());
            cards.add(new MoveTwo());
            cards.add(new MoveThree());
            cards.add(new RotateLeft());
            cards.add(new RotateRight());
            cards.add(new UTurn());
        }
    }
    public ArrayList<ICards> shuffledDeck(){
        Collections.shuffle(cards);
        return cards;
    }
}
