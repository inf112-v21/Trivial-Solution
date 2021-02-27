package Player;

import Board.Position;
import Components.Flag;
import Game.WinConditions;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

/**
 * 
 * @author ilyasali
 *
 */
public class Robot{

	
	private final int lives = 3;
    public static final int INITIAL_HP = 10;
	private int hp = INITIAL_HP;

	private final String name;
	private final Color color;
	private int direction = 0;
	private Position respawnPoint;

	private final ArrayList<Flag> flags = new ArrayList<>();
	private final static ArrayList<Flag> winningCombo = new ArrayList<>();
	
	
	public Robot(String name, Color color, ArrayList<Flag> flagWinningFormation){
		this.name = name;
		this.color = color;
		winningCombo.addAll(flagWinningFormation);
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
	public String flagVisited(Flag flag) {
		String status = "";

		if (!flags.isEmpty()){
			int currentFlagIndex = winningCombo.indexOf(flag.getID());
			int visitedFlagID = flag.getID();
			int lastAddedFlagID = flags.get(flags.size()-1).getID();





			if (flags.contains(flag)){
				status = "You've already picked up this flag";
				return status;
			}
			
			if (flag.getID() == winningCombo.get(currentFlagIndex)){

			}

		} else {
			if (flag.getID() == winningCombo.get(0)){
				status = "First flag has been picked up";
				flags.add(flag);
			} else {
				status = "This is the %s, not flag1"; //Kan legge til formattering etter hvert.
			}
		}

		return status;
	}



    public void rotate(int degree) {
	    direction = (direction + degree) % 4;
    }

	public Color getColor() {
		return color;
	}
}
