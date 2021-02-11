package Cards;

import java.util.ArrayList;
import java.util.Collections;
public class Deck {
    private final ArrayList<ICard> cards = new ArrayList<>();
    private int counter = 0;
    private final int DECKSIZE = 84;

    /**
     * Spawner et dekk på 84 unike kort.
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

    /**
     * Blander alle kortene i bunken, og resetter counteren for hvor mange kort som er trukket.
     */
    public void shuffleDeck(){
        counter = 0;
        Collections.shuffle(cards);
    }

    /**
     * Om 84 kort har blitt utdelt.
     */
    public boolean isEmpty(){
        return counter >= DECKSIZE;
    }

    /**
     * Trekker et kort. Spillerene trenger IKKE å levere det tilbake igjen etterpå.
     * @return ICard, kortet du trakk
     */
    public ICard drawCard() {
        if (isEmpty()) throw new IllegalStateException("Cannot draw more cards than there are in the deck!");
        ICard drawn = cards.get(counter);
        counter++;
        return drawn ;
    }
}
