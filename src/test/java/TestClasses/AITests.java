package TestClasses;

import AIs.AI;
import AIs.Randbot;
import GameBoard.Cards.Deck;
import GameBoard.Cards.ICard;
import GameBoard.Robot;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AITests {

    @Test
    public void AIAddsCardsToRegister(){
        Robot bot = new Robot("Nebuchadnezzar", true);
        AI randbot = new Randbot();
        Deck deck = new Deck(false);
        ArrayList<ICard> availableCards = new ArrayList<>();
        for (int i = 0; i < bot.getHP(); i++) {
            availableCards.add(deck.drawCard());
        }
        bot.setAvailableCards(availableCards);
        assertEquals(0, bot.getChosenCards().size());

        randbot.chooseCards(bot);

        assertTrue(bot.getChosenCards().size() > 0);
    }


}
