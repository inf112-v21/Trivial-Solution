package Components;

public class Wall extends SimpleComponent{

    private boolean up;
    private boolean right;
    private boolean down;
    private boolean left;

    public Wall(int id, boolean up, boolean right, boolean down, boolean left) {
        super(id);
        this.up = up;
        this.right = right;
        this.left = left;
        this.down = down;
        name = "Wall";
    }


    //Kanskje vi kaller de noe annet etterpå. 
    public boolean getNorth(){
        return up;
    }

    public boolean getEast(){
        return right;
    }

    public boolean getSouth(){
        return down;
    }

    public boolean getWest(){
        return left;
    }

    //public boolean en metode for å se hvordan ting virker? Kan en robot gå inn i denne ruten.

}
