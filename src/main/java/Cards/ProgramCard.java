package Cards;

/**
 *
 * @author Samueljacoo
 *
 */

public class ProgramCard implements ICard{
    int distance;
    int rotation;
    int priority;

    /**
     * @param dist The distance the card tells the robot to move.
     * @param rotation The rotation the card tells the robot to rotate
     * @param priority The priority the card has.
     */

    public ProgramCard(int dist, int rotation, int priority) {
        this.distance = dist;
        this.rotation = rotation;
        this.priority = priority;
    }

    @Override
    public int getDistance() {
        return distance;
    }

    @Override
    public int getRotation() {
        return rotation;
    }

    @Override
    public int priority() {
        return priority;
    }
}