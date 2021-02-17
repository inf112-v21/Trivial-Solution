package Game;

import Cards.Deck;
import Components.Flag;
import Player.Robot;

import java.awt.*;
import java.util.ArrayList;

public class Game {
    protected int players;
    Color[] Colours = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.ORANGE, Color.WHITE, Color.BLACK};
    ArrayList<Robot> Bots = new ArrayList<Robot>();
    Deck Deck = new Deck();
    public void Game(int player){
        players = player;
        for (int i=0; i < players; i++){
            //Bots.add(new Robot());
        }

    }
    private void round(){
        Deck.shuffleDeck();

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
