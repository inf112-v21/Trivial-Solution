package TestClasses;

import GUIMain.GUI;
import GUIMain.Main;
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



public class MultiplayerGUITest{

    public static void startProgram(){
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Robo-Rally");
        cfg.setAutoIconify(true);
        cfg.setResizable(false);

        new Lwjgl3Application(new GUI(), cfg);
    }

    public static void main(String[] args) {

        Thread Host = new Thread(new host());
        Thread Client1 = new Thread(new client1());
        Thread Client2 = new Thread(new client2());

        Host.start();
        Client1.start();
        Client2.start();

    }

    static class host implements Runnable{

        @Override
        public void run() {
            startProgram();
        }
    }


    static class client1 implements Runnable{

        @Override
        public void run() {
            startProgram();
        }
    }

    static class client2 implements Runnable{

        @Override
        public void run() {
            startProgram();
        }
    }
}
