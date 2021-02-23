package Game;

public enum WinConditions {

    /**
     * De ulike vinn-mulighetene
     */
    ONEFLAG(55,0,0,0),
    TWOFLAGs(55,63,0,0),
    THREEFLAGS(55,63,71,0),
    FOURFLAGS(55,63,71,79);

    //Vinner kombinasjonen for spillet.
    private final int[] winningCombo;

    WinConditions(int f1, int f2, int f3, int f4) {
        winningCombo = new int[]{f1, f2, f3, f4};
    }

    public int[] getWinningCombo() {
        return winningCombo;
    }
}
