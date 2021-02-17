package Register;

import Cards.Deck;
import Cards.ICard;
import Player.Register;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class registerTest {

    private ArrayList<ICard> cards = new ArrayList<ICard>();

    @BeforeEach
    void createListOfCards(){
        Deck d = new Deck();
        for(int i = 0; i < 6; i++){
            ICard card = d.drawCard();
            cards.add(card);
        }
    }

    @Test
    void registerCanSetNineCards(){
        Register r = new Register();

        r.setRegisterCards(cards);

        assertEquals(cards.size() ,r.getRegisterCards().size());
    }

    @Test
    void registerReturnsNineCardsToPlayerWhenPrompted(){
        Register r = new Register();

        ArrayList<ICard> registerCards= r.getRegisterCards();

        assertEquals(cards, registerCards);
    }



}
