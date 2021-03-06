package GameBoard.Components;

public class Wall extends SimpleComponent{

    private final boolean north;
    private final boolean east;
    private final boolean south;
    private final boolean west;

    public Wall(int id, boolean up, boolean right, boolean down, boolean left) {
        super(id);
        this.north = up;
        this.east = right;
        this.west = left;
        this.south = down;
    }

    /**
     * @param dir Retningen man går i, på formen 0, 1, 2, 3
     * @return om man kan gå til denne ruten i den retningen eller ikke
     */
    public boolean canGoToInDirection(int dir){
        switch (dir){
            case 0: return south;
            case 1: return west;
            case 2: return north;
            case 3: return east;
            default: throw new IllegalArgumentException("illegal direction: " + dir);
        }
    }

    public boolean canLeaveInDirection(int dir){
        switch (dir){
            case 0: return north;
            case 1: return east;
            case 2: return south;
            case 3: return west;
            default: throw new IllegalArgumentException();
        }
    }
}
