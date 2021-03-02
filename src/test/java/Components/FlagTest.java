package Components;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Her tester vi om Flag-comparatoren vil sortere flaggene i riktig rekkefølge
 * Det er aldri mer enn 4 flag på brette på et hvert tidspunkt.
 * Dermed sjekker vi ikke flere enn 4 flag.
 */


class FlagTest {

    private final ArrayList<Flag> winCondition = new ArrayList<>();
    private ArrayList<Flag> ourFlagArray = new ArrayList<>();

    @BeforeEach
    void setUp(){
        Flag f1 = new Flag(63);
        Flag f2 = new Flag(71);
        Flag f3 = new Flag(55);
        Flag f4 = new Flag(79);

        //Riktig rekkeflge
        winCondition.add(f3);
        winCondition.add(f1);
        winCondition.add(f2);
        winCondition.add(f4);

        //Random rekkefølge
        ourFlagArray.add(f1);
        ourFlagArray.add(f2);
        ourFlagArray.add(f3);
        ourFlagArray.add(f4);

        ourFlagArray.sort(new Flag.CompareID());

    }


    @Test
    void testIfTheFirstFlagsAreTheSame() {
        assertEquals(winCondition.get(0), ourFlagArray.get(0));
    }

    @Test
    void testIfTheSecondFlagsAreTheSame() {
        assertEquals(winCondition.get(1), ourFlagArray.get(1));
    }

    @Test
    void testIfTheThirdFlagsAreTheSame() {
        assertEquals(winCondition.get(2), ourFlagArray.get(2));
    }

    @Test
    void testIfTheLastFlagsAreTheSame() {
        assertEquals(winCondition.get(3), ourFlagArray.get(3));
    }

}