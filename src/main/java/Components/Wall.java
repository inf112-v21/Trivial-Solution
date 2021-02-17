package Components;

public class Wall extends SimpleComponent{

    private boolean north;
    private boolean east;
    private boolean south;
    private boolean west;

    public Wall(int id, boolean up, boolean right, boolean down, boolean left) {
        super(id);
        this.north = up;
        this.east = right;
        this.west = left;
        this.south = down;
        name = "Wall";
    }

    /**
     * @param dir Retningen man går i, på formen 0, 1, 2, 3
     * @return true om man kan gå til denne ruten i den retningen
     * @return false om man ikke kan det.
     */
    public boolean canGoToInDirection(int dir){
        switch (dir){
            case 0: return south;
            case 1: return west;
            case 2: return north;
            case 3: return east;
            default: throw new IllegalArgumentException();
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

    //Kanskje vi kaller de noe annet etterpå. 
    public boolean getNorth(){
        return north;
    }

    public boolean getEast(){
        return east;
    }

    public boolean getSouth(){
        return south;
    }

    public boolean getWest(){
        return west;
    }

    //public boolean en metode for å se hvordan ting virker? Kan en robot gå inn i denne ruten.

}
