package Robot;

import Cards.Deck;
import Cards.ICard;
import Player.Robot;
import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RobotTest {

    private static ArrayList<ICard> allCards;
    private static ArrayList<ICard> registerCards;
    private static Robot r;
    private static Integer numberOfLifeTokens;
    private static Integer numberOfDamageTokens;
    private static Boolean initializePowerDown = false;

    @BeforeEach
    public void setCards(){
        Deck d = new Deck(false);
        for(int i = 0; i < r.getHP(); i++){
            ICard card = d.drawCard();
            allCards.add(card);
        }
        r.setAvailableCards(allCards);

        numberOfLifeTokens = r.getRemainingLives();
        numberOfDamageTokens = r.getHP();
    }
    @BeforeAll
    public static void setUp(){
        allCards = new ArrayList<ICard>();
        registerCards = new ArrayList<ICard>();
        r = new Robot("testRobot", false);
    }

    @Test
    void registerCanSetNineCards(){
        assertEquals(allCards, r.getAvailableCards());
    }

    @Test
    void playerCanStoreTheirFiveChosenCardsInOrderInTheRegister(){
        for(int i = 0; i < 5; i++){
            r.addChosenCard(allCards.get(i));
            registerCards.add(allCards.get(i));
        }
        assertEquals(registerCards, r.getMaxFiveCards());
    }

    @Test
    void registerCanClearAllCardsBeforeEachNewGameRound(){
        //clear register-list
        r.clearAllCards();

        assertTrue(r.getAvailableCards().isEmpty());
    }

    @Test
    void registerHoldsCorrectAmountOfDamageTokens(){
        assertEquals(numberOfDamageTokens, r.getHP());
    }

    @Test
    void registerHoldsCorrectAmountOfLifeTokens(){
        assertEquals(numberOfLifeTokens, r.getRemainingLives());
    }

    @Test
    void canRegisterPowerDownARobot(){
        r.powerDownRobot();

        assertTrue(r.isPowerDownAnnounced());
    }

}
