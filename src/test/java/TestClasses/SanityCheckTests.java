package TestClasses;

import GameBoard.Board;
import GameBoard.Cards.ProgramCard;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.InGameMessages.SanityCheck;
import NetworkMultiplayer.Messages.InGameMessages.SanityCheck.UnequalSimulationException;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.fail;

public class SanityCheckTests {

    private final static String defaultMapName = "maps/TestMap.tmx";
    private static Board bård;
    private static Robot bot1;
    private static Robot bot2;

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

    @BeforeEach
    public void reset(){
        bård = new Board(defaultMapName);
        bot1 = new Robot("Nebuchadnezzar", false);
        bot2 = new Robot("Alexstrasza", false);
    }

    @Test
    public void reflexiveSanityCheck(){
        SanityCheck check1 = bård.getSanityCheck();
        SanityCheck check2 = bård.getSanityCheck();

        try{
            check1.assertEqualSimulation(check2);
            check2.assertEqualSimulation(check1);
        }catch (UnequalSimulationException ex){
            fail();
        }
    }

    @Test
    public void twoEqualBoardsShouldYieldEqualSanityCheck(){
        Board bård1 = new Board(defaultMapName);
        Board bård2 = new Board(defaultMapName);
        try{
            bård1.getSanityCheck().assertEqualSimulation(bård2.getSanityCheck()); //Crashes if inequal
        }catch (UnequalSimulationException ex){
            fail();
        }
    }

    @Test
    public void damagingARobotShouldYieldUnequalSanityCheck(){
        bård.spawnRobot(bot1);
        bård.spawnRobot(bot2);

        SanityCheck check1 = bård.getSanityCheck();
        bot1.applyDamage(1);
        SanityCheck check2 = bård.getSanityCheck();

        try {
            check1.assertEqualSimulation(check2);
            fail();
        }catch (UnequalSimulationException ex){
            //Yay
        }
    }

    @Test
    public void movingRobotShouldYieldUnequalSanityCheck(){
        bård.spawnRobot(bot1);

        SanityCheck check1 = bård.getSanityCheck();
        bård.performMove(new ProgramCard(1, 0, 1), bot1);
        SanityCheck check2 = bård.getSanityCheck();

        try{
            check1.assertEqualSimulation(check2);
            fail();
        }catch (UnequalSimulationException e){
            //It worked whoooooo
        }
    }

    @Test
    public void oneBoardHasMoreRobots(){
        Board bård1 = new Board(defaultMapName);
        Board bård2 = new Board(defaultMapName);

        bård1.spawnRobot(bot1);
        bård2.spawnRobot(bot1);
        bård2.spawnRobot(bot2);

        SanityCheck check1 = bård1.getSanityCheck();
        SanityCheck check2 = bård2.getSanityCheck();

        try{
            check1.assertEqualSimulation(check2);
            check2.assertEqualSimulation(check1);
            fail();
        }catch (UnequalSimulationException e){
            //Yippe
        }
    }


    @Test
    public void makingTheSameMoveOnTwoEqualBoardsYieldsEqualSanityCheck(){
        Board bård1 = new Board(defaultMapName);
        bård1.spawnRobot(bot1);
        Board bård2 = new Board(defaultMapName);
        bård2.spawnRobot(bot1);
        bård1.getSanityCheck().assertEqualSimulation(bård2.getSanityCheck());

        bård1.performMove(new ProgramCard(1, 0, 1), bot1);
        bård2.performMove(new ProgramCard(1, 0, 1), bot1);

        try{
            bård1.getSanityCheck().assertEqualSimulation(bård2.getSanityCheck());
        }catch (UnequalSimulationException ex){
            fail();
        }
    }
}
