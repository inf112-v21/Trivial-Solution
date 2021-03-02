package AIs;

import Board.Board;
import Player.Register;

public interface AI {
    /**
     * Et interface for alle robot-AI-er. Gitt et Register og et Game, skal botten selv velge hvilke kort fra det registeret som skal brukes.
     */


    /**
     * Gitt et Board og et Register, velg kort for det registeret.
     *
     * Alle kortene som botten kan velge mellom finnes i reg.getRegisterCards().
     * For å velge et kort, bruk reg.addCardToRegister().
     * En smart bot bruker muligens metoder fra Board til å velge smarte kort, men det er ikke et krav.
     *
     * @param reg
     * @param board
     */
    public void chooseCards(Register reg, Board board);
}
