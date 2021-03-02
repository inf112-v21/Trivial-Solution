package GameBoard;

import Cards.ICard;
import Components.Flag.*;
import Components.*;
import Player.Robot;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

public class Board {


    private int HEIGHT;
    private int WIDTH;

    //Grids. Disse må initialiseres i readFromTMX().
    private Robot[][]      botgrid;
    private IComponent[][] backgrid;
    private IComponent[][] midgrid;
    private IComponent[][] forgrid;

    private final TreeMap<Robot, Position> botPositions = new TreeMap<>((Object bot1, Object bot2) -> Integer.compare(bot1.hashCode(), bot2.hashCode()));
    private final TreeMap<Laser, Position> laserPositions = new TreeMap<>((Object laser1, Object laser2) -> Integer.compare(laser1.hashCode(), laser2.hashCode()));
    private final LinkedList<Position> availableSpawnPoints = new LinkedList<>();
    private final LinkedList<Robot> robotsWaitingToBeRespawned = new LinkedList<>();

    //Antall flagg i spillet.
    private final ArrayList<Flag> flagWinningFormation = new ArrayList<>();

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

        HEIGHT = background.getHeight();
        WIDTH  = background.getWidth();

        botgrid  = new Robot     [HEIGHT][WIDTH];
        backgrid = new IComponent[HEIGHT][WIDTH];
        midgrid  = new IComponent[HEIGHT][WIDTH];
        forgrid  = new IComponent[HEIGHT][WIDTH];

        ArrayList<Object[]> newSpawnPositions = new ArrayList<>(8);
        for (int y = 0; y < background.getHeight(); y++) {
            for (int x = 0; x < background.getWidth(); x++) {
                backgrid[HEIGHT-1-y][x] = ComponentFactory.spawnComponent(background.getCell(x, y));
                midgrid[HEIGHT-1-y][x] = ComponentFactory.spawnComponent(middleground.getCell(x, y));

                IComponent forcomp = ComponentFactory.spawnComponent(foreground.getCell(x, y));
                forgrid[HEIGHT-1-y][x] = forcomp;
                if (forcomp instanceof Flag) {
                    Flag newFlag = (Flag)forcomp;
                    flagWinningFormation.add(newFlag);

                    // Sorterer flaggene slik at roboten kan hente de i riktig rekkefølge
                    flagWinningFormation.sort(new Flag.CompareID());
                }
                else if(forcomp instanceof Laser) laserPositions.put((Laser)forcomp, new Position(x, HEIGHT-1-y));
                else if(forcomp instanceof SpawnPoint) newSpawnPositions.add(new Object[]{forcomp.getID(), new Position(x, HEIGHT-1-y)});
            }
        }
        //Dette burde være det samme som å sortere etter lavest ID.
        newSpawnPositions.sort((o1, o2) -> Integer.compare(o2[0].hashCode(), o1[0].hashCode()));
        for(Object[] o : newSpawnPositions) availableSpawnPoints.addFirst((Position) o[1]);
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

