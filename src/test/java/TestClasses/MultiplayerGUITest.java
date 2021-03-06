package TestClasses;

import GUIMain.GUI;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;



//Dette ble testet manuellt

/**
 * En test for å se at serveren og klientene virker sammen med gui.
 * Får å sjekke at det virker så kjører du host klassen først.
 *
 * 1. Trykk MUltiplayer knappen og deretter velg å være host.
 * 2. Kjør client1 og velg join i mulitplayer.
 *
 * Deretter kan man velge flere ting...
 */
public class MultiplayerGUITest {

    protected static void run(){
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Robo-Rally");
        cfg.setAutoIconify(true);
        cfg.setResizable(false);

        new Lwjgl3Application(new GUI(), cfg);
    }

    //Her har du 3 identiske mainmetoder, slik at vi kan kjøre programmet paralellt.
    static class Host   { public static void main(String[] args) { run(); }}
    static class Client1{ public static void main(String[] args) { run(); }}
    static class Client2{ public static void main(String[] args) { run(); }}
}







