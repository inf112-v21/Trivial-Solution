package AIs;

import GameBoard.Board;
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
    public void chooseCards(Robot bot, Board board) {
        ArrayList<ICard> cards = new ArrayList<>(bot.getAvailableCards()); //lager en kopi av listen
        for (int i = 0; i < bot.getChosenCardSlots(); i++) {
            bot.chooseCard(cards.remove(r.nextInt(cards.size())));
        }
    }
}
