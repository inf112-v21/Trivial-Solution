package Components;

/**
 * The flag. The player needs to have touched 3 flags in order (been on positions which the flags are at
 */

public class Flag extends SimpleComponent implements Comparable<Flag>{


    public Flag(int id){
        super(id);    //If we need a way to identify the different flags by name. Again, this will depend on
        this.name = "Flag"; // how we choose to remember the flags in the grid.
    }

    /**
     * Sammenligner ID'en til flaggene. Dette brukes for å sjekke hvilke flagg en robot har plukket opp.
     */
    @Override
    public int compareTo(Flag comparingFlag) {

        if (this.id > comparingFlag.id) {
            return 1;
        } else if (this.id < comparingFlag.id) {
            return -1;
        }
        return 0; // Hvis flaggene har lik ID så returnerer vi 0
    }
}
