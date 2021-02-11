package Components;

/**
 * The flag. The player needs to have touched 3 flags in order (been on positions which the flags are at
 */

public class Flag extends SimpleComponent{


    public Flag(int id){
        super(id);    //If we need a way to identify the different flags by name. Again, this will depend on
        this.name = "Flag"; // how we choose to remember the flags in the grid.
    }

}
