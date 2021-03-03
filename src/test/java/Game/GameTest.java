package Game;

import Components.Flag;
import Components.IComponent;
import Player.Robot;
import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    //private final Game robotRally = new Game(4, "TestMap.tmx");
    //TODO: Finne ut hvorfor det kommer NullpointException fra Deck klassen når man kaller Game



    @Test
    void startRound() {}

    @Test
    void phase() {}

    @Test
    void endRound() {}

    @Test
    void destroyedBot() {}

    @Test
    void hasWon() {
        /*
        //Posisjonene til de ulike flaggene i griden. Kan ses i tmxfilen.
        //PosY er først og PosX er nummer2
        int[] Flag1 = {3,3};
        int[] Flag2 = {6,5};
        int[] Flag3 = {2,6};

        //Vi henter ut par tilfeldige Boter
        Robot bot1 = robotRally.bots.get(0);
        Robot bot2 = robotRally.bots.get(1);
        Robot bot3 = robotRally.bots.get(2);

        //Vi henter forgriden med flaggen:
        IComponent[][] forgrid = robotRally.Board.getFrontGrid();



        //Vi later som om robotene hentet et par flagg
        bot1.addToFlagsVisited((Flag) forgrid[Flag1[0]][Flag1[1]]);
        bot1.addToFlagsVisited((Flag) forgrid[Flag2[0]][Flag2[1]]);
        bot1.addToFlagsVisited((Flag) forgrid[Flag3[0]][Flag3[1]]);

        bot2.addToFlagsVisited((Flag) forgrid[Flag1[0]][Flag1[1]]);

        bot3.addToFlagsVisited((Flag) forgrid[Flag1[0]][Flag1[1]]);
        bot1.addToFlagsVisited((Flag) forgrid[Flag2[0]][Flag2[1]]);


        assertEquals(bot1,robotRally.hasWon());

         */
    }

}