        moveTowards(card.getDistance(), pos.getX(),pos.getY(),bot.getDirection());

    }

    /**
     * Sjekker om en av roboten landet på et flag eller ikke.
     * Hvis roboten gjorde det så vil vi se om roboten kan plukke opp
     * flagget (Metode robotCanPickUpFlag)
     *
     */
    private void checkIfRobotIsOnFlag() {

        for (Robot bot : botPositions.keySet()) {
            Position pos = botPositions.get(bot);

            int posY = pos.getY();
            int posX = pos.getX();

            if (forgrid[posY][posX] instanceof Flag) {
                Flag newFlag = (Flag) forgrid[posY][posX];

                if (robotCanPickUpFlag(bot, newFlag)) {
                    bot.addToFlagsVisited(newFlag);
                }
            }
        }

    }


    /**
     * Denne metoden bruker den globale variabelen flagWinningFormation
     * for å forsikre seg om at flaggene blir plukket opp/registrert
     * i riktig rekkefølge
     *
     * TODO: Skriv test til denne!!
     *
     * @param bot - roboten vår
     * @param foundFlag - flagget som roboten landet på
     * @return -true hvis roboten kan plukke opp/registrere flagget. Flase ellers.
     */
    public boolean robotCanPickUpFlag(Robot bot, Flag foundFlag) {

        ArrayList<Flag> visited = bot.getVisitedFlags();
        if (!visited.isEmpty()){

            //Finn siste besøkte flagg og hvilket det neste flagget som skal besøkes er
            Flag lastVisitedFlag = visited.get(visited.size()-1);
            int nextFlag = flagWinningFormation.indexOf(lastVisitedFlag) + 1;

            //Hvis neste flagg som skal registreres er det flagget roboten fant så kan vi registrere det.
            return foundFlag.equals(flagWinningFormation.get(nextFlag));
        }

        //Hvis inget flagg er registrert så sjekker vi om roboten landet på det første flagget
        if (foundFlag.equals(flagWinningFormation.get(0))) return true;

        return false;
    }

    /**
     * Det som skal skje på slutten av hver fase.
     * Lasere blir avfyrt, samlebånd går av, roboter blir reparert, etc.
     * TODO: Flagg skal bli plukket opp
     */
    public void endPhase(){
        checkIfRobotIsOnFlag();
        updateRespawnPoints();
        fireAllLasers();
        removeDestroyedRobots();
        respawnRobots();
    }

    private void updateRespawnPoints(){
        for (Robot bot : botPositions.keySet()){
            Position pos = botPositions.get(bot);
            if (midgrid[pos.getY()][pos.getX()] instanceof CheckPoint){
                bot.setRespawnPoint(pos);
            }
        }
    }

    private void respawnRobots(){
        for(Robot bot : robotsWaitingToBeRespawned){
            Position spawnpoint = bot.getRespawnPoint();
            if (spawnpoint == null) throw new IllegalStateException("This bot has no spawnpoint. Try spawning it with spawnRobot() instead of placeRobotAt() if you want to respawn it automatically.");

            if(botgrid[spawnpoint.getY()][spawnpoint.getX()] == null){
                botgrid[spawnpoint.getY()][spawnpoint.getX()] = bot;
                robotsWaitingToBeRespawned.removeFirst();
                botPositions.put(bot, spawnpoint);
                bot.respawn();
            }
        }
    }

    private void removeDestroyedRobots(){
        for (Robot bot : botPositions.keySet()){
            if(bot.isDestroyed()){
                bot.takeLife();
                if (!bot.hasRemainingLives()){
                    robotsWaitingToBeRespawned.addLast(bot);
                }
                botgrid[botPositions.get(bot).getY()][botPositions.get(bot).getX()] = null;
                botPositions.remove(bot);
            }
            // TODO: 26.02.2021 Skal vi kanskje gjøre noe spesiellt når noen har dødd for tredje og siste gang? Si ifra til brukeren?
        }
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

        if(outOfBounds(toX, toY) || midgrid[toY][toX] instanceof Hole){
            botFellOff(bot);
            return true;
        }

        //Om botten kræsjer inn i en vegg.
        if(forgrid[fromY][fromX] instanceof Wall && !((Wall)forgrid[fromY][fromX]).canLeaveInDirection(dir)) return false;
        if(forgrid[toY][toX] instanceof Wall && !((Wall) forgrid[toY][toX]).canGoToInDirection(dir)) return false;

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
        bot.takeLife();
        robotsWaitingToBeRespawned.addLast(bot);
        botgrid[botPositions.get(bot).getY()][botPositions.get(bot).getX()] = null;
        botPositions.remove(bot);
    }

    public Robot getRobotAt(int x, int y){ return botgrid[y][x]; }

    /**
     * Henter flaggene fra forgriden slik at vi kan bruke Flaggene for
     * å teste at robotene henter riktige flagg i BoardTest.
     *
     * @param posY y posisjonen til flagget i tmx-filen
     * @param posX x posisjonene til flagget i tmx-filen
     * @return Flagget fra den posisjonen
     */
    public Flag getFlagInForgridAt(int posY, int posX) {
        if (!(forgrid[posY][posX] instanceof Flag)){
            throw new IllegalStateException("Du har angit en posisjon som ikke inneholder et flag.");
        }
        return (Flag) forgrid[posY][posX];
    }

    public void placeRobotAt(int x, int y, Robot bot){
        botPositions.put(bot, new Position(x, y));
        botgrid[y][x] = bot;
    }

    public void spawnRobot(Robot bot){
        if(availableSpawnPoints.isEmpty()) throw new IllegalStateException("Found no available spawnpoints! Perhaps you added more robots than the game could handle?");
        Position pos = availableSpawnPoints.pollFirst();
        bot.setRespawnPoint(pos);
        placeRobotAt(pos.getX(), pos.getY(), bot);
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

    public ArrayList<Flag> getWinningCombo() { return flagWinningFormation;}
}