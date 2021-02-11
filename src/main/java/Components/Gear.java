package Components;

public class Gear extends SimpleComponent {

    private int rotation;

    public Gear(int id, int rotation) {
        super(id);
        this.rotation = rotation;
        name = "Gear";
    }

    public int getRotation(){ return rotation; }
}
