package GUIMain;

import Board.Board;
import Player.Register;
import Player.Robot;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class GUI extends Game implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    private TiledMap map;
    private TiledMapTileLayer backgroundLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer middlegroundLayer;
    private TiledMapTileLayer foregroundLayer;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Board board;
    private Stage stage;
    private String mapName;
    private int CELL_SIZE = 300;
    private int HEIGHT;
    private int WIDTH;
	private Sprite sprite;
	private boolean isInDebugMode;

    /**
     *
     * @param mapName navnet på filen som mappet skal baseres på. Husk .tmx!
     */
	public GUI(String mapName){ this(mapName, false); }

    /**
     *
     * @param mapName navnet på filen.
     * @param isInDebugMode Om denne er true blir vinduet lukket automatisk ved oppstart. Slik at vi kan kjøre testene.
     */
    public GUI(String mapName, boolean isInDebugMode){
        this.isInDebugMode = isInDebugMode;
        this.mapName = mapName;
    }

    @Override
    public void create() {
        stage = new Stage();

        TmxMapLoader tmx = new TmxMapLoader();
        map = tmx.load(mapName);

        backgroundLayer = (TiledMapTileLayer) map.getLayers().get("Background");
        middlegroundLayer = (TiledMapTileLayer) map.getLayers().get("Middleground");
        foregroundLayer = (TiledMapTileLayer) map.getLayers().get("Foreground");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Robot");

        HEIGHT = backgroundLayer.getHeight();
        WIDTH = backgroundLayer.getWidth();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, CELL_SIZE * WIDTH, CELL_SIZE * HEIGHT);
        camera.position.x = CELL_SIZE * WIDTH / 2;
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(map, 1);
        renderer.setView(camera);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        board = new Board(mapName);
        if (isInDebugMode) Gdx.app.exit(); //Lukker vinduet, om vi startet GUI-en kun for å teste ting.
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
        
        displayRobots(getPlayerImage1("alive"), getPlayerCell(getPlayerImage("alive")));
        renderer.render();
    }

    /**
     * Metode som viser et popup-vindu med en valgt beskjed.
     * @param message
     */
    public void showPopUp(String message, String windowTitle){
        JOptionPane.showMessageDialog(null, message, windowTitle, JOptionPane.INFORMATION_MESSAGE);
    }

    public Board getBoard(){ return board; }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    	
    }

    public void setPlayerCell(int x, int y, TiledMapTileLayer.Cell cell) {
		playerLayer.setCell(x, y, cell);
	}
	
	
	public TiledMapTileLayer.Cell getPlayerCell(StaticTiledMapTile PlayerTile){
		return new TiledMapTileLayer.Cell().setTile(PlayerTile);
	}
    
    /**
	 * Displays robot on screen in the given cell
	 * @param t robot image
	 * @param cell 
	 */
	public void displayRobots(TextureRegion t, TiledMapTileLayer.Cell cell) {

		for(int y = 0; y < board.getHeight(); y++) {
			for(int x = 0; x < board.getWidth(); x++) {
                Robot r = board.getRobotAt(x, y);
                if(r != null) {

                    batch = new SpriteBatch();
                    sprite = new Sprite(t.getTexture());

                    sprite.setColor(r.getColor());
                    batch.begin();
                    batch.draw(t.getTexture(), x, HEIGHT - y - 1);
                    sprite.draw(batch);

                    // TODO: 23.02.2021 Obs obs! Board og GUI er uenige i koordinatsystemer, Board sier at (0, 0) er oppe til venstre, mens GUI sier det er nede til venstre.
                    setPlayerCell(x,HEIGHT - y - 1, cell);

                    batch.end();
				
				}
			}
		}
	}
	
	
	/**
	 * 
	 * @param state
	 * @return the players state as a tile
	 */
	public StaticTiledMapTile getPlayerImage(String state) {

        Texture t = new Texture("player.png");
        TextureRegion[][] tmp = new TextureRegion(t).split(300,300);
		
		if(state.equals("alive")) {
			return  new StaticTiledMapTile(tmp[0][0]);
		}
		else if (state.equals("dead")) {
			return  new StaticTiledMapTile(tmp[0][1]);
		}
		else if (state.equals("victory")) {
			return new StaticTiledMapTile(tmp[0][2]);
		}
		return null;
	}
	
	/**
	 * 
	 * @param state either dead, alive or victory
	 * @return the players state as a texture which could be used as players image
	 */
	public TextureRegion getPlayerImage1(String state) {
        Texture t = new Texture("player.png");
        TextureRegion[][] tmp = new TextureRegion(t).split(300,300);
        switch (state) {
        case "dead":
        	return tmp[0][1];
        case "victory":
        	return tmp[0][2];
        default:
        	return tmp[0][0];
        }
	}
}
