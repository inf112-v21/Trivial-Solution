package GameBoard;

import GUIMain.GameScreen;
import Player.Robot;
import GUIMain.GUI;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static com.badlogic.gdx.graphics.Color.*;

class GameBoardTest {

    private static GameScreen gameScreen;

    private static GUI gui;
    private final static String defaultMapName = "TestMap.tmx";
    private static GameBoard robotRally;

    /**
     * GDX-initialiserings koden må være kjørt før "Gdx.files.internal" i game-konstruktøren skal klare lese
     * PNG-filene med bilder av kortene. Får NullPointerException hvis dette ikke blir gjort.
     */
    @BeforeAll
    public static void setUp(){
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("CLOSE THIS WINDOW TO START THE TESTS");
        cfg.setWindowedMode(500, 100);
        new Lwjgl3Application(new Game() {
            @Override
            public void create() {
                Gdx.app.exit();
            }
        }, cfg);

        ArrayList<Robot> robots = new ArrayList<Robot>();
        robots.add(new Robot("Nebuchadnezzar", BLUE, false));
        robots.add(new Robot("Andromeda", PINK, false));
        robots.add(new Robot("Ashurbarnipal", YELLOW, false));
        robotRally = new GameBoard(robots, defaultMapName);
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