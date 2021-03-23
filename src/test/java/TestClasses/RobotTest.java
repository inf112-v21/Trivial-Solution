package TestClasses;

import GameBoard.Cards.Deck;
import GameBoard.Cards.ICard;
import GameBoard.Robot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RobotTest {

    private static ArrayList<ICard> allCards;
    private static ArrayList<ICard> registerCards;
    private static Robot r;
    private static Integer numberOfLifeTokens;
    private static Integer numberOfDamageTokens;

    @BeforeEach
    public void setCards(){
        Deck d = new Deck(false);
        for(int i = 0; i < r.getHP(); i++){
            ICard card = d.drawCard();
            allCards.add(card);
        }
        r.setAvailableCards(allCards);

        numberOfLifeTokens = r.getLives();
        numberOfDamageTokens = r.getHP();
    }
    @BeforeAll
    public static void setUp(){
        allCards = new ArrayList<>();
        registerCards = new ArrayList<>();
        r = new Robot("testRobot", false);
    }

    @Test
    void registerCanSetNineCards(){
        assertEquals(allCards, r.getAvailableCards());
    }

    @Test
    void playerCanStoreTheirFiveChosenCardsInOrderInTheRegister(){
        for(int i = 0; i < 5; i++){
            r.chooseCard(allCards.get(i));
            registerCards.add(allCards.get(i));
        }
        assertEquals(registerCards, r.getChosenCards());
    }

    @Test
    void registerCanClearAllCardsBeforeEachNewGameRound(){
        //clear register-list
        r.resetCards();

        assertTrue(r.getAvailableCards().isEmpty());
    }

    @Test
    void registerHoldsCorrectAmountOfDamageTokens(){
        assertEquals(numberOfDamageTokens, r.getHP());
    }

    @Test
    void registerHoldsCorrectAmountOfLifeTokens(){
        assertEquals(numberOfLifeTokens, r.getLives());
    }

    @Test
    void canRegisterPowerDownARobot(){
        assertFalse(r.isPowerDownAnnounced());

        r.togglePowerDown();

        assertTrue(r.isPowerDownAnnounced());
    }

}
