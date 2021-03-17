package GameBoard;

import Cards.Deck;
import Cards.ICard;
import Components.Flag;
import Player.Robot;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.Comparator;


public class GameBoard {

    protected int numberOfPlayers;

    private final ArrayList<Flag> flagWinningFormation = new ArrayList<>();

    public static final Color[] colours = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.ORANGE, Color.WHITE, Color.BLACK};
    private ArrayList<Robot> bots = new ArrayList<>();
    private final Deck deck = new Deck();
    private Board board;


    public GameBoard(ArrayList<Robot> robots, String mapName){
        board = new Board(mapName);
        numberOfPlayers = robots.size();
        bots = robots;
        flagWinningFormation.addAll(board.getWinningCombo());
        for(Robot bot : robots){
            board.spawnRobot(bot);
        }
    }
    public void startRound(){
        deck.shuffleDeck();
        for (Robot bot : bots){
            ArrayList<ICard> cardlist = new ArrayList<>();
            for (int amount=0; amount<bot.getHP()-1; amount++){
                ICard card =deck.drawCard();
                cardlist.add(card);
                System.out.print(card);
            }
            bot.setAvailableCards(cardlist);
        }
    }


    /**
     *
     * @param phasenumber Hvilken fase vi er i. OBS! Starter på 0!
     */
    public void phase(int phasenumber){
        bots.sort(new BotComparator(phasenumber));
        for(Robot bot : bots){
            if (bot.hasRemainingLives() && bot.getMaxFiveCards().size() > phasenumber) {

            	ICard card = bot.getMaxFiveCards().get(phasenumber);
                board.performMove(card, bot);
            }
        }
        board.endPhase();
    }

    public void endRound(){
        for (Robot bot : bots) bot.resetCards();
    }

    /**
     * Metode som sjekker om en spiller har vunnet eller ikke.
     * Kan brukes av GameScreen.
     *
     * @return - Roboten som vant, hvis roboten vant, null ellers
     */
    public Robot hasWon() {
        for (Robot bot : bots) {
            if (bot.getVisitedFlags().equals(flagWinningFormation)) {
                return bot;
            }
        }
        return null;
    }

    public Board getBoard(){ return board; }
    public ArrayList<Robot> getBots(){ return bots; }

   
    private static class BotComparator implements Comparator<Robot> {
        int phase;
        public BotComparator(int phase){ this.phase = phase; }

        /** Obs obs! Sorterer slik at høyeste prioritet kommer først. */
        public int compare(Robot o1, Robot o2) {
            try{
                return o2.getAvailableCards().get(phase).priority() - o1.getAvailableCards().get(phase).priority();
            }catch (IndexOutOfBoundsException ex){
                return 0;
            }
        }
    }

    public int getHeight(){ return board.getHeight(); }
    public int getWidth(){ return board.getWidth(); }
    public Robot getRobotAt(int x, int y){ return board.getRobotAt(x, y);}
}
