package Board;

import Components.Flag;
import Components.IComponent;
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

public class BoardFlagPickUpTest {


    private Robot  ourRobot;
    private final static String defaultMapName = "TestMap.tmx";
    private static GUI gui;
    private static Board bård;
    private IComponent[][] forgrid;

    //Posisjonene til de ulike flaggene i griden. Kan ses i tmxfilen.
    //PosY er først og PosX er nummer2
    private int[] Flag1 = {3,3};
    private int[] Flag2 = {6,5};
    private int[] Flag3 = {2,6};

    /**
     * Denne sjiten her må kjøres før Libgdx-biblioteket klarer å lese noen tmx-filer.
     * Om vi ikke gjør dette før en test, kan vi ikke opprette en new Board uten å få en NullPointerException.
     *
     * Gjør dette for å få flaggene satt på brettet. Da blir WinningForamtion også opprettet
     */
    @BeforeAll
    public static void setUp(){
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("CLOSE THIS WINDOW TO START THE TESTS");
        cfg.setWindowedMode(500, 100);
        gui = new GUI(defaultMapName, true);
        new Lwjgl3Application(gui, cfg);
    }

    @BeforeEach
    public void resetRobotPosAndFlagsVisited(){
        bård = new Board(defaultMapName);
        forgrid = bård.getFrontGrid();
        ourRobot = new Robot("Jack&Dexter", Color.LIME);
    }

    @Test
    public void checkIfRobotCanPickUpFlag1First(){
        assertTrue(bård.robotCanPickUpFlag(ourRobot,(Flag) forgrid[Flag1[0]][Flag1[1]]));
    }

    @Test
    public void checkIfRobotCanPickUpFlag2First(){
        assertFalse(bård.robotCanPickUpFlag(ourRobot,(Flag) forgrid[Flag2[0]][Flag2[1]]));
    }

    @Test
    public void checkIfRobotCanPickUpFlag3First(){
        assertFalse(bård.robotCanPickUpFlag(ourRobot,(Flag) forgrid[Flag3[0]][Flag3[1]]));
    }


    @Test
    public void checkIfRobotCanPickUpFlag2BeforeFlag1(){
        assertFalse(bård.robotCanPickUpFlag(ourRobot,(Flag) forgrid[Flag2[0]][Flag2[1]]));
    }

    @Test
    public void checkIfRobotCanPickUpFlag2AfterFlag1(){
        ourRobot.addToFlagsVisited((Flag) forgrid[Flag1[0]][Flag1[1]]); //Henter første flagget
        assertTrue(bård.robotCanPickUpFlag(ourRobot,(Flag) forgrid[Flag2[0]][Flag2[1]]));
    }

    @Test
    public void checkIfRobotCanPickUpFlag3BeforeFlag2WhenFlag1HasBeenPickedUp(){
        ourRobot.addToFlagsVisited((Flag) forgrid[Flag1[0]][Flag1[1]]); //Henter første flagget
        assertFalse(bård.robotCanPickUpFlag(ourRobot,(Flag) forgrid[Flag3[0]][Flag3[1]]));
    }

    @Test
    public void checkIfRobotCanPickUpFlag3AfterFlag2AndFlag1(){
        ourRobot.addToFlagsVisited((Flag) forgrid[Flag1[0]][Flag1[1]]); //Henter første flagget
        ourRobot.addToFlagsVisited((Flag) forgrid[Flag2[0]][Flag2[1]]); //Henter andre flagget
        assertTrue(bård.robotCanPickUpFlag(ourRobot,(Flag) forgrid[Flag3[0]][Flag3[1]]));
    }
    
















}
