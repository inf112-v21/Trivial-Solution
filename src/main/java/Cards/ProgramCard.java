package Cards;

import com.badlogic.gdx.graphics.Texture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Samueljacoo
 *
 */

public class ProgramCard implements ICard{
    int distance;
    int rotation;
    int priority;
    Texture cardImage;

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
}