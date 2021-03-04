import AIs.AI;
import AIs.Randbot;
import Cards.Deck;
import Player.Register;
import Player.Robot;
import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AITests {

    @Test
    public void AIAddsCardsToRegister(){
        Register reg = new Register(new Robot("Nebuchadnezzar", Color.BLUE, true));
        AI randbot = new Randbot();
        Deck deck = new Deck(false);
        ArrayList availableCards = new ArrayList();
        for (int i = 0; i < reg.getRobot().getHP(); i++) {
            availableCards.add(deck.drawCard());
        }
        reg.setRegisterCards(availableCards);
        assertTrue(reg.getMaxFiveCardsFromRegister().size() == 0);

        randbot.chooseCards(reg, null);

        assertTrue(reg.getMaxFiveCardsFromRegister().size() > 0);
    }


}
