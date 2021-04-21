package GameBoard.Cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.javatuples.Triplet;

import java.io.Serializable;
import java.util.TreeMap;

public class ProgramCard implements Serializable {
    private final int distance;
    private final int rotation;
    private final int priority;

    //                               distance    degree   priority
    public static final TreeMap<Triplet<Integer, Integer, Integer>, Texture> textures = new TreeMap<>();

    /**
     * @param dist The distance the card tells the robot to move.
     * @param rotation The rotation the card tells the robot to rotate
     * @param priority The priority the card has.
     */

    public ProgramCard(int dist, int rotation, int priority) {
        this.distance = dist;
        this.rotation = rotation;
        this.priority = priority;
    }

    private void createTextures(){
        textures.put(new Triplet<>(0, 2, 10), new Texture(Gdx.files.internal("Cards/1 Red HULK X90/010 U-TURN 1Red 4.png")));
        textures.put(new Triplet<>(0, -1, 20), new Texture(Gdx.files.internal("Cards/1 Red HULK X90/020 ROTATE LEFT 1Red 3.png")));
        textures.put(new Triplet<>(0, 1,  30), new Texture(Gdx.files.internal("Cards/1 Red HULK X90/030 ROTATE RIGHT 1Red 3.png")));
        textures.put(new Triplet<>(0, -1, 40), new Texture(Gdx.files.internal("Cards/1 Red HULK X90/040 ROTATE LEFT 1Red 3.png")));
        textures.put(new Triplet<>(0, 1, 50), new Texture(Gdx.files.internal("Cards/1 Red HULK X90/030 ROTATE RIGHT 1Red 3.png")));
        textures.put(new Triplet<>(0, -1, 60), new Texture(Gdx.files.internal("Cards/1 Red HULK X90/060 ROTATE LEFT 1Red 3.png")));
        textures.put(new Triplet<>(0, 1, 70), new Texture(Gdx.files.internal("Cards/1 Red HULK X90/070 ROTATE RIGHT 1Red 3.png")));
        textures.put(new Triplet<>(-1, 0, 80), new Texture(Gdx.files.internal("Cards/1 Red HULK X90/080 BACK-UP 1Red 3.png")));
        textures.put(new Triplet<>(1, 0, 90), new Texture(Gdx.files.internal("Cards/1 Red HULK X90/090 MOVE1 1Red 3.png")));
        textures.put(new Triplet<>(1, 0, 100), new Texture(Gdx.files.internal("Cards/1 Red HULK X90/100 MOVE1 1Red 3.png")));
        textures.put(new Triplet<>(1, 0, 110), new Texture(Gdx.files.internal("Cards/1 Red HULK X90/110 MOVE1 1Red 3.png")));
        textures.put(new Triplet<>(2, 0, 140), new Texture(Gdx.files.internal("Cards/1 Red HULK X90/140 MOVE2 1Red 3.png")));
        textures.put(new Triplet<>(2, 0, 150), new Texture(Gdx.files.internal("Cards/1 Red HULK X90/150 MOVE2 1Red 3.png")));
        textures.put(new Triplet<>(3, 0, 170), new Texture(Gdx.files.internal("Cards/1 Red HULK X90/170 MOVE3 1Red 3.png")));
    }

    public int getDistance() {
        return distance;
    }

    public int getRotation() {
        return rotation;
    }

    public int priority() {
        return priority;
    }

    public Texture getCardImage(){
        if (textures.isEmpty()) createTextures();
        Texture ret = textures.get(new Triplet<>(distance, rotation, priority));
        if (ret == null) throw new NullPointerException("Could not find a suitable image for this card!");
        return ret;
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

    @Override
    public boolean equals(Object o){
        if (! (o instanceof ProgramCard)) return false;
        ProgramCard other = (ProgramCard) o;
        return this.distance == other.distance && this.priority == other.priority && this.rotation == other.rotation;
    }
}