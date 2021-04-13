package GUIMain;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class main2 {

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Robo-Rally");
        cfg.setAutoIconify(true);
        cfg.setResizable(false);

        new Lwjgl3Application(new GUI(), cfg);
    }
}
