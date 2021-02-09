package Cards;

public class ProgramCard implements ICard{
    int x;
    int y;
    int z;

    public ProgramCard(int distance, int rotation, int priority) {
        x = distance;
        y = rotation;
        z = priority;
    }

    @Override
    public int getDistance() {
        return x;
    }

    @Override
    public int getRotation() {
        return y;
    }

    @Override
    public int priority() {
        return z;
    }
}