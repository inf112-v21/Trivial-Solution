package GameBoard.Components;

import GUIMain.Screens.GameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LaserBeam extends SimpleComponent{

    private final boolean isDoubleLaser;
    private final int dir;
    private final boolean crossingLasers;
    private final TextureRegion image;

    public LaserBeam(Integer ID, Integer dir, boolean isDoubleLaser, boolean crossingLasers, int x, int y){
        super(ID);
        this.isDoubleLaser = isDoubleLaser;
        this.dir = dir;
        this.crossingLasers = crossingLasers;
        this.image = new TextureRegion(new Texture("mapassets/tiles.png")).split(GameScreen.CELL_SIZE, GameScreen.CELL_SIZE)[x][y];

    }

    public TextureRegion getImage() {
        return image;
    }

    public boolean isDoubleLaser(){
        return isDoubleLaser;
    }

    public int getDirection(){
        return dir;
    }

    //TODO: Legg til getters ved behov
}
