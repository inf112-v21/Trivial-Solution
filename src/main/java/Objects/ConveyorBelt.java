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


public class ConveyorBelt extends SimpleComponent{

    private int speed;
    private int dir;


    public ConveyorBelt(int id, int dir, int speed){
        super(id);
        this.dir = dir;
        this.speed = speed;
        this.name = "ConveyorBelt";
    }


    public int getDirection(){
        return dir;
    }

    public int getSpeed(){
        return speed;
    }


}
