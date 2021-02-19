package Player;

import Board.Position;
import Components.Flag;
import com.badlogic.gdx.graphics.Color;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;

/**
 * 
 * @author ilyasali
 *
 */
public class Robot{
<<<<<<< HEAD
	
	private int lives = 10;
	private int damage = 0;
=======

    public static final int INITIAL_HP = 10;
	private int lives = 3;
	private int hp = INITIAL_HP;
>>>>>>> d7d28c1b386a31a239f651daa7b5c9a4b490e605
	private String name;
	private ArrayList<Flag> flags;
	private Color color;
	private int direction = 0;
	private Position respawnPoint;
	
	
	public Robot(String name, Color color){
		this.name = name;
		this.color = color;
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
	
	/**
	 * 
	 * @return true if the robot is destroyed, e.g. has used all 3 lives, false otherwise
	 */
	public boolean isDestroyed() {
		return lives < 1;
	}

	public int getDirection(){ return direction; }

	public void setDirection(int dir){
	    if (dir < 0 || dir > 3) throw new IllegalArgumentException();
	    direction = dir;
	}

	@Override
	public String toString() {
		return getName() + " has " + getRemainingLives() + " "
				+ "lives and has " + getHP() + " hp.";
	}

	/**
	 * @return Gir oss flaggene som roboten har besøkt.
	 */
	public ArrayList<Flag> getVisitedFlags(){ return flags;}

	public Position getRespawnPoint(){ return respawnPoint; }
	public void setRespawnPoint(Position pos){ respawnPoint = pos; }
	
	/**
	 * Should only be called when the robot visited a flag
	 * Denne funksjonen sjekker om roboten hentet flagget på en sukksesful måte
	 *
	 * @Return true hvis roboten greide å plukke opp flagget. false ellers.
	 */
	public boolean flagVisited(Flag flag) {
		if (!flags.isEmpty()) {
			Flag lastAdded = flags.get(flags.size() - 1);

			if (lastAdded.compareTo(flag) < 0){

			}

		}
		flags.add(flag);

		return false;
	}


    public void rotate(int degree) {
	    direction = (direction + degree) % 4;
    }
}
