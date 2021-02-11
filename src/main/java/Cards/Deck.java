package Cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Samueljacoo
 *
 */

public class Deck {
    ArrayList<ICard> cards = new ArrayList<>();
    int counter;

    /**
     * Builds the deck out of programCards
     */
    public Deck(){
        for (int i=0; i<6; i++){
            cards.add(new ProgramCard(0,2,i+1));
            cards.add(new ProgramCard(0,-1,7+(2*i)));
            cards.add(new ProgramCard(0,1,8+(2*i)));
            cards.add(new ProgramCard(0,-1,19+(2*i)));
            cards.add(new ProgramCard(0,1,20+(2*i)));
            cards.add(new ProgramCard(0,-1,31+(2*i)));
            cards.add(new ProgramCard(0,1,32+(2*i)));
            cards.add(new ProgramCard(-1,0,43+i));
            cards.add(new ProgramCard(1,0,49+i));
            cards.add(new ProgramCard(1,0,55+i));
            cards.add(new ProgramCard(1,0,61+i));
            cards.add(new ProgramCard(2,0,67+i));
            cards.add(new ProgramCard(2,0,73+i));
            cards.add(new ProgramCard(3,0,79+i));
        }
    }

    public void shuffledDeck(){
        //Shuffles the list with ProgramCards in it
        counter = 0;
        Collections.shuffle(cards);
    }

    /**
     * @return the card at the top of the deck
     */
    public ICard drawCard() {
        ICard drawn = cards.get(counter);
        counter++;
        return drawn ;
    }
}
