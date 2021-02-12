package GUIMain;

import Board.Board;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import org.lwjgl.system.CallbackI;

public class GUI extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    private TiledMap map;
    private TiledMapTileLayer backgroundLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer middlegroundLayer;
    private TiledMapTileLayer foregroundLayer;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private TiledMapTileLayer.Cell playerCell;
    private TiledMapTileLayer.Cell playerWonCell;
    private TiledMapTileLayer.Cell playerDiedCell;
    private com.badlogic.gdx.math.Vector2 playerPos;
    private Board bård;
    private String mapName;
    private int CELL_SIZE = 300;
    private int HEIGHT;
    private int WIDTH;

    public GUI(String mapName){
        this.mapName = mapName;
    }

    @Override
    public void create() {
        TmxMapLoader tmx = new TmxMapLoader();
        map = tmx.load(mapName);

        backgroundLayer = (TiledMapTileLayer) map.getLayers().get("Background");
        middlegroundLayer = (TiledMapTileLayer) map.getLayers().get("Middleground");
        foregroundLayer = (TiledMapTileLayer) map.getLayers().get("Foreground");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Robot");

        HEIGHT = backgroundLayer.getHeight();
        WIDTH = backgroundLayer.getWidth();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, CELL_SIZE*WIDTH, CELL_SIZE*HEIGHT);
        camera.position.x = CELL_SIZE*WIDTH / 2;
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map, 1);
        renderer.setView(camera);

        Texture t = new Texture("player.png");
        TextureRegion[][] tmp = new TextureRegion(t).split(CELL_SIZE,CELL_SIZE);

        StaticTiledMapTile PlayerTile = new StaticTiledMapTile(tmp[0][0]);
        StaticTiledMapTile PlayerDiedTile = new StaticTiledMapTile(tmp[0][1]);
        StaticTiledMapTile playerWonTile = new StaticTiledMapTile(tmp[0][2]);

        playerCell = new TiledMapTileLayer.Cell().setTile(PlayerTile);
        playerDiedCell = new TiledMapTileLayer.Cell().setTile(PlayerDiedTile);
        playerWonCell = new TiledMapTileLayer.Cell().setTile(playerWonTile);

        playerPos = new Vector2(0,0);

        // Legger til støtte for å flytte spilleren:
        Gdx.input.setInputProcessor(this);


        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        bård = new Board(mapName);
    }
    @Override
    public boolean keyUp(int keyCode){
        if(keyCode == Input.Keys.UP){
            playerPos.y++;
            playerLayer.setCell((int)playerPos.x,(int)playerPos.y-1,null);
            return true;
        }
        if(keyCode == Input.Keys.DOWN){
            playerPos.y--;
            playerLayer.setCell((int)playerPos.x,(int)playerPos.y+1,null);
            return true;
        }
        if(keyCode == Input.Keys.RIGHT){
            playerPos.x++;
            playerLayer.setCell((int)playerPos.x-1,(int)playerPos.y,null);
            return true;
        }
        if(keyCode == Input.Keys.LEFT){
            playerPos.x--;
            playerLayer.setCell((int)playerPos.x+1,(int)playerPos.y,null);
            return true;
        }
        return false;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.render();

        // Denne logikken bør flyttes ut av GUI asap
        playerLayer.setCell((int)playerPos.x,(int)playerPos.y, playerCell);

        if(middlegroundLayer.getCell((int)playerPos.x, (int)playerPos.y) != null) {
            if (middlegroundLayer.getCell((int) playerPos.x, (int) playerPos.y).getTile().getId() == 6)
                playerLayer.setCell((int) playerPos.x, (int) playerPos.y, playerDiedCell);
        }

        if(foregroundLayer.getCell((int)playerPos.x, (int)playerPos.y) != null) {
            switch (foregroundLayer.getCell((int) playerPos.x, (int) playerPos.y).getTile().getId()) {
                //Dette er de 4 ID-ene som flaggikonene våre har.
                case (55):
                    playerLayer.setCell((int) playerPos.x, (int) playerPos.y, playerWonCell);
                case (63):
                    playerLayer.setCell((int) playerPos.x, (int) playerPos.y, playerWonCell);
                case (71):
                    playerLayer.setCell((int) playerPos.x, (int) playerPos.y, playerWonCell);
                case (79):
                    playerLayer.setCell((int) playerPos.x, (int) playerPos.y, playerWonCell);
            }
        }

        foregroundLayer.getCell((int)playerPos.x, (int)playerPos.y);

    }

    public Board getBoard(){ return bård; }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
