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
    private Robot bot;

    @BeforeEach
    public void setUp(){
        deck = new Deck(false);
        bot = new Robot("Nebuchadnezzar", true);
    }


    /**
     * Denne testen tar imot en AI og sørger for at den er implementert på en oppegående måte som ikke ødelegger noe.
     * Når du lager en ny AI, lag også en test som kaller opp denne for den AI-en.
     */
    private void testAIProperties(AI ai){
        resetState();
        AIAddsCorrectAmountOfCards(ai);
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
        drawRandomCards();
        assertEquals(0, bot.getNumberOfChosenCards());

        ai.chooseCards(bot, null);

        assertTrue(bot.isPowerDownAnnounced() || bot.getNumberOfChosenCards() > 0);
    }

    private void AIDoesNotRemoveCards(AI ai) {
        drawRandomCards();
        int numberOfCards = bot.getAvailableCards().size();

        ai.chooseCards(bot, null);

        assertEquals(numberOfCards, bot.getAvailableCards().size());
    }

    private void AIAddsCorrectAmountOfCards(AI ai){
        drawRandomCards();

        assertEquals(0, bot.getNumberOfChosenCards());
        ai.chooseCards(bot, null);
        assertTrue(bot.getNumberOfChosenCards() <= BoardController.PHASES_PER_ROUND);

        bot.resetAllCards();
        drawRandomCards();
        bot.applyDamage(Robot.INITIAL_HP - 1); //Roboten har nå 1 liv igjen, burde bare få maks ett kort.

        ai.chooseCards(bot, null);
        assertTrue(bot.getNumberOfChosenCards() <= 1);
    }

    private void resetState(){
        bot = new Robot("Nebuchadnezzar", true);
        deck = new Deck(false);
    }

    private void drawRandomCards(){
        ArrayList<ICard> availableCards = new ArrayList<>();
        for (int i = 0; i < bot.getAvailableCardSlots(); i++) {
            availableCards.add(deck.drawCard());
        }
        bot.setAvailableCards(availableCards);
    }
}
