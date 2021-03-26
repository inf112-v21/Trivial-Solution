package AIs;

import GameBoard.Cards.ICard;
import GameBoard.Robot;

import java.util.ArrayList;
import java.util.Random;

public class Randbot implements AI{

    /**
     * En veldig dum AI som alltid velger tilfeldige kort.
     */

    private final Random r = new Random();


    @Override
    public void chooseCards(Robot bot) {
        ArrayList<ICard> cards = bot.getAvailableCards();
        for (int i = 0; i < Math.min(bot.getHP(), 5); i++) {
            bot.chooseCard(cards.get(r.nextInt(cards.size())));
        }
    }
}
