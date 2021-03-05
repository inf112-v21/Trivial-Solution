package AIs;

import GameBoard.Board;
import Cards.ICard;
import Player.Register;

import java.util.ArrayList;
import java.util.Random;

public class Randbot implements AI{

    /**
     * En veldig dum AI som alltid velger tilfeldige kort.
     */

    private Random r = new Random();


    @Override
    public void chooseCards(Register reg, Board board) {
        ArrayList<ICard> cards = reg.getRegisterCards();
        for (int i = 0; i < Math.min(reg.getRobot().getHP(), 5); i++) {
            reg.addCardToRegister(cards.remove(r.nextInt(cards.size())));
        }
    }
}
