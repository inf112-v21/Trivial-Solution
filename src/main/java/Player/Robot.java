package Player;

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
	private boolean isControlledByAI;

	private final ArrayList<Flag> flagsVisited = new ArrayList<>();

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

	public static ArrayList<Robot> getDefaultRobots(int n){
	    if (n < 0 || n > 8) throw new IllegalArgumentException("Expecteded >0 and <9 robots, but was " + n);
        ArrayList<Robot> ret = new ArrayList<>();
        for (int i = 0; i < n; i++) ret.add(defaultRobots[i]);
        return ret;
    }
}
