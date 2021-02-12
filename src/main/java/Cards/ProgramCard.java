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
     * @param x The distance the card tells the robot to move.
     * @param y The rotation the card tells the robot to rotate
     * @param z The priority the card has.
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