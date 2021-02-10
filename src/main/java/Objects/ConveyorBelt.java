package Objects;

/**
 * The ConveyorLane is what can move the robots on the board
 *
 * TODO: There are 16 different kind of conveyor lanes in the game in total.
 * TODO: There are 2 kinds, one that increments the position by 1 and one that does so by 2.
 * TODO: Then there are 4 that increment the position and 4 that change direction
 * TODO: Each one has a specific number in the TestMap.tmx file.
 *
 * How will i differentiate them? Since they all have one direction that they ultimatly
 */


public class ConveyorBelt implements IComponent{

    private static final String name = "ConveyorLane";
    private static final char symbol = 'C';
    private int speed;
    private int direction;
    private int id;

    ConveyorBelt(int id, int dir, int speed){
        this.id = id;
        direction = dir;
        this.speed = speed;
    }



    public int getDirection(){
        return direction;
    }

    public int getSpeed(){
        return speed;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
