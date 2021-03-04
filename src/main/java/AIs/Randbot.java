package AIs;

import Board.Board;
import Cards.ICard;
import Player.Register;

import java.util.ArrayList;
import java.util.Random;

public class Randbot implements AI{

    private Random r = new Random();


    @Override
    public void chooseCards(Register reg, Board board) {
        ArrayList<ICard> cards = reg.getRegisterCards();
        for (int i = 0; i < Math.min(reg.getRobot().getHP(), 5); i++) {
            reg.addCardToRegister(cards.remove(r.nextInt(cards.size())));
        }
    }
}
