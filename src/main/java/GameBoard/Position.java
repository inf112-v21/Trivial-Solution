package GameBoard;

import java.io.Serializable;

public class Position implements Serializable, Comparable<Position>{
    /**
     * En veldig enkel klasse for å representere en posisjon på brettet.
     */
    private final int x;
    private final int y;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){ return x; }
    public int getY(){ return y; }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Position){
            Position o = (Position) other;
            return this.getX() == o.getX() && this.getY() == o.getY();
        }
        return false;
    }

    @Override
    public int compareTo(Position o) {
        if(this.getX() == o.getX()) return this.getY() - o.getY();
        return this.getX() - o.getX();
    }
}
