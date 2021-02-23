package Game;

import Cards.Deck;
import Cards.ICard;
import Components.Flag;
import Player.Register;
import Player.Robot;

import com.badlogic.gdx.graphics.Color;
import java.util.ArrayList;

public class Game {

    protected int numberOfPlayers;
    Color[] colours = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.ORANGE, Color.WHITE, Color.BLACK};
    ArrayList<Robot> bots = new ArrayList<>();
    ArrayList<Register> registers = new ArrayList<>();
    ArrayList<ICard> tempRegister = new ArrayList<>();
    ArrayList<ArrayList<ICard>> phaseRegisters = new ArrayList<>();
    Deck Deck = new Deck();

    public void Game(int players, String mapname){
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
        for (int i=0; i<numberOfPlayers; i++){
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
        //getRegisterCards is placeholder for 5 cars the players want to play
        for (int i = 0; i< registers.size(); i++){
            phaseRegisters.add(registers.get(i).getRegisterCards());
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
                        shortlist.remove(turnCard);
                    }
                    else{
                        //temp
                        break;
                    }
                }
                orderedCards.add(order);
                botOrder.add(botInUse);
            }
        }
        for (int p = 0; p < orderedCards.size(); p++){
            //board.performove(orderedCards.get(p), botOrder.get(p))
        }

    }
    public void endRound(){
    }
    public void destroyedBot(Robot bot){
        registers.remove(bots.indexOf(bot));
        bots.remove(bot);
    }

    /**
     * Metode som sjekker om en spiller har vunnet
     * @param rob
     * @return true if a robot won, false if there is no winner yet
     */
    public boolean hasWon(Robot rob) {
        ArrayList<Flag> visitedFlags = rob.getVisitedFlags();


        return true;
    }


}
