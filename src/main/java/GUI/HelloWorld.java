package GUI;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
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

import java.lang.reflect.Member;

public class HelloWorld implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    private TiledMap map;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer Hole;
    private TiledMapTileLayer Flag;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    //TODO sjekk disse
    private TiledMapTileLayer.Cell playerCell;
    private TiledMapTileLayer.Cell playerWonCell;
    private TiledMapTileLayer.Cell playerDiedCell;
    private com.badlogic.gdx.math.Vector2 playerPos;

    @Override
    public void create() {
        TmxMapLoader tmx = new TmxMapLoader();
        TiledMap map = tmx.load("eksempel.tmx");

        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");
        //Hole = (TiledMapTileLayer) map.getLayers().get("Hole");
        //Flag = (TiledMapTileLayer) map.getLayers().get("Flag");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1500, 1500);
        camera.position.x = 750;
        // TestMap trenger doble verdier i de to linjene over.
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map, 1);
        renderer.setView(camera);

        Texture t = new Texture("player.png");
        TextureRegion[][] tmp = new TextureRegion(t).split(300,300);
        //System.out.println(tmp[0][0]);
        // får nullpointer her av en eller annen grunn

        StaticTiledMapTile PlayerTile = new StaticTiledMapTile(tmp[0][0]);
        //StaticTiledMapTile PlayerDiedTile = new StaticTiledMapTile(tmp[0][1]);
        //StaticTiledMapTile playerWonTile = new StaticTiledMapTile(tmp[0][2]);

        playerCell = new TiledMapTileLayer.Cell().setTile(PlayerTile);
        //playerDiedCell = new TiledMapTileLayer.Cell().setTile(PlayerDiedTile);
        //playerWonCell = new TiledMapTileLayer.Cell().setTile(playerWonTile);

        playerPos = new Vector2(0,0);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
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
        playerLayer.setCell((int)playerPos.x,(int)playerPos.y, playerCell);

    }

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
