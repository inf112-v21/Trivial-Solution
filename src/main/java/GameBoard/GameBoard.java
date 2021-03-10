package GameBoard;

import Cards.Deck;
import Cards.ICard;
import Components.Flag;
import Player.Register;
import Player.Robot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class GameBoard {

    protected int numberOfPlayers;

    private final ArrayList<Flag> flagWinningFormation = new ArrayList<>();

    public static final Color[] colours = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.ORANGE, Color.WHITE, Color.BLACK};
    public final ArrayList<Register> registers = new ArrayList<>();
    private final Deck deck = new Deck();
    private Board board;


    public GameBoard(ArrayList<Robot> robots, String mapName){
        board = new Board(mapName);
        numberOfPlayers = robots.size();
        flagWinningFormation.addAll(board.getWinningCombo());
        for(Robot bot : robots){
            board.spawnRobot(bot);
            registers.add(new Register(bot));
        }
    }
    public void startRound(){
        deck.shuffleDeck();
        for (Register reg : registers){
            ArrayList<ICard> cardlist = new ArrayList<>();
            for (int amount=0; amount<reg.getDamageTokens(); amount++){
                cardlist.add(deck.drawCard());
            }
            reg.setRegisterCards(cardlist);
        }
    }


    /**
     *
     * @param phasenumber Hvilken fase vi er i. OBS! Starter på 0!
     */
    public void phase(int phasenumber){
        registers.sort(new RegisterComparator(phasenumber));
        for(Register reg : registers){
            if (reg.getLifeTokens() > 0 && reg.getMaxFiveCardsFromRegister().size() > phasenumber) {

            	ICard card = reg.getMaxFiveCardsFromRegister().get(phasenumber);
                board.performMove(card, reg.getRobot());
            }
        }
        board.endPhase();
    }

    public void endRound(){
        for (Register reg : registers) reg.resetCards();
    }

    /**
     * Metode som sjekker om en spiller har vunnet eller ikke.
     * Kan brukes av GameScreen.
     *
     * @return - Roboten som vant, hvis roboten vant, null ellers
     */
    public Robot hasWon() {
        for (Register reg : registers) {
            Robot bot = reg.getRobot();
            if (bot.getVisitedFlags().equals(flagWinningFormation)) {
                return bot;
            }
        }
        return null;
    }

    public Board getBoard(){ return board; }
    public ArrayList<Register> getRegisters(){ return registers; }

   
    private static class RegisterComparator implements Comparator<Register> {
        int phase;
        public RegisterComparator(int phase){ this.phase = phase; }

        /** Obs obs! Sorterer slik at høyeste prioritet kommer først. */
        public int compare(Register o1, Register o2) {
            try{
                return o2.getRegisterCards().get(phase).priority() - o1.getRegisterCards().get(phase).priority();
            }catch (IndexOutOfBoundsException ex){
                return 0;
            }
        }
    }

    public int getHeight(){ return board.getHeight(); }
    public int getWidth(){ return board.getWidth(); }
    public Robot getRobotAt(int x, int y){ return board.getRobotAt(x, y);}
}
