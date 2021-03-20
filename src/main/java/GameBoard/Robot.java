package GameBoard;

import GameBoard.Cards.ICard;
import GameBoard.Components.Flag;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

/**
 * 
 * @author ilyasali
 *
 */
public class Robot{
    public static final int INITIAL_HP = 10;
    public static final int INITIAL_LIVES = 3;

    private int lives = INITIAL_LIVES;
	private int hp = INITIAL_HP;

	private final String name;
	private TextureRegion image;
	private int direction = 0;
	private Position respawnPoint;
	private final boolean isControlledByAI;
	private boolean powerDown;

	private final ArrayList<Flag> flagsVisited = new ArrayList<>();
	private ArrayList<ICard> availableCards = new ArrayList<>(); //alle kortene som ble utdelt
	private ArrayList<ICard> chosenCards = new ArrayList<>(5); //De valgte kortene, rekkefølgen er samme som den spilleren valgte dem
	
	public Robot(String name, int design, boolean isControlledByAI){
		this.name = name;
		this.isControlledByAI = isControlledByAI;
		this.image = new TextureRegion(new Texture("Robotdesigns/robots.png")).split(300, 300)[0][design];
		powerDown = false;
	}

	public Robot(String name, boolean isControlledByAI){
        this.name = name;
        this.isControlledByAI = isControlledByAI;
        powerDown = false;
    }
	
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return the life's left for this robot
	 */
	public int getLives() {
		return lives;
	}
	
	/**
	 * 
	 * @return the hp of this robot
	 */
	public int getHP() {
		return hp;
	}

	/**
	 * apply damage to this robot
	 */
	public void applyDamage(int dmg) { hp -= dmg; }

	public void takeLife(){ lives--; }
	
	/**
	 * 
	 * @return true if the robot is destroyed, ie. has no hp, false otherwise
	 */
	public boolean isDestroyed() {
		return hp < 1;
	}

    /**
     *
     * @return true if it has no remaining lives, false otherwise
     */
	public boolean hasRemainingLives(){
	    return lives > 0;
    }

    public void respawn(){
	    hp = INITIAL_HP - 2;
    }

	public int getDirection(){ return direction; }

	public void setDirection(int dir){
	    if (dir < 0 || dir > 3) throw new IllegalArgumentException();
	    direction = dir;
	}

	@Override 
	public String toString() {
		return "Name: " + name
            +  "\nLives: " + lives
            +  "\nHP: " + hp
            +  "\nDirection: " + direction;
	}

	/**
	 * @return Gir oss flaggene som roboten har besøkt.
	 */
	public ArrayList<Flag> getVisitedFlags(){ return flagsVisited;}

	public Position getRespawnPoint(){
	    if(respawnPoint == null) throw new NullPointerException("This robot has no spawnpoint, " +
                "make sure you spawn it with board.spawnRobot() and not board.placeRobot() " +
                "if you want it to have a spawnpoint.");
	    return respawnPoint;
	}
	public void setRespawnPoint(Position pos){ respawnPoint = pos; }
	
	/**
	 * Denne funksjonen leggr til et flag som en roboten henter
	 */
	public void addToFlagsVisited(Flag flag) { flagsVisited.add(flag);}


    public void rotate(int degree) {
	    direction = Math.floorMod(direction + degree, 4);
    }

	public TextureRegion getImage() {
	    if (image == null) throw new IllegalStateException("This robot has no image. If it should have one, please use the other Robot constructor instead.");
		return image;
	}

	public boolean isControlledByAI(){return isControlledByAI; }

	public void resetState(){
	    hp = INITIAL_HP;
	    lives = INITIAL_LIVES;
	    direction = 0;
    }

	public static ArrayList<Robot> getDefaultRobots(int n){
	    if (n < 0 || n > 8) throw new IllegalArgumentException("Expecteded >0 and <9 robots, but was " + n);
        //Denne her kunne vært en statisk feltvariabel, men da kan vi ikke kjøre tester som bruker roboter uten at denne blir ærklert,
        // og om GUI-en ikke har startet opp ennå får vi da feil når vi laster inn bildene.
        final Robot[] defaultRobots = {
                // TODO: 18.03.2021 Finn på en måte å sørge for at vi får tilfeldige (men fortsatt unike) designs for hver robot. Så vi ikke spiller mot de samme hver gang.
                new Robot("Nebuchadnezzar", 0, true),
                new Robot("Alexstrasza", 1, true),
                new Robot("Gilgamesh", 2, true),
                new Robot("Ashurbarnipal", 7, true),
                new Robot("Andromeda", 4, true),
                new Robot("Hephaistion", 5, true),
                new Robot("Styxifus", 6, true),
                new Robot("Promotheus", 3, true)
        };
        ArrayList<Robot> ret = new ArrayList<>();
        for (int i = 0; i < n; i++){
            defaultRobots[i].resetState();
            ret.add(defaultRobots[i]);
        }
        return ret;
    }
	/**
	 * Denne metoden "setter" de 9 kortene som registerer får inn.
	 */
	public void setAvailableCards(ArrayList<ICard> cards){
		availableCards = cards;
	}

	public void resetCards(){ availableCards.clear(); chosenCards.clear(); }

	/**
	 * Denne metoden returnerer de 9 kortene som registeret holder.
	 * @return listen over tilgjengelige kort
	 */
	public ArrayList<ICard> getAvailableCards(){
		return availableCards;
	}

	/**
	 * Denne metoden legger til et og et kort i rekkefølge i registeret utifra hva spilleren velger.
	 * @param chosenCard kortet som ble valgt
	 */
	public void chooseCard(ICard chosenCard){
		chosenCards.add(chosenCard);
	}

	public void removeCardFromRegister(ICard unchosenCard){ chosenCards.remove(unchosenCard); }

	/**
	 * @return returnerer maks 5 kort fra registeret, kan returnere færre dersom roboten har mye damage.
	 */
	public ArrayList<ICard> getChosenCards(){
		return chosenCards;
	}

	/**
	 * Returnerer "true" hvis roboten har en "powerdown", og "false" hvis den ikke har det.
	 * @return Tilstanden til roboten
	 */
	public boolean isPowerDownAnnounced(){
		return powerDown;
	}

	/**
	 * Denne metoden endrer powerDown til true dersom knappen for powerDown blir trykket på i GameScreen-en for registeret.
	 */
	public void togglePowerDown(){
		powerDown = !powerDown;
	}
}
