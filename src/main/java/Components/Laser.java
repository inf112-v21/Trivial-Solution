package Components;

public class Laser extends SimpleComponent{

    private int direction;
    private boolean isDoubleLaser;

    public Laser(int id, int dir, boolean isDoubleLaser) {
        super(id);
        name = "Laser";
        this.isDoubleLaser = isDoubleLaser;
    }
}
