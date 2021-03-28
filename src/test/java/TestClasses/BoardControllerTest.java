package TestClasses;

import GameBoard.BoardController;
import GameBoard.Components.Flag;
import GameBoard.Robot;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class BoardControllerTest {

    private final String defaultMapName = "maps/TestMap.tmx";
    private BoardController boardController;
    private Robot robot1;
    private Robot robot2;

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
    }

    @BeforeEach
    public void reset(){
        robot1 = new Robot("Nebuchadnezzar", false);
        robot2 = new Robot("Andromeda", false);
        ArrayList<Robot> robots = new ArrayList<>();
        robots.add(robot1);
        robots.add(robot2);

        boardController = new BoardController(robots, defaultMapName);
    }

    @Test
    public void hasWon() {
        //Posisjonene til de ulike flaggene i griden. Kan ses i tmxfilen.
        //PosY er først og PosX er nummer2
        Flag flag1 = boardController.getBoard().getFlagInForgridAt(3,3);
        Flag flag2 = boardController.getBoard().getFlagInForgridAt(6,5);
        Flag flag3 = boardController.getBoard().getFlagInForgridAt(2,6);

        //Vi henter ut et par tilfeldige Boter (Posisjonene under er start posisjonene i til robotene i tmx-filen)
        Robot bot1 = boardController.getRobotAt(9,3);
        Robot bot2 = boardController.getRobotAt(4,9);
        Robot bot3 = boardController.getRobotAt(0,4);


        //Vi later som om robotene hentet et par flagg
        bot1.addToFlagsVisited(flag1);
        bot1.addToFlagsVisited(flag2);

        bot2.addToFlagsVisited(flag1);

        bot3.addToFlagsVisited(flag1);

        //Ingen har vunnet enda
        assertNull(boardController.hasWon());

        //bot1 besøker det siste flagget og skal dermed ha vunnet
        bot1.addToFlagsVisited(flag3);
        assertEquals(bot1, boardController.hasWon());
    }

    @Test
    public void robotDyingForTheThirdTimeAddsRobotToListOfDeceasedRobots(){
        assertFalse(boardController.getRecentlyDeceasedRobots().contains(robot1));

        for (int i = 0; i < Robot.INITIAL_LIVES; i++) {
            robot1.takeLife();
        }

        simulateRound();

        assertTrue(boardController.getRecentlyDeceasedRobots().contains(robot1));
    }

    @Test
    public void robotDyingRemovesTheRobotWithoutCrashingTheGame(){
        robot1 = new Robot("Nebuchadnezzar", true);  //Setter opp alt på nytt, bare med at robotene er styrt av AI-en istedet.
        robot2 = new Robot("Andromeda", true);
        ArrayList<Robot> robots = new ArrayList<>();
        robots.add(robot1);
        robots.add(robot2);
        boardController = new BoardController(robots, defaultMapName);

        for (int i = 0; i < Robot.INITIAL_LIVES; i++) {
            robot1.takeLife();
        }
        simulateRound();
        simulateRound(); //Denne vil kræsje om roboten ikke blir fjernet
    }

    private void simulateRound(){
        boardController.playersAreReady();
        for (int i = 0; i < BoardController.PHASES_PER_ROUND * boardController.getNumberOfAliveRobots(); i++) {
            boardController.simulate();
        }
    }

}