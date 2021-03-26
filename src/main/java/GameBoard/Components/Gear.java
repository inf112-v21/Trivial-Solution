package GameBoard.Components;

public class Gear extends SimpleComponent {

    private final int rotation;

    public Gear(int id, int rotation) {
        super(id);
        this.rotation = rotation;
    }

    public int getRotation(){ return rotation; }
}
