package Game;

import Board.Board;
import Cards.Deck;
import Cards.ICard;
import Player.Register;
import Player.Robot;

import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;

public class Game {

    protected int numberOfPlayers;
    final Color[] colours = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.ORANGE, Color.WHITE, Color.BLACK};
    final ArrayList<Robot> bots = new ArrayList<>();
    final ArrayList<Register> registers = new ArrayList<>();
    final ArrayList<ICard> tempRegister = new ArrayList<>();
    final ArrayList<ArrayList<ICard>> phaseRegisters = new ArrayList<>();
    final Deck Deck = new Deck();
    public Board Board;

    public void Game(int players, String mapName){
        Board = new Board(mapName);
        numberOfPlayers = players;
        for (int i=0; i < numberOfPlayers; i++){
            String name = "Player " +i+1;
            Robot r = new Robot(name, colours[i]);
            bots.add(r);
            registers.add(new Register(r));
        }
    }
    public void startRound(){
        Deck.shuffleDeck();
        for (int i=0; i<registers.size(); i++){
            for (int amount=0; amount<bots.get(i).getRemainingLives(); amount++){
                tempRegister.add(Deck.drawCard());
            }
            registers.get(i).setRegisterCards(tempRegister);
            tempRegister.clear();
        }
    }

    public void phase(){
        ArrayList<ICard> orderedCards = new ArrayList<>();
        ArrayList<Robot> botOrder = new ArrayList<>();
        for (int i = 0; i< registers.size(); i++){
            phaseRegisters.add(registers.get(i).getMaxFiveCardsFromRegister());
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
            Board.performMove(orderedCards.get(p), botOrder.get(p));
        }
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
    public void destroyedBot(Robot bot){
        registers.remove(bots.indexOf(bot));
        bots.remove(bot);
    }

    /**
     * Metode som sjekker om en spiller har vunnet
     * @param rob is the bot to check.
     * @return true if a robot won, false if there is no winner yet
     */
    public boolean hasWon(Robot rob) {
        return true;
    }


}
