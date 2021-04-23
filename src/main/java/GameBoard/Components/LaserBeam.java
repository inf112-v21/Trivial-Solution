package GameBoard.Components;

import GUIMain.Screens.GameScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LaserBeam extends SimpleComponent{

    private final TextureRegion image;

    public LaserBeam(Integer ID, int x, int y){
        super(ID);
        this.image = new TextureRegion(new Texture("mapassets/tiles.png")).split(GameScreen.CELL_SIZE, GameScreen.CELL_SIZE)[x][y];

    }
    public TextureRegion getImage() {
        return image;
    }

}
