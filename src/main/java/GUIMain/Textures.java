package GUIMain;

import Player.Robot;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class Textures {
    private static int x = 0;
    private static int y = 0;
    static SpriteBatch batch;
    static Color color;
    static Sprite sprite;
    OrthographicCamera camera;
    Robot robot;

    public Textures(Robot robot, OrthographicCamera camera, int x, int y, Color c) {
        this.robot = robot;
        batch = new SpriteBatch();
        this.camera = camera;
        color = c;
        this.x = x;
        this.y = y;
        //System.out.println(robot.getPlayerState());
        batch.setProjectionMatrix(camera.combined);
        sprite = new Sprite(getPlayerImage1(robot.getPlayerState()));
        //System.out.println(robot.getName() + " is moving to (" + x + " - " + y + " )");
    }

    /**
     * Draws robot using sprite.
     * x and y coordinates needs to be multiplied with 300
     * to match a cell in the grid.
     */
    public void drawRobot() {
        batch.begin();
        sprite.setColor(color);
        sprite.draw(batch);
        sprite.setPosition(x * 300, 900 - (y * 300) + 1800);
        batch.end();
    }

    public TextureRegion getPlayerImage1(String state) {
        Texture t = new Texture("player.png");
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


}
