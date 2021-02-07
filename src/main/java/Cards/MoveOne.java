package Cards;
class MoveOne implements ICards{

    @Override
    public int getDistance() {
        //placeholder
        return 1;
    }

    @Override
    public int getRotation() {
        return 0;
    }

    @Override
    public int priority() {
        return 18;
    }
}

