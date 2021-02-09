package Objects;


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
     *@return the symbol which is a character that represents a board-component on the board
     */
    //TODO: Maybe unnessecary if we are just going to store the components in their objectform
    char getSymbol();

}
