package Components;

public class Laser extends Wall{

    private int direction;
    private boolean isDoubleLaser;

    public Laser(int id, int dir, boolean isDoubleLaser) {
        super(id, dir!=2, dir!=3, dir!=0, dir!=1); //Siden laseren er montert på en vegg.
        name = "Laser";
        this.isDoubleLaser = isDoubleLaser;
    }

    public int getDirection(){ return direction; }

    public boolean isDoubleLaser(){ return isDoubleLaser; }
}
