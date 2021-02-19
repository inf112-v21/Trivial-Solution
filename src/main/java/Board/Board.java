package Board;

import Cards.ICard;
import Components.*;
import Player.Robot;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.TreeMap;

public class Board {


    int HEIGHT;
    int WIDTH;

    //Grids. Disse må initialiseres i readFromTMX().
    private Robot[][]      botgrid;
    private IComponent[][] backgrid;
    private IComponent[][] midgrid;
    private IComponent[][] forgrid;

    private TreeMap<Robot, Position> botPositions = new TreeMap((Object bot1, Object bot2) -> Integer.compare(bot1.hashCode(), bot2.hashCode()));
    private TreeMap<Laser, Position> laserPositions = new TreeMap((Object laser1, Object laser2) -> Integer.compare(laser1.hashCode(), laser2.hashCode()));


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

        botgrid  = new Robot     [HEIGHT][WIDTH];
        backgrid = new IComponent[HEIGHT][WIDTH];
        midgrid  = new IComponent[HEIGHT][WIDTH];
        forgrid  = new IComponent[HEIGHT][WIDTH];

        for (int y = 0; y < background.getHeight(); y++) {
            for (int x = 0; x < background.getWidth(); x++) {
                backgrid[HEIGHT-1-y][x] = ComponentFactory.spawnComponent(background.getCell(x, y));
                midgrid[HEIGHT-1-y][x] = ComponentFactory.spawnComponent(middleground.getCell(x, y));

                IComponent forcomp = ComponentFactory.spawnComponent(foreground.getCell(x, y));
                forgrid[HEIGHT-1-y][x] = forcomp;
                if (forcomp instanceof Flag) numberOfFlags++;
                else if(forcomp instanceof Laser) laserPositions.put((Laser)forcomp, new Position(x, HEIGHT-1-y));

                if (robots.getCell(x, y) != null){
                    Robot bot = new Robot("Robot" + (robots.getCell(x, y).getTile().getId() - 136), Color.WHITE); //Erstatt senere med custom navn og farger
                    botgrid[HEIGHT-1-y][x] = bot;
                    botPositions.put(bot, new Position(x, HEIGHT-1-y));
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
            bot.rotate(card.getRotation());
            if (card.getDistance() != 0) throw new IllegalArgumentException("A card has to be either a moving card, or a rotation card. This one is both!");
            return;
        }
        Position pos = botPositions.get(bot);
        if (pos == null) throw new IllegalArgumentException("Could not find the bot");

        moveTowards(card.getDistance(), pos.getX(), pos.getY(), bot.getDirection());
    }

    /**
     * Det som skal skje på slutten av hver fase.
     * Lasere blir avfyrt, samlebånd går av, roboter blir reparert, etc.
     * TODO: Alt unntatt lasere gjenstår.
     */
    public void endPhase(){
        fireAllLasers();
    }

    /**
     * En metode for å rekursivt flytte en robot i én retning.
     * Om den kræsjer inn i en vegg stopper den opp.
     * Om den kræsjer inn i en bot blir begge flyttet den veien,
     * med mindre den også kræsjer inn i en vegg, da står begge stille.
     *
     * @param N_Moves Antall skritt vi skal prøve å gå
     * @param fromX x-posisjon vi flytter fra
     * @param fromY y-posisjon vi flytter fra
     * @param dir Retningen, oppgitt i 0, 1, 2, 3
     * @return true om den klarte å gå minst ett skritt
     * @return false om den ble stoppet av en vegg eller noe
     */
    private boolean moveTowards(int N_Moves, int fromX, int fromY, int dir){
        if (N_Moves <= 0) return true;

        Robot bot = botgrid[fromY][fromX];
        if (bot == null) throw new NullPointerException("There is no bot at (" + fromX + ", " + fromY + ").");

        int toX = fromX + directionToX(dir);
        int toY = fromY + directionToY(dir);

        if(outOfBounds(toX, toY)){
            botFellOff(bot);
            return true; //Eller false, avhengig av hva som skal skje når botten faller av brettet.
        }

        //Om botten kræsjer inn i en vegg.
        if(midgrid[fromY][fromX] instanceof Wall && !((Wall)midgrid[fromY][fromX]).canLeaveInDirection(dir)) return false;
        if(midgrid[toY][toX] instanceof Wall && !((Wall) midgrid[toY][toX]).canGoToInDirection(dir)) return false;

        //Om botten prøver å dytte en annen robot.
        Robot target = botgrid[toY][toX];
        if (target != null && !moveTowards(1, toX, toY, dir)) return false; //Om botten kræsjet inn i en annen bot, og ikke klarte å dytte den.

        //Flytter roboten, endelig.
        botgrid[toY][toX] = bot;
        botgrid[fromY][fromX] = null;
        botPositions.put(bot, new Position(toX, toY));
        moveTowards(N_Moves-1, toX, toY, dir);
        return true;
    }

    /**
     * Avfyrer alle lasere. inkluderte de skutt av robotene.
     */
    private void fireAllLasers(){
        for(Laser laser : laserPositions.keySet()){
            Position pos = laserPositions.get(laser);
            fireOneLaser(pos.getX(), pos.getY(), laser.getDirection(), laser.isDoubleLaser(), false);
        }
        for(Robot bot : botPositions.keySet()){
            Position pos = botPositions.get(bot);
            fireOneLaser(pos.getX(), pos.getY(), bot.getDirection(), false, true);
        }
    }

    /**
     * Skyter en laser ett og ett skritt, rekursivt.
     * Om laseren er avfyrt av en robot, skal den første ruten ignoreres, slik at roboten ikke treffer seg selv.
     *
     * @param x x-posisjonen akkurat nå
     * @param y y-posisjonen akkurat np
     * @param dir retningen til laseren
     * @param isDoubleLaser om det er en dobbellaser eller ikke.
     * @param ignoreFirst om laseren er avfyrt av en robot.
     */
    private void fireOneLaser(int x, int y, int dir, boolean isDoubleLaser, boolean ignoreFirst){
        if(!ignoreFirst && botgrid[y][x] != null){
            botgrid[y][x].applyDamage(isDoubleLaser ? 2 : 1);
            return;
        }

        if(midgrid[y][x] instanceof Wall && !((Wall) midgrid[y][x]).canLeaveInDirection(dir)) return;

        int nextX = x + directionToX(dir);
        int nextY = y + directionToY(dir);

        if (outOfBounds(nextX, nextY)) return;
        if (midgrid[nextY][nextX] instanceof Wall && !((Wall) midgrid[nextY][nextX]).canGoToInDirection(dir)) return;

        fireOneLaser(nextX, nextY, dir, isDoubleLaser, false);
    }



    private void botFellOff(Robot bot){
        // TODO: 17.02.2021 Her skal botten drepes og respawnes ved forrige respawn-punkt.
        System.out.println("Å nei! Du fallt utenfor brettet!");
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

    public void placeRobotAt(int x, int y, Robot bot){
        botPositions.put(bot, new Position(x, y));
        botgrid[y][x] = bot;
    }

    public int getHeight(){
        return HEIGHT;
    }
    public int getWidth(){
        return WIDTH;
    }


    /**
     * Konverterer retninger på formen 0, 1, 2, 3 til hvilken retning det vil si for x-aksen.
     *
     * Eksempler:
     *    0 -> 0
     *    1 -> 1
     *    2 -> 0
     *    3 -> -1
     */
    private int directionToX(int dir){
        return - dir % 2 * (dir - 2);
    }

    /**
     * Konverterer retninger på formen 0, 1, 2, 3 til hvilken retning det vil si for y-aksen.
     *
     * Eksempler:
     *    0 -> -1
     *    1 -> 0
     *    2 -> 1
     *    3 -> 0
     */
    private int directionToY(int dir){
        return (dir+1) % 2 * (dir - 1);
    }

    private boolean outOfBounds(int x, int y){
        return !(x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT);
    }
}
