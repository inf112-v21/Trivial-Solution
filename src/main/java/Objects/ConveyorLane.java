package Objects;

/**
 * The ConveyorLane is what can move the robots on the board
 *
 * TODO: There are 16 different kind of conveyor lanes in the game in total.
 * TODO: There are 2 kinds, one that increments the position by 1 and one that does so by 2.
 * TODO: Then there are 4 that increment the position and 4 that change direction
 * TODO: Each one has a specific number in the TestMap.tmx file.
 *
 * How will i differentiate them?
 */


public class ConveyorLane implements IComponent{

    private String name = "ConveyorLane";
    private final char symbol = 'C'; //Maybe we should make an enum class for symbols?
    private int direction;

    ConveyorLane(int dir){
        direction = dir;
    }



    @Override
    public String getName() {
        return name;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
