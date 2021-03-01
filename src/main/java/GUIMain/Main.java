package GUIMain;

import Cards.ICard;
import Cards.ProgramCard;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;

public class Main {

    private static String defaultMapName = "TestMap.tmx";

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle(defaultMapName.substring(0, defaultMapName.length() - 4));
        cfg.setWindowedMode(1000, 1000);
        new Lwjgl3Application(new GUI(defaultMapName), cfg);
    }
}
