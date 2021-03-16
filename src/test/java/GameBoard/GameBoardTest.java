package GameBoard;

import Components.Flag;
import GUIMain.Screens.GameScreen;
import Player.Robot;
import GUIMain.GUI;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.Color.*;

class GameBoardTest {

    private static GameScreen gameScreen;

    private static GUI gui;
    private final static String defaultMapName = "maps/TestMap.tmx";
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
                Gdx.app.exit(); //Sørger for at vinduet lukkes umiddelbart
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
    public void hasWon() {
        //Posisjonene til de ulike flaggene i griden. Kan ses i tmxfilen.
        //PosY er først og PosX er nummer2
        Flag flag1 = robotRally.getBoard().getFlagInForgridAt(3,3);
        Flag flag2 = robotRally.getBoard().getFlagInForgridAt(6,5);
        Flag flag3 = robotRally.getBoard().getFlagInForgridAt(2,6);

        //Vi henter ut et par tilfeldige Boter (Posisjonene under er start posisjonene i til robotene i tmx-filen)
        Robot bot1 = robotRally.getRobotAt(9,3);
        Robot bot2 = robotRally.getRobotAt(4,9);
        Robot bot3 = robotRally.getRobotAt(0,4);


        //Vi later som om robotene hentet et par flagg
        bot1.addToFlagsVisited(flag1);
        bot1.addToFlagsVisited(flag2);

        bot2.addToFlagsVisited(flag1);

        bot3.addToFlagsVisited(flag1);

        //Ingen har vunnet enda
        assertNull(robotRally.hasWon());

        //bot1 besøker det siste flagget og skal dermed ha vunnet
        bot1.addToFlagsVisited(flag3);
        assertEquals(bot1,robotRally.hasWon());
    }

}