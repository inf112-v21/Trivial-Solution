package GameBoard.Cards;

import com.badlogic.gdx.graphics.Texture;

public class ProgramCard implements ICard{
    final int distance;
    final int rotation;
    final int priority;
    final Texture cardImage;

    /**
     * @param dist The distance the card tells the robot to move.
     * @param rotation The rotation the card tells the robot to rotate
     * @param priority The priority the card has.
     */

    public ProgramCard(int dist, int rotation, int priority, Texture cardImage) { //
        this.distance = dist;
        this.rotation = rotation;
        this.priority = priority;
        this.cardImage = cardImage;
    }

    @Override
    public int getDistance() {
        return distance;
    }

    @Override
    public int getRotation() {
        return rotation;
    }

    @Override
    public int priority() {
        return priority;
    }

    @Override
    public Texture getCardImage(){
        return cardImage;
    }

    public String toString(){
        if(rotation != 0){
            if (rotation == -1) return "TURN LEFT | "  + priority;
            if (rotation == 1 ) return "TURN RIGHT | " + priority;
            if (rotation == 2 ) return "TURN AROUND | "+ priority;
        }
        if (distance == -1) return "BACK UP | " + priority;
        return "MOVE " + distance + " | " + priority;
    }

}