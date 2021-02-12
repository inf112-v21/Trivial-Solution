package Components;

public class Hole extends SimpleComponent {

    private static final int damage = 10;

    public Hole(int id) {
        super(id);
        name = "Hole";
    }


    public int getDamage(){
        return damage;
    }
}
