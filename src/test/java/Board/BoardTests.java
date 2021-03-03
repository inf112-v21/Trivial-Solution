package Board;

import Cards.ProgramCard;
import Components.Flag;
import Components.IComponent;
import GUIMain.GUI;
import Player.Robot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class BoardTests {

    private static Board bård;
    private final static String defaultMapName = "TestMap.tmx";
    private static Robot robot1;
    private static Robot robot2;
    private static Robot robot3;
    private static Robot robot4;
    private static Robot robot5;
    private static GUI gui;

    //Posisjonene til de ulike flaggene i griden. Kan ses i tmxfilen.
    //Disse brukes for å teste om Flagene blir hentet på riktig måte
    private final int[] Flag1 = {3,3};
    private final int[] Flag2 = {6,5};
    private final int[] Flag3 = {2,6};
    private IComponent[][] forgrid;

    /**
     * Denne sjiten her må kjøres før Libgdx-biblioteket klarer å lese noen tmx-filer.
     * Om vi ikke gjør dette før en test, kan vi ikke opprette en new Board uten å få en NullPointerException.
     *
     */
    @BeforeAll
    public static void setUp(){
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("CLOSE THIS WINDOW TO START THE TESTS");
        cfg.setWindowedMode(500, 100);
        gui = new GUI(defaultMapName, true);
        new Lwjgl3Application(gui, cfg);
    }

    /**
     * Resetter alle posisjonene på brettet, slik at alle testene har det samme utgangspunktet.
     */
    @BeforeEach
    public void resetState(){
        bård = new Board(defaultMapName);
        robot1 = new Robot("Nebuchadnezzar", Color.BLUE, false);
        robot2 = new Robot("Alexstrasza", Color.RED, false);
        robot3 = new Robot("Gilgamesh", Color.YELLOW, false);
        robot4 = new Robot("Ashurbarnipal", Color.GREEN, false);
        robot5 = new Robot("Andromeda", Color.PINK, false);
        //forgrid = bård.getFrontGrid();
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

        bård.performMove(new ProgramCard(0, 1, 30, new Texture(Gdx.files.internal("1 Red HULK X90/030 ROTATE RIGHT 1Red 3.png"))), robot1);

        assertNotEquals(direction, robot1.getDirection());
    }

    @Test
    public void canMoveTheRobotWithMoveCard(){
        bård.placeRobotAt(0, 0, robot1);
        robot1.setDirection(1);

        bård.performMove(new ProgramCard(1, 0, 90, new Texture(Gdx.files.internal("1 Red HULK X90/090 MOVE1 1Red 3.png"))), robot1);

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
        bård.performMove(new ProgramCard(1, 0, 90, new Texture(Gdx.files.internal("1 Red HULK X90/090 MOVE1 1Red 3.png"))), robot1);

        assertEquals(robot1, bård.getRobotAt(1, 3));
        assertNull(bård.getRobotAt(2, 3));
    }

    @Test
    public void canMoveRobotMultipleTilesIfUnobstructed(){
        bård.placeRobotAt(0, 0, robot1);
        robot1.setDirection(1);

        bård.performMove(new ProgramCard(3, 0, 170, new Texture(Gdx.files.internal("1 Red HULK X90/170 MOVE3 1Red 3.png"))), robot1);

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
        bård.performMove(new ProgramCard(3, 0, 170, new Texture(Gdx.files.internal("1 Red HULK X90/170 MOVE3 1Red 3.png"))), robot1);

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

        bård.performMove(new ProgramCard(1, 0, 90, new Texture(Gdx.files.internal("1 Red HULK X90/090 MOVE1 1Red 3.png"))), robot1);

        assertEquals(robot1, bård.getRobotAt(2, 3));
        assertNull(bård.getRobotAt(1, 3));
    }

    @Test
    public void movingIntoAnotherBotPushesBoth(){
        bård.placeRobotAt(0, 0, robot1);
        bård.placeRobotAt(1, 0, robot2);
        robot1.setDirection(1);

        bård.performMove(new ProgramCard(1, 0, 90, new Texture(Gdx.files.internal("1 Red HULK X90/090 MOVE1 1Red 3.png"))), robot1);

        assertNull(bård.getRobotAt(0, 0));
        assertEquals(robot1, bård.getRobotAt(1, 0));
        assertEquals(robot2, bård.getRobotAt(2, 0));
    }

    @Test
    public void canPushOtherBotTwoSteps(){
        bård.placeRobotAt(0, 0, robot1);
        bård.placeRobotAt(1, 0, robot2);
        robot1.setDirection(1);

        bård.performMove(new ProgramCard(2, 0, 130, new Texture(Gdx.files.internal("1 Red HULK X90/130 MOVE1 1Red 3.png"))), robot1);

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
        bård.performMove(new ProgramCard(3, 0, 170, new Texture(Gdx.files.internal("1 Red HULK X90/170 MOVE3 1Red 3.png"))), robot1);

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
        bård.performMove(new ProgramCard(3, 0, 170, new Texture(Gdx.files.internal("1 Red HULK X90/170 MOVE3 1Red 3.png"))), robot1);

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
    public void robotsFireLasersAtEachOther(){
        bård.placeRobotAt(0, 0, robot1);
        bård.placeRobotAt(2, 0, robot2);
        robot1.setDirection(1);
        robot2.setDirection(3);
        int hp1 = robot1.getHP();
        int hp2 = robot1.getHP();

        bård.endPhase();

        assertNotEquals(hp1, robot1.getHP());
        assertNotEquals(hp2, robot2.getHP());
    }

    @Test
    public void botDoesNotShootItselfLikeADumbass(){
        bård.placeRobotAt(0, 0, robot1);
        int hp = robot1.getHP();

        bård.endPhase();

        assertEquals(hp, robot1.getHP());
    }

    @Test
    public void laserStopsWhenItHitsARobot(){
        bård.placeRobotAt(0, 0, robot1);
        bård.placeRobotAt(0, 2, robot2);
        bård.placeRobotAt(0, 3, robot3);
        robot1.setDirection(1);
        robot2.setDirection(0);
        robot3.setDirection(0);
        int hp2 = robot2.getHP();
        int hp3 = robot3.getHP();

        bård.endPhase();

        assertNotEquals(hp2, robot2.getHP());
        assertEquals(hp3, robot3.getHP());
    }

    @Test
    public void cannotGoTroughTheWallTheLaserIsMountedOn(){
        bård.placeRobotAt(7, 4, robot1);
        robot1.setDirection(1);

        bård.performMove(new ProgramCard(1, 0, 90, new Texture(Gdx.files.internal("1 Red HULK X90/090 MOVE1 1Red 3.png"))), robot1);

        assertEquals(robot1, bård.getRobotAt(7, 4));
        assertNull(bård.getRobotAt(8, 4));
    }

    @Test
    public void robotsSpawnInCorrectPlacesAndInCorrectOrder(){
        assertNull(bård.getRobotAt(9, 3));
        assertNull(bård.getRobotAt(4, 9));
        assertNull(bård.getRobotAt(0, 4));
        assertNull(bård.getRobotAt(5, 0));

        bård.spawnRobot(robot1);
        assertEquals(robot1, bård.getRobotAt(9, 3));

        bård.spawnRobot(robot2);
        assertEquals(robot2, bård.getRobotAt(4, 9));

        bård.spawnRobot(robot3);
        assertEquals(robot3, bård.getRobotAt(0, 4));

        bård.spawnRobot(robot4);
        assertEquals(robot4, bård.getRobotAt(5, 0));
    }

    @Test
    public void spawningMoreRobotsThanTheMapAllowsYieldsError(){
        bård.spawnRobot(robot1);
        bård.spawnRobot(robot2);
        bård.spawnRobot(robot3);
        bård.spawnRobot(robot4);

        try{
            bård.spawnRobot(robot5);
        } catch (IllegalStateException ex){
            //Yay it worked
        }
    }

    @Test
    public void drivingOffTheMapRemovesOneOfTheRobotsLivesAndReducesMaxHP(){
        bård.spawnRobot(robot1);
        robot1.setDirection(1);

        bård.performMove(new ProgramCard(1, 0, 90, new Texture(Gdx.files.internal("1 Red HULK X90/090 MOVE1 1Red 3.png"))), robot1);
        bård.endPhase();

        assertTrue(robot1.getRemainingLives() < Robot.INITIAL_LIVES);
        assertTrue(robot1.getHP() < Robot.INITIAL_HP);
    }

    @Test
    public void drivingIntoAHoleActsAsJumpingOffTheMap(){
        bård.spawnRobot(robot1);
        robot1.setDirection(3);

        bård.performMove(new ProgramCard(3, 0, 170, new Texture(Gdx.files.internal("1 Red HULK X90/170 MOVE3 1Red 3.png"))), robot1);
        bård.endPhase();

        assertNull(bård.getRobotAt(6, 3));
        assertNull(bård.getRobotAt(7, 3));
        assertNull(bård.getRobotAt(8, 3));
        assertEquals(robot1, bård.getRobotAt(9, 3));
        assertTrue(robot1.getHP() < Robot.INITIAL_HP);
    }

    @Test
    public void whenDestroyedTheRobotRespawnsAtTheCheckPoint(){
        bård.spawnRobot(robot1);
        robot1.setDirection(2);

        //Kjører vekk fra checkpointet
        bård.performMove(new ProgramCard(1, 0, 90, new Texture(Gdx.files.internal("1 Red HULK X90/090 MOVE1 1Red 3.png"))), robot1);
        robot1.setDirection(1);

        //Kjører av brettet
        bård.performMove(new ProgramCard(1, 0, 90, new Texture(Gdx.files.internal("1 Red HULK X90/090 MOVE1 1Red 3.png"))), robot1);
        bård.endPhase();

        assertNull(bård.getRobotAt(9, 4));
        assertEquals(robot1, bård.getRobotAt(9, 3));
    }

    @Test
    public void endingPhaseOnCheckPointSetNewSpawnPoint(){
        bård.spawnRobot(robot1);
        robot1.setDirection(2);
        bård.performMove(new ProgramCard(2, 0, 140, new Texture(Gdx.files.internal("1 Red HULK X90/140 MOVE2 1Red 3.png"))), robot1);
        robot1.setDirection(3);

        //Her står botten oppå respawnpointet
        bård.performMove(new ProgramCard(2, 0, 140, new Texture(Gdx.files.internal("1 Red HULK X90/140 MOVE2 1Red 3.png"))), robot1);

        bård.endPhase();

        //Hopper nedi hullet
        bård.performMove(new ProgramCard(1, 0, 90, new Texture(Gdx.files.internal("1 Red HULK X90/090 MOVE1 1Red 3.png"))), robot1);

        bård.endPhase();

        assertNull(bård.getRobotAt(9, 3));
        assertEquals(robot1, bård.getRobotAt(7, 5));
    }

    @Test
    public void cannotRespawnIfCheckPointIsOccupied(){
        bård.spawnRobot(robot1);
        robot1.setDirection(3);
        bård.performMove(new ProgramCard(2, 0, 140, new Texture(Gdx.files.internal("1 Red HULK X90/140 MOVE2 1Red 3.png"))), robot1);
        bård.placeRobotAt(9, 3, robot2);

        bård.performMove(new ProgramCard(1, 0, 90, new Texture(Gdx.files.internal("1 Red HULK X90/090 MOVE1 1Red 3.png")) ), robot1);
        bård.endPhase();

        //Nå er checkpointet okkupert
        assertEquals(robot2, bård.getRobotAt(9, 3));

        bård.performMove(new ProgramCard(1, 0, 90, new Texture(Gdx.files.internal("1 Red HULK X90/090 MOVE1 1Red 3.png"))), robot2);
        bård.endPhase();

        //Nå er checkpointet ledig igjen
        assertEquals(robot1, bård.getRobotAt(9, 3));
    }

    @Test
    public void tryingToMoveNonExistentRobotYieldsError(){
        try{
            bård.performMove(new ProgramCard(1, 0, 90, new Texture(Gdx.files.internal("1 Red HULK X90/090 MOVE1 1Red 3.png"))), robot1);
            fail();
        } catch (IllegalArgumentException ex){
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


    /**
     * De neste testene fra linje 438-473 sjekker om robotene henter flaggene i riktig
     * rekkefølge. Det er brettet som git Robotene lov å hente flaggene.
     */
    @Test
    public void checkIfRobotCanPickUpFlag1First(){
        assertTrue(bård.robotCanPickUpFlag(robot1,(Flag) forgrid[Flag1[0]][Flag1[1]]));
    }

    @Test
    public void checkIfRobotCanPickUpFlag2BeforeFlag1() {
        assertFalse(bård.robotCanPickUpFlag(robot1, (Flag) forgrid[Flag2[0]][Flag2[1]]));
    }

    @Test
    public void checkIfRobotCanPickUpFlag3First(){
        assertFalse(bård.robotCanPickUpFlag(robot1,(Flag) forgrid[Flag3[0]][Flag3[1]]));
    }


    @Test
    public void checkIfRobotCanPickUpFlag2AfterFlag1(){
        robot1.addToFlagsVisited((Flag) forgrid[Flag1[0]][Flag1[1]]); //Henter første flagget
        assertTrue(bård.robotCanPickUpFlag(robot1,(Flag) forgrid[Flag2[0]][Flag2[1]]));
    }

    @Test
    public void checkIfRobotCanPickUpFlag3BeforeFlag2WhenFlag1HasBeenPickedUp(){
        robot1.addToFlagsVisited((Flag) forgrid[Flag1[0]][Flag1[1]]); //Første flagg er hentet
        assertFalse(bård.robotCanPickUpFlag(robot1,(Flag) forgrid[Flag3[0]][Flag3[1]]));
    }

    @Test
    public void checkIfRobotCanPickUpFlag3AfterFlag2AndFlag1(){
        robot1.addToFlagsVisited((Flag) forgrid[Flag1[0]][Flag1[1]]); //Henter første flagget
        robot1.addToFlagsVisited((Flag) forgrid[Flag2[0]][Flag2[1]]); //Henter andre flagget
        assertTrue(bård.robotCanPickUpFlag(robot1,(Flag) forgrid[Flag3[0]][Flag3[1]]));
    }

}
