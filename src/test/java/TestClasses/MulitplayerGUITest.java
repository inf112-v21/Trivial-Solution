package TestClasses;

import GUIMain.GUI;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


/**
 * En test for å se at serveren og klientene virker sammen med gui.
 * Får å sjekke at det virker så kjører du host klassen først.
 *
 * 1. Trykk MUltiplayer knappen og deretter velg å være host.
 * 2. Kjør client1/2 og velg join i mulitplayer.
 *
 * Deretter kan man velge flere ting...
 */

class host{
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Robo-Rally");
        cfg.setAutoIconify(true);
        cfg.setResizable(false);

        new Lwjgl3Application(new GUI(), cfg);
    }
}

class client1{
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Robo-Rally");
        cfg.setAutoIconify(true);
        cfg.setResizable(false);

        new Lwjgl3Application(new GUI(), cfg);
    }
}

class client2{
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Robo-Rally");
        cfg.setAutoIconify(true);
        cfg.setResizable(false);

        new Lwjgl3Application(new GUI(), cfg);
    }
}
