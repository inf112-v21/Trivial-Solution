package Cards;

import java.util.ArrayList;
import java.util.Collections;
public class Deck {
    ArrayList<ICard> cards = new ArrayList<>();
    int counter;
    public Deck(){
        for (int i=0; i<6; i++){
            cards.add(new ProgramCard(-1,0,43+i));
            cards.add(new ProgramCard(1,0,49+i));
            cards.add(new ProgramCard(1,0,55+i));
            cards.add(new ProgramCard(1,0,61+i));
            cards.add(new ProgramCard(2,0,67+i));
            cards.add(new ProgramCard(2,0,63+i));
            cards.add(new ProgramCard(3,0,79+i));
            cards.add(new ProgramCard(0,-1,7+(2*i)));
            cards.add(new ProgramCard(0,1,8+(2*i)));
            cards.add(new ProgramCard(0,-1,19+(2*i)));
            cards.add(new ProgramCard(0,1,20+(2*i)));
            cards.add(new ProgramCard(0,-1,31+(2*i)));
            cards.add(new ProgramCard(0,1,32+(2*i)));
            cards.add(new ProgramCard(0,2,i+1));
        }
    }
    public ArrayList<ICard> shuffledDeck(){
        counter = 0;
        Collections.shuffle(cards);
        return cards;
    }

    public ICard drawCard() {
        ICard drawn = cards.get(counter);
        counter++;
        return drawn ;
    }
}