package TestClasses;

import GameBoard.BoardController;
import GameBoard.Components.Flag;
import GameBoard.Robot;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class BoardControllerTest {

    private final static String defaultMapName = "maps/TestMap.tmx";
    private static BoardController robotRally;

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

        ArrayList<Robot> robots = new ArrayList<>();
        robots.add(new Robot("Nebuchadnezzar", false));
        robots.add(new Robot("Andromeda", false));
        robots.add(new Robot("Ashurbarnipal",false));
        robotRally = new BoardController(robots, defaultMapName);
    }

    @Test
    public void hasWon() {
        //Posisjonene til de ulike flaggene i griden. Kan ses i tmxfilen.
        //PosY er først og PosX er nummer2
        Flag flag1 = robotRally.getBoard().getFlagInGridAt(3,3);
        Flag flag2 = robotRally.getBoard().getFlagInGridAt(6,5);
        Flag flag3 = robotRally.getBoard().getFlagInGridAt(2,6);

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