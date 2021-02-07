package Cards;

class RotateLeft implements ICards {
    @Override
    public int getDistance() {
        return 0;
    }

    @Override
    public int getRotation() {
        return -1;
    }

    @Override
    public int priority() {
        return 18;
    }
}
