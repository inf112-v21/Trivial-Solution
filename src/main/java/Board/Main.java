package Board;

import GUI.HelloWorld;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("hello-world");
        cfg.setWindowedMode(500, 500);

        new Lwjgl3Application(new HelloWorld(), cfg);

         //Av en eller annen grunn må koden over kjøres før Board kan lese tmx-filen sin.
         //TODO: Finn ut hvorfor, så vi slepper å kalle HelloWorld hver gang programmet skal kjøres
        Board bård = new Board();
    }
}