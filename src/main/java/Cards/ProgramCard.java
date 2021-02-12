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
     *
     * @param dist The distance to move, may be zero
     * @param rotation The radians to rotate is rotation*pi/2
     * @param priority The priority level of the card.
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