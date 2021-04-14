package GameBoard;

import AIs.AI;
import AIs.Randbot;
import GUIMain.Screens.GameScreen;
import GameBoard.Cards.Deck;
import GameBoard.Cards.ICard;
import GameBoard.Components.Flag;
import GameBoard.Components.IComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;


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
        GameScreen.shouldLasersBeDrawn = true;

        if(currentMove == aliveRobots.size()){
            currentMove = 0;
            currentPhase++;
            board.endPhase();
        }
        if (currentPhase == PHASES_PER_ROUND){
            endRound();
            currentPhase = 0;
            waitingForPlayers = true;
            if (amITheHost) startRound(); //Deler ut kort, om vi er host eller i singleplayer.
        }
    }

    private void moveNextRobot(){
        Robot botToMove = aliveRobots.get(currentMove);
        ICard card = botToMove.getNthChosenCard(currentPhase);
        if (botToMove.hasRemainingLives() && card != null){
            board.performMove(card, botToMove);
        }
    }

    public boolean isWaitingForPlayers(){ return waitingForPlayers; }
    public void playersAreReady(){ waitingForPlayers = false; }

    private void startRound(){
        deck.shuffleDeck();
        for (Robot bot : aliveRobots){
            ArrayList<ICard> cardlist = new ArrayList<>();
            for (int amount=0; amount<bot.getAvailableCardSlots(); amount++){
                ICard card = deck.drawCard();
                cardlist.add(card);
            }
            bot.setAvailableCards(cardlist);
            if (bot.isControlledByAI()){
                ai.chooseCards(bot, board);
            }
        }
    }

    private void endRound(){
        for (Robot bot : aliveRobots) bot.resetAllCards();
        removeDeceasedRobots();
        for (Robot bot : aliveRobots) if (bot.isPowerDownAnnounced()) {
            bot.repairRobot(Robot.INITIAL_HP); //Healer roboten fullt.
            bot.togglePowerDown();
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

    public TreeSet<Position> getDirtyLocations(){ return board.getDirtyLocations(); }
    public TreeSet<Position> getLaserLocations() { return board.getLaserLocations();}

    private static class BotComparator implements Comparator<Robot> {
        final int phase;
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

    public int getNumberOfAliveRobots(){ return aliveRobots.size(); }
    public int getHeight(){ return board.getHeight(); } //bruket til konvertering av origo mellom, Board og GUI
    public Robot getRobotAt(int x, int y){ return board.getRobotAt(x, y);}
    public IComponent getLaserAt(int x, int y) { return board.getLaserAt(x,y);}
    public IComponent getForgridAt(int x, int y){ return board.getForgridAt(x, y); }
}
