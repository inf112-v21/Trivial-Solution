package Components;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Her tester vi om Flag-comparatoren vil sortere flaggene i riktig rekkefølge
 * Det er aldri mer enn 4 flag på brette på et hvert tidspunkt.
 * Dermed sjekker vi ikke flere enn 4 flag.
 */


class FlagTest {

    private final static ArrayList<Flag> winCondition = new ArrayList<>();
    private ArrayList<Flag> ourFlagArray = new ArrayList<>();

    //Disse simulerer flaggene i spillet.
    private final static Flag f1 = new Flag(55);
    private final static Flag f2 = new Flag(63);
    private final static Flag f3 = new Flag(71);
    private final static Flag f4 = new Flag(79);

    @BeforeAll
    public static void setUpCorrectFlagFormation(){
        //Dette er den riktig rekkefølgen som flaggene skal ende opp i.
        winCondition.add(f1);
        winCondition.add(f2);
        winCondition.add(f3);
        winCondition.add(f4);

    }

    @BeforeEach
    public void setUpRandomFlags(){
        ourFlagArray.add(f1);
        ourFlagArray.add(f2);
        ourFlagArray.add(f3);
        ourFlagArray.add(f4);

        //Stokker om på flaggene slik at de kommer i en random rekkefølge
        Collections.shuffle(ourFlagArray);

        //Deretter sorterer vi flaggene
        ourFlagArray.sort(new Flag.CompareID());
    }


    @Test
    void checkIfOurFlagArrayIndeedHasTheFlagsOrganizedInThCorrectOrder() {
        assertEquals(winCondition, ourFlagArray);
    }

}