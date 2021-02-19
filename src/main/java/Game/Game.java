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
    Deck Deck = new Deck();

    public void Game(int players){
        numberOfPlayers = players;
        for (int i=0; i < numberOfPlayers; i++){
            String name = "Player " +i+1;
            bots.add(new Robot(name, colours[i]));
            registers.add(new Register());
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
    public void turn(){
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
