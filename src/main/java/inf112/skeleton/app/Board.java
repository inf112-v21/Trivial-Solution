package inf112.skeleton.app;

import com.badlogic.gdx.scenes.scene2d.ui.Widget;

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
        this("");
    }

    private void readFromTMX(String filename){
        // TODO: 05.02.2021 Denne må kunne lese en tmx-fil og spawne alle brikker og roboter.
    }

    public void performMove(ICard card, Robot bot){

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

    public int getHeight(){
        return HEIGHT;
    }

    public int getWidth(){
        return WIDTH;
    }

}
