package Game;

import Components.Flag;
import Player.Robot;
import com.badlogic.gdx.graphics.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game robotRally;

    @BeforeEach
    void setUp(){
        robotRally = new Game(4, "TestMap.tmx");
    }

    @Test
    void startRound() {
    }

    @Test
    void phase() {
    }

    @Test
    void endRound() {
    }

    @Test
    void destroyedBot() {
    }

    @Test
    void hasWon() {
        //Må finne ut hvordan denne skal testes
        Robot rob = new Robot("Nebuchadnezzar", Color.BLUE, false);

    }
}