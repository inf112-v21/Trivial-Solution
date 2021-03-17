import AIs.AI;
import AIs.Randbot;
import Cards.Deck;
import Player.Robot;
import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AITests {

    @Test
    public void AIAddsCardsToRegister(){
        Robot bot = new Robot("Nebuchadnezzar", true);
        AI randbot = new Randbot();
        Deck deck = new Deck(false);
        ArrayList availableCards = new ArrayList();
        for (int i = 0; i < bot.getHP(); i++) {
            availableCards.add(deck.drawCard());
        }
        bot.setAvailableCards(availableCards);
        assertTrue(bot.getMaxFiveCards().size() == 0);

        randbot.chooseCards(bot, null);

        assertTrue(bot.getMaxFiveCards().size() > 0);
    }


}
