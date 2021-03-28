package TestClasses;

import AIs.AI;
import AIs.Randbot;
import GameBoard.BoardController;
import GameBoard.Cards.Deck;
import GameBoard.Cards.ICard;
import GameBoard.Robot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AITests {

    private Deck deck;
    private Robot robot1;

    @BeforeEach
    public void setUp(){
        deck = new Deck(false);
        robot1 = new Robot("Nebuchadnezzar", true);
    }


    /**
     * Denne testen tar imot en AI og sørger for at den er implementert på en oppegående måte som ikke ødelegger noe.
     * Når du lager en ny AI, lag også en test som kaller opp denne for den AI-en.
     */
    private void testAIProperties(AI ai){
        resetState();
        AIAddCorrectAmountOfCards(ai);
        resetState();
        AIAddsCardsToRegisterOrTogglesPowerdDown(ai);
        resetState();
        AIDoesNotRemoveCards(ai);
    }

    @Test
    public void testRandbotProperties(){
        testAIProperties(new Randbot());
    }


    private void AIAddsCardsToRegisterOrTogglesPowerdDown(AI ai){
        Robot bot = new Robot("Nebuchadnezzar", true);
        ArrayList<ICard> availableCards = new ArrayList<>();
        for (int i = 0; i < bot.getAvailableCardSlots(); i++) {
            availableCards.add(deck.drawCard());
        }
        bot.setAvailableCards(availableCards);
        assertEquals(0, bot.getChosenCards().size());

        ai.chooseCards(bot, null);

        assertTrue(bot.isPowerDownAnnounced() || bot.getChosenCards().size() > 0);
    }

    private void AIDoesNotRemoveCards(AI ai) {
        ArrayList<ICard> availableCards = new ArrayList<>();
        for (int i = 0; i < robot1.getAvailableCardSlots(); i++) {
            availableCards.add(deck.drawCard());
        }
        robot1.setAvailableCards(availableCards);

        ai.chooseCards(robot1, null);

        assertEquals(availableCards.size(), 9, robot1.getAvailableCards().size());
    }

    private void AIAddCorrectAmountOfCards(AI ai){
        ArrayList<ICard> availableCards = new ArrayList<>();
        for (int i = 0; i < robot1.getAvailableCardSlots(); i++) {
            availableCards.add(deck.drawCard());
        }
        robot1.setAvailableCards(availableCards);

        assertEquals(0, robot1.getChosenCards().size());
        ai.chooseCards(robot1, null);
        assertEquals(BoardController.PHASES_PER_ROUND, robot1.getChosenCards().size());

        robot1.resetCards();
        robot1.setAvailableCards(availableCards);
        robot1.applyDamage(Robot.INITIAL_HP - 1); //Roboten har nå 1 liv igjen, burde bare få ett kort.

        ai.chooseCards(robot1, null);
        assertEquals(1, robot1.getChosenCards().size());
    }

    private void resetState(){
        robot1 = new Robot("Nebuchadnezzar", true);
        deck = new Deck(false);
    }
}
