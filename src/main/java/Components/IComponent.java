package Components;


/**
 * General explanation:
 * Every component in our game is supposed to be in one type of specific position.
 * Therefore, to be able to place different objects in the same position, we have chosen to represent
 * the board in 4 different layers --> grids.
 *
 * Some objects will be placed on tiles in the background layer, some in the foreground layer and some in the
 * middleground layer.
 */

public interface IComponent {

    /**
     *@return the name
     */
    String getName();

    /**
     * @return the id of a component. We use this to identify which component we have on the field.
     */
    int getID();


    //We will probably need a guiRepresentation for each component
    //Maybe unicode character? Will leave it empty for now
    default void guiRepresentation(){};


    /**
     * Other thoughts about components
     * -Maybe we should make an enum class for symbols?
     */

}
