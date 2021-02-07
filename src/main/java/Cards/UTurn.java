package Cards;

class UTurn implements ICards {
    @Override
    public int getDistance() {
        return 0;
    }

    @Override
    public int getRotation() {
        return 2;
    }

    @Override
    public int priority() {
        return 6;
    }
}