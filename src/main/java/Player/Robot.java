package Player;

import Board.Position;
import Components.Flag;
import Game.WinConditions;
import com.badlogic.gdx.graphics.Color;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;

/**
 * 
 * @author ilyasali
 *
 */
public class Robot{

	
	private int lives = 10;
    public static final int INITIAL_HP = 10;
	private int hp = INITIAL_HP;

	private String name;
	private Color color;
	private int direction = 0;
	private Position respawnPoint;

	private ArrayList<Flag> flags = new ArrayList<>();
	private WinConditions checkOrder = WinConditions.THREEFLAGS;
	
	
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
	 * Denne funksjonen sjekker om roboten hentet flagget på en sukksesful måte
	 *
	 * @Return true hvis roboten greide å plukke opp flagget. false ellers.
	 */
	public boolean flagNotVisited(Flag flag) {

		checkOrder.getWinningCombo();

		boolean notVisited = true;
		for (Flag value : flags) {
			if (flag.compareTo(value) == 0) {
				notVisited = false;
			}
		}
		return notVisited;
	}



    public void rotate(int degree) {
	    direction = (direction + degree) % 4;
    }

	public Color getColor() {
		return color;
	}
}
