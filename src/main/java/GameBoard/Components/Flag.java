package GameBoard.Components;

import java.util.Comparator;

/**
 * The flag. The player needs to have touched 3 flags in order (been on positions which the flags are at
 */

public class Flag extends SimpleComponent{


    public Flag(int id){
        super(id);    //If we need a way to identify the different flags by name. Again, this will depend on
    }

    /**
     * Sammenligner ID'en til flaggene. Dette brukes for å bestemmer rekkefølgen som robotene
     * må plukke opp flag i. Resultatet er altså den globale variabelen flagWinningFormation i Board
     * klassen.
     */
    public static class CompareID implements Comparator<Flag> {
        @Override
        public int compare(Flag flag1, Flag flag2) {
            if (flag1.getID() > flag2.getID()) {
                return 1;
            } else if (flag1.getID() < flag2.getID()) {
                return -1;
            }
            return 0; // Hvis flaggene har lik ID så returnerer vi 0
        }
    }
}
