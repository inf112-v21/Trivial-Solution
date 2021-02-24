package Game;

import java.util.ArrayList;

public enum WinConditions {

    /**
     * De ulike vinn-mulighetene
     */
    ONEFLAG(55,0,0,0),
    TWOFLAGs(55,63,0,0),
    THREEFLAGS(55,63,71,0),
    FOURFLAGS(55,63,71,79);

    //Vinner kombinasjonen for spillet.
    private final ArrayList<Integer> winningCombo = new ArrayList<>(4);

    WinConditions(int f1, int f2, int f3, int f4) {
        this.winningCombo.add(f1);
        this.winningCombo.add(f2);
        this.winningCombo.add(f3);
        this.winningCombo.add(f4);
    }

    public ArrayList<Integer> getWinningCombo() {
        return winningCombo;
    }
}
