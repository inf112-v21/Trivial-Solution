package GameBoard.Components;

public class Laser extends Wall{
    /**
     * Alle lasere er montert på en vegg, så de er i bunn og grunn vegger.
     * Derfor trenger vi metoder for å sjekke om man kan gå igjennom dem, så det arver vi fra Wall.
     */

    private final int direction;
    private final boolean isDoubleLaser;

    public Laser(int id, int dir, boolean isDoubleLaser) {
        super(id, dir!=2, dir!=3, dir!=0, dir!=1); //Siden laseren er montert på en vegg.
        this.isDoubleLaser = isDoubleLaser;
        this.direction = dir;
    }

    public int getDirection(){ return direction; }

    public boolean isDoubleLaser(){ return isDoubleLaser; }
}
