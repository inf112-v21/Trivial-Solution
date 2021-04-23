package AIs;

import GameBoard.Robot;

public interface AI {
    /**
     * Gitt et Board og et Register, velg kort for det registeret.
     *
     * Alle kortene som botten kan velge mellom finnes i bot.getAvailableCards().
     * For å velge et kort, bruk bot.chooseCard().
     * En smart bot bruker muligens metoder fra Board til å velge smarte kort, men det er ikke et krav.
     *  @param bot botten som skal velge kort
     */
    void chooseCards(Robot bot);
}
