package Objects;

/**
 * The flag. The player needs to have touched 3 flags in order (been on positions which the flags are at
 */

public class Flag implements IComponent {

    private static String name = "flag";
    private static int id;

    Flag(int id){
        this.id = id;    //If we need a way to identify the different flags by name. Again, this will depend on
                             // how we choose to remember the flags in the grid.
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getID() {
        return id;
    }


}
