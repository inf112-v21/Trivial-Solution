package Board;

import Cards.ProgramCard;
import GUIMain.GUI;
import Player.Robot;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class BoardTests {

    private static Board bård;
    private static String defaultMapName = "TestMap.tmx";
    private static Robot robot1 = new Robot("Nebuchadnezzar", Color.BLUE);
    private static Robot robot2 = new Robot("Alexstrasza", Color.RED);
    private static GUI gui;

    /**
     * Denne sjiten her må kjøres før Libgdx-biblioteket klarer å lese noen tmx-filer.
     * Om vi ikke gjør dette før en test, kan vi ikke opprette en new Board uten å få en NullPointerException.
     *
     */
    @BeforeAll
    public static void setUp(){
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("CLOSE THIS WINDOW TO START THE TESTS");
        cfg.setWindowedMode(500, 500);
        gui = new GUI(defaultMapName);
        new Lwjgl3Application(gui, cfg);
    }

    /**
     * Resetter alle posisjonene på brettet, slik at alle testene har det samme utgangspunktet.
     */
    @BeforeEach
    public void resetBoard(){
        bård = new Board(defaultMapName);
    }

    @Test
    public void readFromFileReadsWidthAndHeight(){
        assertNotNull(bård.getHeight());
        assertNotNull(bård.getWidth());
    }

    @Test
    public void canPlaceRobot(){
        assertNull(bård.getRobotAt(0, 0));

        bård.placeRobotAt(0, 0, robot1);

        assertNotNull(bård.getRobotAt(0, 0));
    }

    @Test
    public void canTurnTheRobotAroundWithRotationCard(){
        bård.placeRobotAt(0, 0, robot1);
        int direction = robot1.getDirection();
        bård.performMove(new ProgramCard(0, 1, 1), robot1);

        assertNotEquals(direction, robot1.getDirection());
    }

    @Test
    public void canMoveTheRobotWithMoveCard(){
        bård.placeRobotAt(0, 0, robot1);
        robot1.setDirection(1);
        bård.performMove(new ProgramCard(1, 0, 1), robot1);

        assertNull(bård.getRobotAt(0, 0));
        assertEquals(robot1, bård.getRobotAt(1, 0));
    }

    @Test
    public void cannotMoveIntoWallTileIfItFacesThatDirection(){
        bård.placeRobotAt(1, 3, robot1);
        robot1.setDirection(1);

        assertEquals(robot1, bård.getRobotAt(1, 3));
        assertNull(bård.getRobotAt(2, 3));

        //Her prøver roboten å gå inn i en vegg. Da skal den ikke flyttes.
        bård.performMove(new ProgramCard(1, 0, 1), robot1);

        assertEquals(robot1, bård.getRobotAt(1, 3));
        assertNull(bård.getRobotAt(2, 3));
    }

    @Test
    public void canMoveRobotMultipleTilesIfUnobstructed(){
        bård.placeRobotAt(0, 0, robot1);
        robot1.setDirection(1);

        bård.performMove(new ProgramCard(3, 0, 1), robot1);

        assertNull(bård.getRobotAt(0, 0));
        assertNull(bård.getRobotAt(1, 0));
        assertNull(bård.getRobotAt(2, 0));
        assertEquals(robot1, bård.getRobotAt(3, 0));
    }

    @Test
    public void botMovesOnlyPartlyIfObstructedMidway(){
        bård.placeRobotAt(4, 3, robot1);
        robot1.setDirection(3);

        //Prøver å gå 3 skritt, skal bli stoppet av en vegg etter 2
        bård.performMove(new ProgramCard(3, 0, 1), robot1);

        assertNull(bård.getRobotAt(4, 3));
        assertNull(bård.getRobotAt(3, 3));
        assertEquals(robot1, bård.getRobotAt(2, 3));
        assertNull(bård.getRobotAt(1, 3));
    }

    @Test
    public void cannotMoveFromWallTileIfItFacesThatDirection(){
        bård.placeRobotAt(2, 3, robot1);
        robot1.setDirection(3);

        assertEquals(robot1, bård.getRobotAt(2, 3));
        assertNull(bård.getRobotAt(1, 3));

        bård.performMove(new ProgramCard(1, 0, 1), robot1);

        assertEquals(robot1, bård.getRobotAt(2, 3));
        assertNull(bård.getRobotAt(1, 3));
    }

    @Test
    public void movingIntoAnotherBotPushesBoth(){
        bård.placeRobotAt(0, 0, robot1);
        bård.placeRobotAt(1, 0, robot2);
        robot1.setDirection(1);

        bård.performMove(new ProgramCard(1, 0, 1), robot1);

        assertNull(bård.getRobotAt(0, 0));
        assertEquals(robot1, bård.getRobotAt(1, 0));
        assertEquals(robot2, bård.getRobotAt(2, 0));
    }

    @Test
    public void canPushOtherBotTwoSteps(){
        bård.placeRobotAt(0, 0, robot1);
        bård.placeRobotAt(1, 0, robot2);
        robot1.setDirection(1);

        bård.performMove(new ProgramCard(2, 0, 1), robot1);

        assertNull(bård.getRobotAt(0, 0));
        assertNull(bård.getRobotAt(1, 0));
        assertEquals(robot1, bård.getRobotAt(2, 0));
        assertEquals(robot2, bård.getRobotAt(3, 0));
    }

    @Test
    public void pushingBotIntoWallStopsBoth(){
        bård.placeRobotAt(0, 3, robot1);
        bård.placeRobotAt(1, 3, robot2);
        robot1.setDirection(1);

        //Her treffer de veggen umiddelbart.
        bård.performMove(new ProgramCard(3, 0, 1), robot1);

        assertEquals(robot1, bård.getRobotAt(0, 3));
        assertEquals(robot2, bård.getRobotAt(1, 3));
        assertNull(bård.getRobotAt(2, 3));
    }

    @Test
    public void pushingBotTowardsWallStopsBothButOnlyAfterTheyReachSaidWall(){
        bård.placeRobotAt(3, 3, robot2);
        bård.placeRobotAt(4, 3, robot1);
        robot1.setDirection(3);

        //Har kan de gå ett skritt før de treffer veggen.
        bård.performMove(new ProgramCard(3, 0, 1), robot1);

        assertNull(bård.getRobotAt(4, 3));
        assertEquals(robot1, bård.getRobotAt(3, 3));
        assertEquals(robot2, bård.getRobotAt(2, 3));
    }

    @Test
    public void firingLaserAtRobotDealsDamage(){
        int startingDamage = robot1.getHP();
        bård.placeRobotAt(7, 4, robot1);

        bård.endPhase();

        assertNotEquals(startingDamage, robot1.getHP());
    }

    @Test
    public void doubleLaserYieldsDoubleDamage(){
        int startingHP = robot1.getHP();
        bård.placeRobotAt(7, 6, robot1);

        bård.endPhase();

        assertEquals(startingHP-2, robot1.getHP());
    }

    @Test
    public void laserStopsWhenItHitsAWall(){
        int startingDamage = robot1.getHP();
        bård.placeRobotAt(5, 4, robot1);

        bård.endPhase();

        assertEquals(startingDamage, robot1.getHP());
    }

    @Test
    public void tryingToMoveNonExistentRobotYieldsError(){
        try{
            bård.performMove(new ProgramCard(1, 0, 10), robot1);
            fail();
        } catch (IllegalStateException ex){
            //Yay it worked
        }
    }

    /** Tester at mappet faktisk blir resatt mellom hver test.
     * Mange av testene plasserer en bot på (0, 0), så dette sjekker at den er null igjen. */
    @Test
    public void beforeEachActuallyResetsTheBoard(){
        assertNull(bård.getRobotAt(0, 0));
    }

    @Test
    public void setUpActuallyCreatesTheBoardEveryTime(){ assertNotNull(bård); }

}
