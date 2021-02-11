package GUI;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import Board.Board;

public class GUIMain {
    // kopierte denne metoden fra main-klassen

    private static String defaultMapName = "TestMap.tmx";

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle(defaultMapName.substring(0, defaultMapName.length() - 4));
        cfg.setWindowedMode(500, 500);

        new Lwjgl3Application(new HelloWorld(defaultMapName), cfg);
    }
}