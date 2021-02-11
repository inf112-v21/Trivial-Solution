package Board;

import Cards.ICard;
import Components.ComponentFactory;
import Components.Flag;
import Components.IComponent;;
import Player.Robot;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Board {


    int HEIGHT;
    int WIDTH;

    //Grids. Disse må initialiseres i readFromTMX().
    private Robot[][]      botgrid;
    private IComponent[][] backgrid;
    private IComponent[][] midgrid;
    private IComponent[][] forgrid;

    //Antall flagg i spillet.
    private int numberOfFlags = 0;

    public Board(String filename){
        readFromTMX(filename);
    }

    //Kun for testing.
    public Board(){
        this("assets/TestMap.tmx");
    }


    /**
     * Når Board startes opp må denne kalles. Denne finner ut av hvordan Boardet skal settes opp, hvor roboter står, etc.
     * Den setter all informasjonen inn i 4 grids, for 4 lag.
     *
     * @param filename navnet på filen som skal leses.
     */
    private void readFromTMX(String filename){
        TmxMapLoader tmx = new TmxMapLoader();
        TiledMap map = tmx.load(filename);

        TiledMapTileLayer background   = (TiledMapTileLayer) map.getLayers().get("Background");
        TiledMapTileLayer middleground = (TiledMapTileLayer) map.getLayers().get("Middleground");
        TiledMapTileLayer foreground   = (TiledMapTileLayer) map.getLayers().get("Foreground");
        TiledMapTileLayer robots       = (TiledMapTileLayer) map.getLayers().get("Robot");

        HEIGHT = background.getHeight();
        WIDTH  = background.getWidth();

        botgrid  = new Robot[HEIGHT][WIDTH];
        backgrid = new IComponent[HEIGHT][WIDTH];
        midgrid  = new IComponent[HEIGHT][WIDTH];
        forgrid  = new IComponent[HEIGHT][WIDTH];

        for (int y = 0; y < background.getHeight(); y++) {
            for (int x = 0; x < background.getWidth(); x++) {
                backgrid[y][x] = ComponentFactory.spawnComponent(background.getCell(x, y));
                midgrid[y][x] = ComponentFactory.spawnComponent(middleground.getCell(x, y));
                IComponent forcomp = ComponentFactory.spawnComponent(foreground.getCell(x, y));
                forgrid[y][x] = forcomp;
                if (forcomp instanceof Flag) numberOfFlags++;

                if (robots.getCell(x, y) != null){
                    botgrid[y][x] = new Robot("Robot" + (robots.getCell(x, y).getTile().getId() - 136), Color.WHITE); //Erstatt senere med custom navn og farger
                }
            }
        }
    }

    /**
     * Flytter en robot i henhold til kortet.
     *
     * @param card Bevegelseskortet
     * @param bot Roboten som skal flyttes
     */
    public void performMove(ICard card, Robot bot){
        if(card.getRotation() != 0){
            bot.setDirection((bot.getDirection() + card.getRotation()) % 4);
            return;
        }
        int dx = directionToX(bot.getDirection());
        int dy = directionToY(bot.getDirection());

        int botX = -1;
        int botY = -1;

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (botgrid[y][x].equals(bot)) {
                    botX = x;
                    botY = y;
                    break;
                }
            }
        }
        if (botX < 0 || botY < 0) throw new IllegalStateException("Kunne ikke finne botten på brettet?");

        if (isOutOfBounds(botX + dx, botY + dy)){
            //Placeholdere, her skal botten drepes og respawnes ved forrige respawn-punkt.
            System.out.println("Å nei! Du fallt utenfor brettet!");
            bot.applyDamage();
            return;
        }

        botgrid[botY+dy][botX+dx] = botgrid[botY][botX];
        botgrid[botY][botX] = null;
    }

    public void afterPhase(){
        // TODO: 11.02.2021 Det som skjer på slutten av hver fase. Lasere aktiveres, samlebånd går, etc.
    }

    public IComponent getBackgroundAt(int x, int y){
        return backgrid[y][x];
    }
    public IComponent getMiddlegroundAt(int x, int y){
        return midgrid[y][x];
    }
    public IComponent getForegroundAt(int x, int y){
        return forgrid[y][x];
    }
    public Robot getRobotAt(int x, int y){ return botgrid[y][x]; }

    public int getHeight(){
        return HEIGHT;
    }

    public int getWidth(){
        return WIDTH;
    }


    /** Konverterer retninger på formen 0, 1, 2, 3 til hvilken retninger det vil si for x-aksen. */
    private int directionToX(int dir){
        return - dir % 2 * (dir - 2);
    }

    /** Konverterer retninger på formen 0, 1, 2, 3 til hvilken retning det vil si for y-aksen. */
    private int directionToY(int dir){
        return (dir+1) % 2 * (dir - 1);
    }

    private boolean isOutOfBounds(int x, int y){
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

}
