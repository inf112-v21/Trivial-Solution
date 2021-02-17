package Register;

import Cards.Deck;
import Cards.ICard;
import Player.Register;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class registerTest {

    private static ArrayList<ICard> cards = new ArrayList<ICard>();
    private static Register r;

    @BeforeAll
    public static void setUp(){
        r = new Register();

        Deck d = new Deck();
        for(int i = 0; i < 6; i++){
            ICard card = d.drawCard();
            cards.add(card);
        }
        r.setRegisterCards(cards);

    }

    @Test
    public void registerCanSetNineCards(){
        assertEquals(cards, r.getRegisterCards());
    }

    @Test
    public void registerCanClearAllCardsBeforeEachNewGameRound(){
        //clear register-list
        r.clearAllRegisterCards();

        assertTrue(r.getRegisterCards().isEmpty());
    }
}
