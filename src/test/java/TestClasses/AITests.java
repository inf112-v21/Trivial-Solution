package TestClasses;

import AIs.AI;
import AIs.Randbot;
import GameBoard.BoardController;
import GameBoard.Cards.Deck;
import GameBoard.Cards.ICard;
import GameBoard.Cards.ProgramCard;
import GameBoard.Robot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AITests {


    private AI randbot = new Randbot();
    private Deck deck;
    private Robot robot1;

    @BeforeEach
    public void setUp(){
        deck = new Deck(false);
        robot1 = new Robot("Nebuchadnezzar", true);
    }

    @Test
    public void AIAddsCardsToRegister(){
        Robot bot = new Robot("Nebuchadnezzar", true);
        ArrayList<ICard> availableCards = new ArrayList<>();
        for (int i = 0; i < bot.getHP(); i++) {
            availableCards.add(deck.drawCard());
        }
        bot.setAvailableCards(availableCards);
        assertEquals(0, bot.getChosenCards().size());

        randbot.chooseCards(bot, null);

        assertTrue(bot.getChosenCards().size() > 0);
    }

    @Test
    public void AIDoesNotRemoveCards() {
        ArrayList<ICard> availableCards = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            availableCards.add(deck.drawCard());
        }
        robot1.setAvailableCards(availableCards);

        randbot.chooseCards(robot1, null);

        assertEquals(availableCards.size(), 9, robot1.getAvailableCards().size());
    }

    @Test
    public void AIAddCorrectAmountOfCards(){
        ArrayList<ICard> availableCards = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            availableCards.add(deck.drawCard());
        }
        robot1.setAvailableCards(availableCards);

        assertEquals(0, robot1.getChosenCards().size());
        randbot.chooseCards(robot1, null);
        assertEquals(BoardController.PHASES_PER_ROUND, robot1.getChosenCards().size());

        robot1.resetCards();
        robot1.setAvailableCards(availableCards);
        robot1.applyDamage(Robot.INITIAL_HP - 1); //Roboten har nå 1 liv igjen, burde bare få ett kort.

        randbot.chooseCards(robot1, null);
        assertEquals(1, robot1.getChosenCards().size());
    }
}
