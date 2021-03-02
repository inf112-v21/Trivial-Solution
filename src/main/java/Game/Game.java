package Game;

import Board.Board;
import Cards.Deck;
import Cards.ICard;
import Cards.ProgramCard;
import Components.Flag;
import Player.Register;
import Player.Robot;

import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;
import java.util.Comparator;

public class Game {

    protected int numberOfPlayers;

    private final ArrayList<Flag> flagWinningFormation = new ArrayList<>();

    public static final Color[] colours = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.ORANGE, Color.WHITE, Color.BLACK};
    final ArrayList<Register> registers = new ArrayList<>();
    final ArrayList<ArrayList<ICard>> phaseRegisters = new ArrayList<>();
    final Deck deck = new Deck();
    private Board board;


    public Game(int players, String mapName){
        board = new Board(mapName);
        numberOfPlayers = players;
        flagWinningFormation.addAll(board.getWinningCombo());
        for (int i=0; i < numberOfPlayers; i++){
            String name = "Player " +i+1;
            Robot r = new Robot(name, colours[i]);
            registers.add(new Register(r));
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
            ICard card = reg.getRegisterCards().get(phasenumber);
            board.performMove(card, reg.getRobot());
        }
        board.endPhase();

        /*
        ArrayList<ICard> orderedCards = new ArrayList<>();
        ArrayList<Robot> botOrder = new ArrayList<>();
        for (Register reg : registers){
            phaseRegisters.add(reg.getMaxFiveCardsFromRegister());
        }
        for(int turnCard = 0; turnCard < 5; turnCard++){
            for (int r = 0; r < phaseRegisters.size(); r++){
                int pri = 0;
                ICard order = null;
                Robot botInUse = null;
                for(int registerInUse = 0; registerInUse < phaseRegisters.size(); registerInUse++){
                    ArrayList<ICard> shortlist = phaseRegisters.get(registerInUse);
                    if(shortlist.get(turnCard) == null){
                        break;
                    }
                    if (orderedCards.contains(shortlist.get(turnCard))){
                        break;
                    }
                    if(pri < shortlist.get(turnCard).priority()){
                        pri = shortlist.get(turnCard).priority();
                        order = shortlist.get(turnCard);
                        botInUse = bots.get(registerInUse);
                    }
                }
                orderedCards.add(order);
                botOrder.add(botInUse);
            }
        }
        for (int p = 0; p < orderedCards.size(); p++){
            board.performMove(orderedCards.get(p), botOrder.get(p));
        }

         */
        /*orderedCards.clear();
        botOrder.clear();*/
    }
    public void endRound(){
        for (int i = 0; i< registers.size(); i++){
            ArrayList<ICard> noCards = new ArrayList<>();
            phaseRegisters.clear();
            registers.get(i).setRegisterCards(noCards);
        }
    }

    /**
     * Metode som sjekker om en spiller har vunnet
     * @param rob is the bot to check.
     * @return true if a robot won, false if there is no winner yet
     */
    public boolean hasWon(Robot rob) {

        ArrayList<Flag> visitedFlags = rob.getVisitedFlags();
        if (visitedFlags.equals(flagWinningFormation)){
            return true;
        }
        return false;
    }

    public Board getBoard(){ return board; }


    private static class RegisterComparator implements Comparator<Register> {
        int phase;
        public RegisterComparator(int phase){ this.phase = phase; }

        public int compare(Register o1, Register o2) {
            return o2.getRegisterCards().get(phase).priority() - o1.getRegisterCards().get(phase).priority();
        }
    }
}
