package Components;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FlagTest {

    @Set
    //Her tester vi om comparatoren vil generere flaggene i riktig rekkefølge
    private static final ArrayList<Flag> winCondition = new ArrayList<>();

    @Test
    void testIfTheFlagsGetSortedCorrectly() {
        Flag f1 = new Flag(63);
        Flag f2 = new Flag(71);
        Flag f3 = new Flag(55);
        Flag f4 = new Flag(79);

        winCondition.add(f1);
        winCondition.add(f2);
        winCondition.add(f3);
        winCondition.add(f4);

        winCondition.sort(new Flag.CompareID());

        Flag[] currentWinCondition = (Flag[]) winCondition.toArray();

        Flag[] expectedWinCondition = {f3,f1,f2,f4};


        assertArrayEquals(expectedWinCondition,currentWinCondition);

    }
}