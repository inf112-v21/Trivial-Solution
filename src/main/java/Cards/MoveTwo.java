package Cards;
class MoveTwo implements ICards{

    @Override
    public int getDistance() {
        //placeholder
        return 2;
    }

    @Override
    public int getRotation() {
        return 0;
    }

    @Override
    public int priority() {
        return 12;
    }
}