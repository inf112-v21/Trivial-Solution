package Register;

import Cards.Deck;
import Cards.ICard;
import Player.Register;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class registerTest {

    private static ArrayList<ICard> cards;
    private static Register r;
    private static Integer numberOfLifeTokens = 5;
    private static Integer numberOfDamageTokens = 7;

    @BeforeAll
    public static void setUp(){
        cards = new ArrayList<ICard>();
        r = new Register(numberOfLifeTokens, numberOfDamageTokens,false);

        Deck d = new Deck();
        for(int i = 0; i < 6; i++){
            ICard card = d.drawCard();
            cards.add(card);
        }
        r.setRegisterCards(cards);
    }

    @Test
    void registerCanSetNineCards(){
        assertEquals(cards, r.getRegisterCards());
    }

    @Test
    void registerCanClearAllCardsBeforeEachNewGameRound(){
        //clear register-list
        r.clearAllRegisterCards();

        assertTrue(r.getRegisterCards().isEmpty());
    }

    @Test
    void registerHoldsCorrectAmountOfDamageTokens(){
        assertEquals(7, r.getDamageTokens());
    }

    @Test
    void registerHoldsCorrectAmountOfLifeTokens(){
        assertEquals(5, r.getLifeTokens());
    }

    @Test
    void isRegisterPoweredDown(){
        assertEquals(false, r.isPowerDownAnnounced());
    }

}
