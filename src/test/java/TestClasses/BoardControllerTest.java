package TestClasses;

import GameBoard.BoardController;
import GameBoard.Cards.ICard;
import GameBoard.Cards.ProgramCard;
import GameBoard.Components.Flag;
import GameBoard.Robot;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

        boardController = new BoardController(robots, defaultMapName, false);
    }

    @Test
    public void hasWon() {
        //Posisjonene til de ulike flaggene i griden. Kan ses i tmxfilen.
        Flag flag1 = (Flag) boardController.getForgridAt(3,3);
        Flag flag2 = (Flag) boardController.getForgridAt(5,6);
        Flag flag3 = (Flag) boardController.getForgridAt(6,2);

        //Vi later som om robotene hentet et par flagg
        robot1.addToFlagsVisited(flag1);
        robot1.addToFlagsVisited(flag2);

        robot2.addToFlagsVisited(flag1);

        //Ingen har vunnet enda
        assertNull(boardController.hasWon());

        //bot1 besøker det siste flagget og skal dermed ha vunnet
        robot1.addToFlagsVisited(flag3);
        assertEquals(robot1, boardController.hasWon());
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
        boardController = new BoardController(robots, defaultMapName, true);

        for (int i = 0; i < Robot.INITIAL_LIVES; i++) {
            robot1.takeLife();
        }
        simulateRound();
        simulateRound(); //Denne vil kræsje om roboten ikke blir fjernet
    }

    @Test
    public void robotsAreSortedInCorrectOrderEveryPhase(){
        ArrayList<ICard> nebbie_cards = new ArrayList<>();
        nebbie_cards.add(new ProgramCard(1, 0, 100, null));
        nebbie_cards.add(new ProgramCard(1, 0, 10, null));
        nebbie_cards.add(new ProgramCard(1, 0, 1, null));
        robot1.setAvailableCards(nebbie_cards);
        for (ICard card : robot1.getAvailableCards()) robot1.chooseCard(card);

        ArrayList<ICard> andromeda_cards = new ArrayList<>();
        andromeda_cards.add(new ProgramCard(0, 1, 50, null));
        andromeda_cards.add(new ProgramCard(0, 1, 35, null));
        robot2.setAvailableCards(andromeda_cards);
        for (ICard card : robot2.getAvailableCards()) robot2.chooseCard(card);

        boardController.playersAreReady();

        boardController.simulate();
        assertEquals(robot1, boardController.getRobotAt(9, 2));
        assertEquals(Robot.INITIAL_DIRECTION, robot2.getDirection());

        boardController.simulate();
        assertEquals(Math.floorMod(Robot.INITIAL_DIRECTION + 1, Robot.TAU), robot2.getDirection());

        boardController.simulate();
        assertEquals(Math.floorMod(Robot.INITIAL_DIRECTION + 2, Robot.TAU), robot2.getDirection());

        boardController.simulate();
        assertEquals(robot1, boardController.getRobotAt(9, 1));

        boardController.simulate();
        boardController.simulate();
        assertEquals(robot1, boardController.getRobotAt(9, 0));
    }

    @Test
    public void boardWaitsForEveryoneToBeReadyBeforeStarting(){
        ArrayList<ICard> nebbie_cards = new ArrayList<>();
        nebbie_cards.add(new ProgramCard(1, 0, 1, null));
        robot1.setAvailableCards(nebbie_cards);
        robot1.chooseCard(robot1.getAvailableCards().get(0));

        for (int i = 0; i < 9001; i++) {
            boardController.simulate(); //Kan ikke simulere før alle er ferdige med å velge kort
        }

        assertNull(boardController.getRobotAt(9, 2));
        assertEquals(robot1, boardController.getRobotAt(9, 3));
        assertEquals(robot2, boardController.getRobotAt(4, 9));

        boardController.playersAreReady(); //Nå kan vi begynne
        simulateRound();

        assertEquals(robot1, boardController.getRobotAt(9, 2));
        assertNull(boardController.getRobotAt(9, 3));
        assertEquals(robot2, boardController.getRobotAt(4, 9));
    }

    @Test
    public void togglingPowerDownHealsRobotAtTheEndOfTheRound(){
        robot1.applyDamage(robot1.getHP() - 1);
        robot2.applyDamage(robot2.getHP() - 1);
        robot1.togglePowerDown(); //Kun robot1 er i powerdown

        simulateRound();

        assertEquals(Robot.INITIAL_HP, robot1.getHP());
        assertEquals(1, robot2.getHP());
    }

    private void simulateRound(){
        boardController.playersAreReady();
        for (int i = 0; i < BoardController.PHASES_PER_ROUND * boardController.getNumberOfAliveRobots(); i++) {
            boardController.simulate();
        }
    }

}