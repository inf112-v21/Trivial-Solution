package Register;

import Cards.Deck;
import Cards.ICard;
import Components.Flag;
import Player.Register;
import Player.Robot;
import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class registerTest {

    private static ArrayList<ICard> allCards;
    private static ArrayList<ICard> registerCards;
    private static Register r;
    private static Integer numberOfLifeTokens = 10;
    private static Integer numberOfDamageTokens = 10;
    private static Boolean initializePowerDown = false;

    @BeforeAll
    public static void setUp(){
        allCards = new ArrayList<ICard>();
        r = new Register(new Robot("testRobot", Color.RED, new ArrayList<Flag>()));

        Deck d = new Deck();
        for(int i = 0; i < 6; i++){
            ICard card = d.drawCard();
            allCards.add(card);
        }
        r.setRegisterCards(allCards);
    }

    @Test
    void registerCanSetNineCards(){
        assertEquals(allCards, r.getRegisterCards());
    }

    @Test
    void registerCanClearAllCardsBeforeEachNewGameRound(){
        //clear register-list
        r.clearAllRegisterCards();

        assertTrue(r.getRegisterCards().isEmpty());
    }
    @Test
    void registerCanHoldFiveCardsInOrderFromOneToFive(){

    }

    @Test
    void playerCanStoreTheirFiveChosenCardsInOrderInTheRegister(){
        for(int i = 0; i < 5; i++){
            r.addCardsToRegister(allCards.get(i));
            registerCards.add(allCards.get(i));
        }

        assertEquals(registerCards, r.getMaxFiveCardsFromRegister());
    }

    @Test
    void registerHoldsCorrectAmountOfDamageTokens(){
        assertEquals(numberOfDamageTokens, r.getDamageTokens());
    }

    @Test
    void registerHoldsCorrectAmountOfLifeTokens(){
        assertEquals(numberOfLifeTokens, r.getLifeTokens());
    }

    @Test
    void isRegisterPoweredDown(){
        assertEquals(false, r.isPowerDownAnnounced());
    }

}
