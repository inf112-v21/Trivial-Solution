package Components;

public class Wall extends SimpleComponent{

    private boolean up;
    private boolean right;
    private boolean down;
    private boolean left;

    public Wall(int id, boolean up, boolean right, boolean down, boolean left) {
        super(id);
        this.up = up;
        this.right = right;
        this.left = left;
        this.down = down;
        name = "Wall";
    }



}
