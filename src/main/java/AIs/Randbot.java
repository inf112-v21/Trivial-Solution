package AIs;

import GameBoard.Board;
import Cards.ICard;
import Player.Robot;

import java.util.ArrayList;
import java.util.Random;

public class Randbot implements AI{

    /**
     * En veldig dum AI som alltid velger tilfeldige kort.
     */

    private final Random r = new Random();


    @Override
    public void chooseCards(Robot bot, Board board) {
        ArrayList<ICard> cards = bot.getAvailableCards();
        for (int i = 0; i < Math.min(bot.getHP(), 5); i++) {
            bot.addChosenCard(cards.remove(r.nextInt(cards.size())));
        }
    }
}
