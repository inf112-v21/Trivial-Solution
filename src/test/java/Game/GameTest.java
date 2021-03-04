package Game;

import Components.Flag;
import Components.IComponent;
import GUIMain.GUI;
import GUIMain.GUI2;
import Player.Robot;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private static GUI gui;
    private final static String defaultMapName = "TestMap.tmx";
    private static Game robotRally;

    /**
     * GDX-initialiserings koden må være kjørt før "Gdx.files.internal" i game-konstruktøren skal klare lese
     * PNG-filene med bilder av kortene. Får NullPointerException hvis dette ikke blir gjort.
     */
    @BeforeAll
    public static void setUp(){
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("CLOSE THIS WINDOW TO START THE TESTS");
        cfg.setWindowedMode(500, 100);
        gui = new GUI(defaultMapName, true);
        new Lwjgl3Application(gui, cfg);
        robotRally = new Game(4, defaultMapName);
    }

    @Test
    void startRound() {
    }

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