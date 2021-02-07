package Cards;
class MoveThree implements ICards{

    @Override
    public int getDistance() {
        //placeholder
        return 3;
    }

    @Override
    public int getRotation() {
        return 0;
    }

    @Override
    public int priority() {
        return 6;
    }
}
