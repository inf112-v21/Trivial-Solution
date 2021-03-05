package Register;

import Cards.Deck;
import Cards.ICard;
import GUIMain.GameScreen;
import Player.Register;
import Player.Robot;
import com.badlogic.gdx.graphics.Color;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.lwjgl.system.linux.X11.InputOutput;

public class registerTest {

    private static ArrayList<ICard> allCards;
    private static ArrayList<ICard> registerCards;
    private static Register r;
    private static Robot robot;
    private static Integer numberOfLifeTokens;
    private static Integer numberOfDamageTokens;
    private static Boolean initializePowerDown = false;

    @BeforeEach
    public void setCards(){
        Deck d = new Deck(false);
        for(int i = 0; i < r.getDamageTokens(); i++){
            ICard card = d.drawCard();
            allCards.add(card);
        }
        r.setRegisterCards(allCards);

        numberOfLifeTokens = robot.getRemainingLives();
        numberOfDamageTokens = robot.getHP();
    }
    @BeforeAll
    public static void setUp(){
        allCards = new ArrayList<ICard>();
        registerCards = new ArrayList<ICard>();
        robot = new Robot("testRobot", Color.RED, false);
        r = new Register(robot);
    }

    @Test
    void registerCanSetNineCards(){
        assertEquals(allCards, r.getRegisterCards());
    }

    @Test
    void playerCanStoreTheirFiveChosenCardsInOrderInTheRegister(){
        for(int i = 0; i < 5; i++){
            r.addCardToRegister(allCards.get(i));
            registerCards.add(allCards.get(i));
        }
        assertEquals(registerCards, r.getMaxFiveCardsFromRegister());
    }

    @Test
    void registerCanClearAllCardsBeforeEachNewGameRound(){
        //clear register-list
        r.clearAllRegisterCards();

        assertTrue(r.getRegisterCards().isEmpty());
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
    void canRegisterPowerDownARobot(){
        r.powerDownRobot();

        assertTrue(r.isPowerDownAnnounced());
    }

}
