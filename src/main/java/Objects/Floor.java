package Objects;

public class Floor implements IComponent{

    private static final String name = "Tile";
    private int id;

    Floor(int id){

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getID() {
        return 0;
    }

}

