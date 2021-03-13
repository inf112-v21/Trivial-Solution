package GUIMain;

import Player.Robot;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import GameBoard.Position;

public class Textures {
	private Position pos;
    private SpriteBatch batch;
    private Sprite sprite;
    private Robot robot;

    public Textures(Robot robot, OrthographicCamera camera, Position pos) {
        this.robot = robot;
        batch = new SpriteBatch();
        this.pos = pos;
        batch.setProjectionMatrix(camera.combined);
        sprite = new Sprite(getPlayerImage1(robot.getPlayerState()));
    }

    /**
     * Draws robot using sprite.
     * x and y coordinates needs to be multiplied with 300
     * to match a cell in the grid.
     */
    public void drawRobot() {
        batch.begin();
        sprite.setColor(robot.getColor());
        sprite.draw(batch);
        sprite.setPosition(pos.getX() * 300, 900 - (pos.getY() * 300) + 1800);
        batch.end();
    }

    private TextureRegion getPlayerImage1(String state) {
        Texture t = new Texture("mapassets/player.png");
        TextureRegion[][] tmp = new TextureRegion(t).split(300, 300);
        switch (state) {
            case "dead":
                return tmp[0][1];
            case "victory":
                return tmp[0][2];
            default:
                return tmp[0][0];
        }
    }

    public Robot getRobot() {
        return robot;
    }
    
    public void addNewPositions(Position p) {
    	this.pos = p;
    }

}
