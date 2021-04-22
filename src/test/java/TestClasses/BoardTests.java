package TestClasses;

import GameBoard.Cards.ProgramCard;
import GameBoard.Components.Flag;
import GameBoard.Board;
import GameBoard.Position;
import GameBoard.Robot;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class BoardTests {

    private static Board bård;
    private final static String defaultMapName = "maps/TestMap.tmx";
    private static Robot robot1;
    private static Robot robot2;
    private static Robot robot3;
    private static Robot robot4;
    private static Robot robot5;


    //Disse brukes for å teste om Flagene blir hentet på riktig måte
    private Flag Flag1;
    private Flag Flag2;
    private Flag Flag3;


    /**
     * Denne sjiten her må kjøres før Libgdx-biblioteket klarer å lese noen tmx-filer.
     * Om vi ikke gjør dette før en test, kan vi ikke opprette en new Board uten å få en NullPointerException.
     *
     */
    @BeforeAll
    public static void setUp(){
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        new Lwjgl3Application(new Game() {
            @Override
            public void create() {
                Gdx.app.exit();
            }
        }, cfg);
    }

    /**
     * Resetter alle posisjonene på brettet, slik at alle testene har det samme utgangspunktet.
     */
    @BeforeEach
    public void resetState(){
        bård = new Board(defaultMapName);
        robot1 = new Robot("Nebuchadnezzar", false);
        robot2 = new Robot("Alexstrasza", false);
        robot3 = new Robot("Gilgamesh", false);
        robot4 = new Robot("Ashurbarnipal", false);
        robot5 = new Robot("Andromeda", false);

        //Flaggene med de tilhørende posisjonene i griden. posY og PosX Kan ses i tmxfilen.
        Flag1 = (Flag) bård.getForgridAt(3,3);
        Flag2 = (Flag) bård.getForgridAt( 5,6);
        Flag3 = (Flag) bård.getForgridAt(6,2);
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

        bård.performMove(new ProgramCard(0, 1, 30), robot1);

        assertNotEquals(direction, robot1.getDirection());
    }

    @Test
    public void canMoveTheRobotWithMoveCard(){
        bård.placeRobotAt(0, 0, robot1);
        robot1.setDirection(1);

        bård.performMove(new ProgramCard(1, 0, 90), robot1);

        assertNull(bård.getRobotAt(0, 0));
        assertEquals(robot1, bård.getRobotAt(1, 0));
    }

    @Test
    public void canMoveRobotBackwards(){
        bård.spawnRobot(robot1);
        robot1.setDirection(0);

        bård.performMove(new ProgramCard(-1, 0, 1), robot1);

        assertNull(bård.getRobotAt(9, 3));
        assertEquals(robot1, bård.getRobotAt(9, 4));
    }

    @Test
    public void cannotMoveIntoWallTileIfItFacesThatDirection(){
        bård.placeRobotAt(1, 3, robot1);
        robot1.setDirection(1);

        assertEquals(robot1, bård.getRobotAt(1, 3));
        assertNull(bård.getRobotAt(2, 3));

        //Her prøver roboten å gå inn i en vegg. Da skal den ikke flyttes.
        bård.performMove(new ProgramCard(1, 0, 90), robot1);

        assertEquals(robot1, bård.getRobotAt(1, 3));
        assertNull(bård.getRobotAt(2, 3));
    }

    @Test
    public void canMoveRobotMultipleTilesIfUnobstructed(){
        bård.placeRobotAt(0, 0, robot1);
        robot1.setDirection(1);

        bård.performMove(new ProgramCard(3, 0, 170), robot1);

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
        bård.performMove(new ProgramCard(3, 0, 170), robot1);

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

        bård.performMove(new ProgramCard(1, 0, 90), robot1);

        assertEquals(robot1, bård.getRobotAt(2, 3));
        assertNull(bård.getRobotAt(1, 3));
    }

    @Test
    public void movingIntoAnotherBotPushesBoth(){
        bård.placeRobotAt(0, 0, robot1);
        bård.placeRobotAt(1, 0, robot2);
        robot1.setDirection(1);

        bård.performMove(new ProgramCard(1, 0, 90), robot1);

        assertNull(bård.getRobotAt(0, 0));
        assertEquals(robot1, bård.getRobotAt(1, 0));
        assertEquals(robot2, bård.getRobotAt(2, 0));
    }

    @Test
    public void canPushOtherBotTwoSteps(){
        bård.placeRobotAt(0, 0, robot1);
        bård.placeRobotAt(1, 0, robot2);
        robot1.setDirection(1);

        bård.performMove(new ProgramCard(2, 0, 130), robot1);

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
        bård.performMove(new ProgramCard(3, 0, 170), robot1);

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
        bård.performMove(new ProgramCard(3, 0, 170), robot1);

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
    public void laserFiresInCorrectDirection(){
        int startingHP = robot1.getHP();
        bård.placeRobotAt(5, 5, robot1);

        bård.endPhase();

        assertNotEquals(startingHP, robot1.getHP());
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
        bård.placeRobotAt(5, 0, robot1);
        bård.placeRobotAt(3, 0, robot2);
        robot1.setDirection(3);
        robot2.setDirection(1);
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

        bård.performMove(new ProgramCard(1, 0, 90), robot1);

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
            fail();
        } catch (IllegalStateException ex){
            //Yay it worked
        }
    }

    @Test
    public void drivingOffTheMapRemovesOneOfTheRobotsLivesAndReducesMaxHP(){
        bård.spawnRobot(robot1);
        robot1.setDirection(1);

        bård.performMove(new ProgramCard(1, 0, 90), robot1);
        bård.endPhase();

        assertTrue(robot1.getLives() < Robot.INITIAL_LIVES);
        assertTrue(!robot1.hasRemainingLives() || Robot.INITIAL_HP - Robot.RESPAWN_HANDICAP == robot1.getHP());
    }

    @Test
    public void drivingIntoAHoleActsAsJumpingOffTheMap(){
        bård.spawnRobot(robot1);
        robot1.setDirection(3);

        bård.performMove(new ProgramCard(3, 0, 170), robot1);
        bård.endPhase();

        assertNull(bård.getRobotAt(6, 3));
        assertNull(bård.getRobotAt(7, 3));
        assertNull(bård.getRobotAt(8, 3));
        assertTrue( !robot1.hasRemainingLives() || robot1 == bård.getRobotAt(9, 3));
        assertTrue( !robot1.hasRemainingLives() || robot1.getHP() < Robot.INITIAL_HP);
    }

    @Test
    public void whenDestroyedTheRobotRespawnsAtTheCheckPoint(){
        bård.spawnRobot(robot1);
        robot1.setDirection(2);

        //Kjører vekk fra checkpointet
        bård.performMove(new ProgramCard(1, 0, 90), robot1);
        robot1.setDirection(1);

        //Kjører av brettet
        bård.performMove(new ProgramCard(1, 0, 90), robot1);
        bård.endPhase();

        assertNull(bård.getRobotAt(9, 4));
        assertTrue( !robot1.hasRemainingLives() || robot1.equals(bård.getRobotAt(9, 3)));
    }

    @Test
    public void endingPhaseOnCheckPointSetsNewSpawnPoint(){
        bård.spawnRobot(robot1);
        robot1.setDirection(2);
        bård.performMove(new ProgramCard(2, 0, 140), robot1);
        robot1.setDirection(3);

        //Her står botten oppå respawnpointet
        bård.performMove(new ProgramCard(2, 0, 140), robot1);

        bård.endPhase();

        //Hopper nedi hullet
        bård.performMove(new ProgramCard(1, 0, 90), robot1);

        bård.endPhase();

        assertNull(bård.getRobotAt(9, 3));
        assertTrue( !robot1.hasRemainingLives() || robot1.equals(bård.getRobotAt(7, 5)));
    }

    @Test
    public void cannotRespawnIfCheckPointIsOccupied(){
        bård.spawnRobot(robot1);
        robot1.setDirection(3);
        bård.performMove(new ProgramCard(2, 0, 140), robot1);
        bård.placeRobotAt(9, 3, robot2);

        bård.performMove(new ProgramCard(1, 0, 90), robot1);
        bård.endPhase();

        //Nå er checkpointet okkupert
        assertEquals(robot2, bård.getRobotAt(9, 3));

        bård.performMove(new ProgramCard(1, 0, 90), robot2);
        bård.endPhase();

        //Nå er checkpointet ledig igjen
        assertTrue( !robot1.hasRemainingLives() || robot1.equals(bård.getRobotAt(9, 3)));
    }

    @Test
    public void tryingToMoveNonExistentRobotYieldsError(){
        try{
            bård.performMove(new ProgramCard(1, 0, 90), robot1);
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

    @Test
    public void checkIfRobotCanPickUpFlag1First(){
        assertTrue(bård.robotCanPickUpFlag(robot1,Flag1));
    }

    @Test
    public void checkIfRobotCanPickUpFlag2BeforeFlag1() {
        assertFalse(bård.robotCanPickUpFlag(robot1, Flag2));
    }

    @Test
    public void checkIfRobotCanPickUpFlag3First(){
        assertFalse(bård.robotCanPickUpFlag(robot1,Flag3));
    }


    @Test
    public void checkIfRobotCanPickUpFlag2AfterFlag1(){
        robot1.addToFlagsVisited(Flag1); //Henter første flagget
        assertTrue(bård.robotCanPickUpFlag(robot1,Flag2));
    }

    @Test
    public void checkIfRobotCanPickUpFlag3BeforeFlag2WhenFlag1HasBeenPickedUp(){
        robot1.addToFlagsVisited(Flag1); //Første flagg er hentet
        assertFalse(bård.robotCanPickUpFlag(robot1,Flag3));
    }

    @Test
    public void checkIfRobotCanPickUpFlag3AfterFlag2AndFlag1(){
        robot1.addToFlagsVisited(Flag1); //Henter første flagget
        robot1.addToFlagsVisited(Flag2); //Henter andre flagget
        assertTrue(bård.robotCanPickUpFlag(robot1,Flag3));
    }

    @Test
    public void conveyorBeltsPushesBotsInCorrectDirection(){
        bård.placeRobotAt(1, 1, robot1);
        bård.placeRobotAt(1, 5, robot2);

        bård.endPhase();

        assertEquals(robot1, bård.getRobotAt(2, 1));
        assertEquals(robot2, bård.getRobotAt(1, 4));
    }

    @Test
    public void blueConveyorBeltsPushTwoSteps(){
        bård.placeRobotAt(8, 5, robot1);

        bård.endPhase();

        assertEquals(robot1, bård.getRobotAt(8, 7));
    }

    @Test
    public void conveyorBeltPushingRobotIntoHoleKillsSaidRobot(){
        bård.spawnRobot(robot1); //Setter spawnpunkt, slik at den har et sted å respawne når den dør
        bård.placeRobotAt(7, 9, robot1);

        bård.endPhase();

        assertNull(bård.getRobotAt(8, 9));
        assertNull(bård.getRobotAt(7, 9));
        assertTrue(robot1.getLives() < Robot.INITIAL_LIVES);
    }

    @Test
    public void twoRobotsNextToEachOtherOnTheSameConveyorBeltLine(){
        bård.placeRobotAt(1, 6, robot1);
        bård.placeRobotAt(1, 5, robot2);

        bård.endPhase();

        assertEquals(robot2, bård.getRobotAt(1, 4));
        assertEquals(robot1, bård.getRobotAt(1, 5));
    }

    @Test
    public void sameAsLastTestButOrderIsFlipped(){
        bård.placeRobotAt(1, 5, robot1);
        bård.placeRobotAt(1, 6, robot2);

        bård.endPhase();

        assertEquals(robot1, bård.getRobotAt(1, 4));
        assertEquals(robot2, bård.getRobotAt(1, 5));
    }

    @Test
    public void threeRobotsOnTheSameConveyorBeltLine(){
        bård.placeRobotAt(1, 6, robot1);
        bård.placeRobotAt(1, 5, robot2);
        bård.placeRobotAt(1, 4, robot3);

        bård.endPhase();

        assertNull(bård.getRobotAt(1, 6));
        assertEquals(robot1, bård.getRobotAt(1, 5));
        assertEquals(robot2, bård.getRobotAt(1, 4));
        assertEquals(robot3, bård.getRobotAt(1, 3));
    }

    @Test
    public void robotOnConveyorBeltPushingRobotInFrontOfTheConveyorBelt(){
        bård.placeRobotAt(2, 9, robot1);
        bård.placeRobotAt(1, 9, robot2);

        bård.endPhase();

        assertEquals(robot1, bård.getRobotAt(1, 9));
        assertEquals(robot2, bård.getRobotAt(0, 9));
    }

    @Test
    public void conveyorBeltPushingRobotOffTheMap(){
        bård.spawnRobot(robot1); //Setter spawnpunktet
        bård.placeRobotAt(0, 8, robot1);

        bård.endPhase();

        assertNull(bård.getRobotAt(0, 8));
        assertEquals(robot1, bård.getRobotAt(9, 3));
        assertTrue(robot1.getLives() < Robot.INITIAL_LIVES);
    }

    @Test
    public void robotOnConveyorBeltPushingARobotThatPushesAnotherRobotOffTheMap(){
        bård.spawnRobot(robot2); //Bare for å sette spawnpunktet
        bård.placeRobotAt(9, 9, robot2);
        bård.placeRobotAt(9, 8, robot1);

        bård.endPhase();

        assertEquals(robot1, bård.getRobotAt(9, 9));
        assertTrue(robot2.getLives() < Robot.INITIAL_LIVES);
    }

    @Test
    public void conveyorBeltPushingBotCannotPushAnotherBotThroughAWall(){
        bård.placeRobotAt(0, 7, robot1);
        bård.placeRobotAt(0, 6, robot2);

        bård.endPhase();

        assertEquals(robot1, bård.getRobotAt(0, 7));
        assertEquals(robot2, bård.getRobotAt(0, 6));
        assertNull(bård.getRobotAt(0, 5));
    }

    @Test
    public void conveyorBeltPushingDeadRobotDoesNotCrashTheGame(){
        bård.spawnRobot(robot1);
        bård.placeRobotAt(4, 8, robot1);

        for (int i = 0; i < Robot.INITIAL_LIVES; i++) {
            robot1.takeLife();
        }
        robot1.applyDamage(robot1.getHP());
        bård.endPhase(); //Her blir roboten dyttet, og deretter fjernet av brettet (som burde gå fint)
        bård.endPhase(); //Her prøver samlebåndet og dyttet roboten, selv om den er død og fjernet, som gir kræsj

        //Yay it worked
    }

    @Test
    public void movingRobotOneStepAddsTheNewLocationAndTheOldOneToTheSetOfDirtyLocations(){
        bård.placeRobotAt(0, 0, robot1);
        robot1.setDirection(1);
        bård.getDirtyLocations();

        bård.performMove(new ProgramCard(1, 0, 1), robot1);

        TreeSet<Position> dirtyLocations = bård.getDirtyLocations();
        assertTrue(dirtyLocations.contains(new Position(0, 0)));
        assertTrue(dirtyLocations.contains(new Position(1, 0)));
    }

    @Test
    public void drivingIntoHoleAndDyingMarksCorrectLocationsAsDirty(){
        bård.spawnRobot(robot1);
        robot1.setDirection(3);
        bård.performMove(new ProgramCard(2, 0, 1), robot1);
        bård.getDirtyLocations(); //Resetter settet

        bård.performMove(new ProgramCard(1, 0, 1), robot1);

        assertTrue(bård.getDirtyLocations().contains(new Position(7, 3)));

        bård.endPhase();

        assertTrue( !robot1.hasRemainingLives() || bård.getDirtyLocations().contains(new Position(9, 3)));
    }

    @Test
    public void setOfDirtyLocationsGetsResetEachTime(){
        bård.placeRobotAt(0, 0, robot1);
        robot1.setDirection(1);

        bård.performMove(new ProgramCard(1, 0, 1), robot1);

        assertFalse(bård.getDirtyLocations().isEmpty()); //Her bør settet resettes
        assertTrue(bård.getDirtyLocations().isEmpty());
    }

    @Test
    public void robotPushingAnotherRobotMarksCorrectLocationsAsDirty(){
        bård.placeRobotAt(4, 4, robot1);
        bård.placeRobotAt(4, 3, robot2);
        bård.getDirtyLocations();

        bård.performMove(new ProgramCard(3, 0, 1), robot1);
        TreeSet<Position> dirtyLocations = bård.getDirtyLocations();

        assertTrue(dirtyLocations.contains(new Position(4, 0)));
        assertTrue(dirtyLocations.contains(new Position(4, 1)));
        assertTrue(dirtyLocations.contains(new Position(4, 2)));
        assertTrue(dirtyLocations.contains(new Position(4, 3)));
        assertTrue(dirtyLocations.contains(new Position(4, 4)));
        assertFalse(dirtyLocations.contains(new Position(4, 5))); //Denne er nedenfor og blir ikke påvirket
    }

    @Test
    public void conveyorBeltPushingRobotAddsDirtyLocations(){
        bård.placeRobotAt(8, 4, robot1);

        bård.endPhase();
        TreeSet<Position> dirtyLocations = bård.getDirtyLocations();

        assertTrue(dirtyLocations.contains(new Position(8, 4)));
        assertTrue(dirtyLocations.contains(new Position(8, 5)));
        assertTrue(dirtyLocations.contains(new Position(8, 6)));
    }

    @Test
    public void rotatingARobotMarksLocationAsDirty(){
        bård.placeRobotAt(0, 0, robot1);
        bård.getDirtyLocations();

        bård.performMove(new ProgramCard(0, 1, 1), robot1);

        assertTrue(bård.getDirtyLocations().contains(new Position(0, 0)));
    }

    @Test
    public void robotTakingMortalDamageMarksLocationAsDirty(){
        bård.spawnRobot(robot1);
        robot1.setDirection(0);
        bård.performMove(new ProgramCard(2, 0, 1), robot1);
        bård.getDirtyLocations(); //Resetter settet

        robot1.applyDamage(9001);
        bård.endPhase();

        TreeSet<Position> dirtyLocations = bård.getDirtyLocations();
        assertTrue(!robot1.hasRemainingLives() || dirtyLocations.contains(new Position(9, 3)));
        assertTrue(dirtyLocations.contains(new Position(9, 1)));
    }

    @Test
    public void gearRotatesTheRobotsTheCorrectDirection(){
        bård.placeRobotAt(8,0, robot1);

        bård.endPhase();

        assertEquals(1, bård.getRobotAt(8,0).getDirection() );
    }

    @Test
    public void rotatingRobotWithGearMarksLocationDirty(){
        bård.placeRobotAt(8,0,robot1);
        bård.getDirtyLocations();

        bård.endPhase();

        assertTrue(bård.getDirtyLocations().contains(new Position(8, 0)));
    }

    @Test
    public void wrenchGivesRobotOneExtraHP(){
        bård.placeRobotAt(2,0, robot1);

        robot1.applyDamage(1);
        bård.endPhase();

        assertEquals(Robot.INITIAL_HP, robot1.getHP());
    }

    @Test
    public void whenARobotStepsOnAWrenchTheLocationIsMarkedDirty(){
        bård.placeRobotAt(2,0,robot1);
        bård.getDirtyLocations();

        bård.endPhase();

        assertTrue(bård.getDirtyLocations().contains(new Position(2, 0)));
    }

    @Test
    public void robotsGettingHitByLaserAddsThemToTheSet(){
        bård.placeRobotAt(6, 4, robot1); //I skuddlinjen
        bård.placeRobotAt(5, 4, robot2); //Bak en vegg
        bård.placeRobotAt(9, 5, robot3); //Langt unna, men fortsatt i skuddlinjen
        bård.placeRobotAt(3, 9, robot4); //Langt vekke, utenfor skuddlinjen

        bård.endPhase();
        TreeSet<Position> locs = bård.getRecentlyDamagedPositions();

        assertTrue(locs.contains(new Position(6, 4)));
        assertFalse(locs.contains(new Position(5, 4)));
        assertTrue(locs.contains(new Position(9, 5)));
        assertFalse(locs.contains(new Position(3, 9)));
    }

    @Test
    public void damagedLocationSetGetResetEverytime(){
        bård.placeRobotAt(6, 4, robot1);

        bård.endPhase();

        assertTrue(bård.getRecentlyDamagedPositions().contains(new Position(6, 4)));
        assertFalse(bård.getRecentlyDamagedPositions().contains(new Position(6, 4)));
    }
}
