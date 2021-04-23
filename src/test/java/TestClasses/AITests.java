package TestClasses;

import AIs.AI;
import AIs.Randbot;
import GameBoard.Board;
import GameBoard.BoardController;
import GameBoard.Cards.Deck;
import GameBoard.Cards.ProgramCard;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.InGameMessages.SanityCheck;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AITests {

    private Deck deck;
    private Robot bot;
    private Board bård;

    @BeforeEach
    public void reset(){
        deck = new Deck();
        bot = new Robot("Nebuchadnezzar", true);
        bård = new Board("assets/maps/TestMap.tmx");
    }

    @BeforeAll
    public static void setUp(){
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        new Lwjgl3Application(new Game() {
            @Override
            public void create() {
                Gdx.app.exit();
            }
        }, cfg);
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
        resetState();
        AIDoesNotModifyTheBoard(ai);
    }

    @Test
    public void testRandbotProperties(){
        testAIProperties(new Randbot());
    }

    private void AIAddsCardsToRegisterOrTogglesPowerdDown(AI ai){
        drawRandomCards();
        assertEquals(0, bot.getNumberOfChosenCards());

        ai.chooseCards(bot);

        assertTrue(bot.isPowerDownAnnounced() || bot.getNumberOfChosenCards() > 0);
    }

    private void AIDoesNotRemoveCards(AI ai) {
        drawRandomCards();
        int numberOfCards = bot.getAvailableCards().size();

        ai.chooseCards(bot);

        assertEquals(numberOfCards, bot.getAvailableCards().size());
    }

    private void AIAddsCorrectAmountOfCards(AI ai){
        drawRandomCards();

        assertEquals(0, bot.getNumberOfChosenCards());
        ai.chooseCards(bot);
        assertTrue(bot.getNumberOfChosenCards() <= BoardController.PHASES_PER_ROUND);

        bot.resetAllCards();
        drawRandomCards();
        bot.applyDamage(Robot.INITIAL_HP - 1); //Roboten har nå 1 liv igjen, burde bare få maks ett kort.

        ai.chooseCards(bot);
        assertTrue(bot.getNumberOfChosenCards() <= 1);
    }

    private void AIDoesNotModifyTheBoard(AI ai){
        drawRandomCards();
        bård.spawnRobot(bot);
        SanityCheck before = bård.getSanityCheck();

        ai.chooseCards(bot);

        SanityCheck after = bård.getSanityCheck();
        try{
            SanityCheck.assertEqualSimulation(before, after);
        }catch (SanityCheck.UnequalSimulationException ex){
            Assertions.fail();
        }
    }

    private void resetState(){
        bot.resetState();
        deck.shuffleDeck();
    }

    private void drawRandomCards(){
        ArrayList<ProgramCard> availableCards = new ArrayList<>();
        for (int i = 0; i < bot.getAvailableCardSlots(); i++) {
            availableCards.add(deck.drawCard());
        }
        bot.setAvailableCards(availableCards);
    }
}
