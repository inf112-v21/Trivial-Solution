package TestClasses;

import GUIMain.GUI;
import GUIMain.Screens.GameLoadingScreen;
import GameBoard.Robot;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GUITests {

    @Test
        // TODO: 25.03.2021 Denne funker ikke lol, skriv ferdig en annen dag maybe?
    void startAndGoStraightToTheGame(){
        ArrayList<Robot> robots = Robot.getDefaultRobots(3);
        robots.add(new Robot("Steinar", 3, false));
        GUI gui = new GUI(new GameLoadingScreen(robots, "TestMap.tmx", null));

        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Robo-Rally-Testing");
        cfg.setWindowedMode(1000, 1000);
        new Lwjgl3Application(gui, cfg);
    }
}
