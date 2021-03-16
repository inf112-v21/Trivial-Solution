package AIs;

import Cards.ICard;
import GameBoard.Board;
import Player.Robot;

import java.util.ArrayList;

public interface AI {
    /**
     * Gitt et Board og et Register, velg kort for det registeret.
     *
     * Alle kortene som botten kan velge mellom finnes i reg.getRegisterCards().
     * For å velge et kort, bruk reg.addCardToRegister().
     * En smart bot bruker muligens metoder fra Board til å velge smarte kort, men det er ikke et krav.
     *
     * @param bot
     * @param board
     */
    public void chooseCards(Robot bot, Board board);
}
