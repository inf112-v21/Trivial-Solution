package Player;

import Cards.ICard;
import GameBoard.Position;
import Components.Flag;
import com.badlogic.gdx.graphics.Color;

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
	private final Color color;
	private int direction = 0;
	private Position respawnPoint;
	private final boolean isControlledByAI;
	private boolean powerDown;

	private final ArrayList<Flag> flagsVisited = new ArrayList<>();
	private ArrayList<ICard> allRegisterCards = new ArrayList<ICard>(); //alle 9 kortene som spilleren får utdelt
	private ArrayList<ICard> registerCards = new ArrayList<ICard>(5); //de 5 kortene som spilleren velger
	// Det første kortet i listen er kort nr.1 i registeret, og det siste kortet er kort nr.5.

	private static final Robot[] defaultRobots = {
        new Robot("Nebuchadnezzar", Color.DARK_GRAY, true),
        new Robot("Alexstrasza", Color.RED, true),
        new Robot("Gilgamesh", Color.YELLOW, true),
        new Robot("Ashurbarnipal", Color.GREEN, true),
        new Robot("Andromeda", Color.PINK, true),
        new Robot("Hephaistion", Color.CYAN, true),
        new Robot("Styxifus", Color.BROWN, true),
        new Robot("Promotheus", Color.GRAY, true)
    };
	
	
	public Robot(String name, Color color, boolean isControlledByAI){
		this.name = name;
		this.color = color;
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
	public int getRemainingLives() {
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
	public void applyDamage(int dmg) {
		hp -= dmg;
	}

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

	public Position getRespawnPoint(){ return respawnPoint; }
	public void setRespawnPoint(Position pos){ respawnPoint = pos; }
	
	/**
	 * Denne funksjonen leggr til et flag som en roboten henter
	 */
	public void addToFlagsVisited(Flag flag) { flagsVisited.add(flag);}


    public void rotate(int degree) {
	    direction = Math.floorMod(direction + degree, 4);
    }

	public Color getColor() {
		return color;
	}

	public boolean isControlledByAI(){return isControlledByAI; }
	
	public String getPlayerState() {
		if(lives <= 0) {
			return "dead";
		}
		if(this.getVisitedFlags().size() >= 3) {
			return "victory";
		}
		return "alive";
	}

	public void resetState(){
	    hp = INITIAL_HP;
	    lives = INITIAL_LIVES;
	    direction = 0;
    }

	public static ArrayList<Robot> getDefaultRobots(int n){
	    if (n < 0 || n > 8) throw new IllegalArgumentException("Expecteded >0 and <9 robots, but was " + n);
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
		allRegisterCards = cards;
	}

	public void resetCards(){ allRegisterCards.clear(); registerCards.clear(); }

	/**
	 * Denne metoden returnerer de 9 kortene som registeret holder.
	 * @return
	 */
	public ArrayList<ICard> getAvailableCards(){
		return allRegisterCards;
	}

	/**
	 * Denne metoden legger til et og et kort i rekkefølge i registeret utifra hva spilleren velger.
	 * @param chosenCard
	 */
	public void addChosenCard(ICard chosenCard){
		registerCards.add(chosenCard);
	}

	public void removeCardFromRegister(ICard unchosenCard){ registerCards.remove(unchosenCard); }

	/**
	 * @return returnerer maks 5 kort fra registeret, kan returnere færre dersom roboten har mye damage.
	 */
	public ArrayList<ICard> getMaxFiveCards(){
		return registerCards;
	}

	/**
	 * Metode for å "cleare" alle kortene i registerne før en nye runde starter.
	 */
	public void clearAllCards(){
		allRegisterCards.clear();
	}

	/**
	 * Returnerer "true" hvis roboten har en "powerdown", og "false" hvis den ikke har det.
	 * @return Tilstanden til roboten
	 */
	public Boolean isPowerDownAnnounced(){
		return powerDown;
	}

	/**
	 * Denne metoden endrer powerDown til true dersom knappen for powerDown blir trykket på i GameScreen-en for registeret.
	 */
	public void powerDownRobot(){
		powerDown = true;
	}
}
