package Cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
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
            cards.add(new ProgramCard(0,2,10, new Texture(Gdx.files.internal("1 Red HULK X90/010 U-TURN 1Red 4.png"))));
            cards.add(new ProgramCard(0,-1,20,new Texture(Gdx.files.internal("1 Red HULK X90/020 ROTATE LEFT 1Red 3.png"))));
            cards.add(new ProgramCard(0,1,30,new Texture(Gdx.files.internal("1 Red HULK X90/030 ROTATE RIGHT 1Red 3.png"))));
            cards.add(new ProgramCard(0,-1,40,new Texture(Gdx.files.internal("1 Red HULK X90/040 ROTATE LEFT 1Red 3.png"))));
            cards.add(new ProgramCard(0,1,50,new Texture(Gdx.files.internal("1 Red HULK X90/030 ROTATE RIGHT 1Red 3.png"))));
            cards.add(new ProgramCard(0,-1,60,new Texture(Gdx.files.internal("1 Red HULK X90/060 ROTATE LEFT 1Red 3.png"))));
            cards.add(new ProgramCard(0,1,70,new Texture(Gdx.files.internal("1 Red HULK X90/070 ROTATE RIGHT 1Red 3.png"))));
            cards.add(new ProgramCard(-1,0,80, new Texture(Gdx.files.internal("1 Red HULK X90/080 BACK-UP 1Red 3.png"))));
            cards.add(new ProgramCard(1,0,90, new Texture(Gdx.files.internal("1 Red HULK X90/090 MOVE1 1Red 3.png"))));
            cards.add(new ProgramCard(1,0,100, new Texture(Gdx.files.internal("1 Red HULK X90/100 MOVE1 1Red 3.png"))));
            cards.add(new ProgramCard(1,0,110, new Texture(Gdx.files.internal("1 Red HULK X90/110 MOVE1 1Red 3.png"))));
            cards.add(new ProgramCard(2,0,130, new Texture(Gdx.files.internal("1 Red HULK X90/130 MOVE1 1Red 3.png"))));
            cards.add(new ProgramCard(2,0,150, new Texture(Gdx.files.internal("1 Red HULK X90/150 MOVE2 1Red 3.png"))));
            cards.add(new ProgramCard(3,0,170, new Texture(Gdx.files.internal("1 Red HULK X90/170 MOVE3 1Red 3.png"))));

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
