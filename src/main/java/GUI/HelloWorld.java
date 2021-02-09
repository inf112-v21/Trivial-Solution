package GUI;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.lang.reflect.Member;

public class HelloWorld implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    private TiledMap map;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer Player;
    private TiledMapTileLayer Hole;
    private TiledMapTileLayer Flag;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    @Override
    public void create() {
        TmxMapLoader tmx = new TmxMapLoader();
        TiledMap map = tmx.load("TestMap.tmx");

        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");
        Player = (TiledMapTileLayer) map.getLayers().get("Player");
        Hole = (TiledMapTileLayer) map.getLayers().get("Hole");
        Flag = (TiledMapTileLayer) map.getLayers().get("Flag");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 3000, 3000);
        camera.position.x = 1500;
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map, 1);
        renderer.setView(camera);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED); //Testing git push siste gang
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
