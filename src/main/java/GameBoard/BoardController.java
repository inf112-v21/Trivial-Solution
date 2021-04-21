package GameBoard;

import AIs.AI;
import AIs.Randbot;
import GameBoard.Cards.Deck;
import GameBoard.Cards.ProgramCard;
import GameBoard.Components.Flag;
import GameBoard.Components.IComponent;
import NetworkMultiplayer.Messages.InGameMessages.SanityCheck;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.Comparator;


public class BoardController {

    public static final int PHASES_PER_ROUND = 5;
    private final ArrayList<Flag> flagWinningFormation = new ArrayList<>();
    private final ArrayList<Robot> aliveRobots;
    private final ArrayList<Robot> recentlyDeceasedRobots = new ArrayList<>();
    private final Deck deck = new Deck();
    private final Board board;
    private final AI ai = new Randbot();
    private final boolean amITheHost;
    private int currentPhase = 0;
    private int currentMove  = 0;
    private boolean waitingForPlayers;
    private boolean phaseOver = false;

    public BoardController(List<Robot> robots, String mapName, boolean amITheHost){
        this.amITheHost = amITheHost;
        board = new Board(mapName);
        aliveRobots = new ArrayList<>(robots);
        flagWinningFormation.addAll(board.getWinningCombo());
        for(Robot bot : robots) board.spawnRobot(bot);
        if(amITheHost) startRound();
        waitingForPlayers = true;
    }

    /**
     * Simulerer spillet. Hver gang denne blir kalt, blir ett trekk gjort.
     * Denne husker på hvilket trekk fra hvilken fase som skal blir gjort,
     *  slik at denne kan vite nøyaktig hvor i runden vi er.
     */
    public void simulate(){
        if (waitingForPlayers) return;

        if (currentMove == 0) aliveRobots.sort(new BotComparator(currentPhase));
        moveNextRobot();
        currentMove++;

        if(currentMove == aliveRobots.size()){
            currentMove = 0;
            currentPhase++;
            board.endPhase();
            phaseOver = true;
        }
        if (currentPhase == PHASES_PER_ROUND){
            endRound();
            currentPhase = 0;
            waitingForPlayers = true;
            if (amITheHost) startRound(); //Deler ut kort, om vi er host eller i singleplayer.
        }

        hasWon();

    }
    public boolean isThePhaseOver(){
        if (phaseOver){
            phaseOver = false;
            return true;
        }
        return false;
    }

    private void moveNextRobot(){
        Robot botToMove = aliveRobots.get(currentMove);
        ProgramCard card = botToMove.getNthChosenCard(currentPhase);
        if (botToMove.hasRemainingLives() && card != null){
            board.performMove(card, botToMove);
        }
    }

    public boolean isWaitingForPlayers(){ return waitingForPlayers; }
    public void playersAreReady(){ waitingForPlayers = false; }

    private void startRound(){
        deck.shuffleDeck();
        for (Robot bot : aliveRobots){
            ArrayList<ProgramCard> cardlist = new ArrayList<>();
            for (int amount=0; amount<bot.getAvailableCardSlots(); amount++){
                ProgramCard card = deck.drawCard();
                cardlist.add(card);
            }
            bot.setAvailableCards(cardlist);
            if (bot.isControlledByAI()){
                ai.chooseCards(bot, board);
            }
        }
    }

    private void endRound(){
        removeDeceasedRobots();
        for (Robot bot : aliveRobots){
            bot.resetAllCards();
            if (bot.isPowerDownAnnounced()) {
                bot.repairRobot(Robot.INITIAL_HP); //Healer roboten fullt.
                bot.setPowerDown(false);
            }
        }
    }

    private void removeDeceasedRobots(){
        for (Robot bot : aliveRobots){
            if ( ! bot.hasRemainingLives()){
                recentlyDeceasedRobots.add(bot);
            }
        }
        for (Robot bot : recentlyDeceasedRobots){
            aliveRobots.remove(bot);
        }
    }

    public ArrayList<Robot> getRecentlyDeceasedRobots(){
        ArrayList<Robot> ret = new ArrayList<>(recentlyDeceasedRobots);
        recentlyDeceasedRobots.clear();
        return ret;
    }

    /**
     * Metode som sjekker om en spiller har vunnet eller ikke.
     * Kan brukes av GameScreen.
     *
     * @return - Roboten som vant, hvis roboten vant, null ellers
     */
    public Robot hasWon() {
        for (Robot bot : aliveRobots) {
            if (bot.getVisitedFlags().equals(flagWinningFormation)) {
                return bot;
            }
        }
        return null;
    }

    public SanityCheck getSanityCheck(){ return board.getSanityCheck(); }

    public TreeSet<Position> getDirtyLocations(){ return board.getDirtyLocations(); }
    public TreeMap<Position, TiledMapTileLayer.Cell> getLaserLocations() { return board.getLaserLocations();}
    public TreeSet<Position> getDamagedPositions(){return board.getRecentlyDamagedPositions(); }

    private static class BotComparator implements Comparator<Robot> {
        private final int phase;
        public BotComparator(int phase){ this.phase = phase; }

        /** Obs obs! Sorterer slik at høyeste prioritet kommer først. */
        public int compare(Robot o1, Robot o2) {
            try{
                int diff = o2.getChosenCards().get(phase).priority() - o1.getChosenCards().get(phase).priority();
                if (diff == 0) return o2.getName().compareTo(o1.getName());
                else return diff;
            }catch (IndexOutOfBoundsException ex){
                return 0;
            }
        }
    }

    public int getNumberOfAliveRobots(){ return aliveRobots.size(); }
    public int getHeight(){ return board.getHeight(); } //bruket til konvertering av origo mellom, Board og GUI
    public Robot getRobotAt(int x, int y){ return board.getRobotAt(x, y);}
    public IComponent getForgridAt(int x, int y){ return board.getForgridAt(x, y); }
}
