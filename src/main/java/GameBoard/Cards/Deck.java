package GameBoard.Cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<ICard> cards = new ArrayList<>();
    private int counter = 0;
    public static final int DECK_SIZE = 84;

    /**
     * Spawner et dekk på 84 unike kort.
     */
    public Deck(){
        for (int i=0; i<6; i++){
            //endre slik at hvert programkort har unik prioritet
            //TODO: Ser ut som at gdx.files.internal ikke klarer å lese filer før gdx er initialisert
            // https://stackoverflow.com/questions/10690982/libgdx-external-storage-causes-nullpointerexception-on-android/12149817
            cards.add(new ProgramCard(0,2,10));
            cards.add(new ProgramCard(0,-1,20));
            cards.add(new ProgramCard(0,1,30));
            cards.add(new ProgramCard(0,-1,40));
            cards.add(new ProgramCard(0,1,50));
            cards.add(new ProgramCard(0,-1,60));
            cards.add(new ProgramCard(0,1,70));
            cards.add(new ProgramCard(-1,0,80));
            cards.add(new ProgramCard(1,0,90));
            cards.add(new ProgramCard(1,0,100));
            cards.add(new ProgramCard(1,0,110));
            cards.add(new ProgramCard(2,0,140));
            cards.add(new ProgramCard(2,0,150));
            cards.add(new ProgramCard(3,0,170));
        }
        shuffleDeck();
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
        return counter >= DECK_SIZE;
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
