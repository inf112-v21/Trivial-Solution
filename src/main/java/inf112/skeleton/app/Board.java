package inf112.skeleton.app;

import Cards.ICards;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.awt.*;

public class Board {


    int HEIGHT;
    int WIDTH;

    //Grids. Disse må initialiseres i readFromTMX().
    private Robot[][]            botgrid  = new Robot[HEIGHT][WIDTH];
    private BackgroundTile[][]   backgrid = new BackgroundTile[HEIGHT][WIDTH];
    private MiddlegroundTile[][] midgrid  = new MiddlegroundTile[HEIGHT][WIDTH];
    private ForegroundTile[][]   forgrid  = new ForegroundTile[HEIGHT][WIDTH];

    public Board(String filename){
        readFromTMX(filename);
    }

    //Kun for testing
    public Board(){
        this("assets/TestMap.tmx"); // TODO: 06.02.2021 Erstatt med Dusan sitt map når han er ferdig
    }

    private void readFromTMX(String filename){
        // TODO: 05.02.2021 Denne må kunne lese en tmx-fil og spawne alle brikker og roboter.
        TmxMapLoader tmx = new TmxMapLoader();
        TiledMap map = tmx.load(filename);

        TiledMapTileLayer background = (TiledMapTileLayer) map.getLayers().get("Background");
        TiledMapTileLayer middleground = (TiledMapTileLayer) map.getLayers().get("Middleground");
        TiledMapTileLayer foreground = (TiledMapTileLayer) map.getLayers().get("Foreground");
        for (int y = 0; y < background.getHeight(); y++) {
            for (int x = 0; x < background.getWidth(); x++) {


                //Disse skal erstattes med konstruktører for ulike karttilbehør når Dusan er ferdig
                System.out.println(background.getCell(x, y));
                System.out.println(middleground.getCell(x, y));
                System.out.println(foreground.getCell(x, y));
            }
        }
    }

    public void performMove(ICards card, Robot bot){
        // TODO: 06.02.2021
    }

    public BackgroundTile getBackgroundAt(int x, int y){
        return backgrid[y][x];
    }

    public MiddlegroundTile getMiddlegroundAt(int x, int y){
        return midgrid[y][x];
    }

    public ForegroundTile getForegroundAt(int x, int y){
        return forgrid[y][x];
    }

    public Robot getRobotAt(int x, int y){ return botgrid[y][x]; }

    public int getHeight(){
        return HEIGHT;
    }

    public int getWidth(){
        return WIDTH;
    }

}
