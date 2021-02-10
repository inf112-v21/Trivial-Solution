package Objects;

import com.badlogic.gdx.graphics.Color;
import Cards.ICard;
/**
 * 
 * @author ilyasali
 *
 */
public class Robot{
	
	private int lives = 3;
	private int damage = 0;
	private String name;
	private int flags;
	private Color color;
	public int direction = 0;
	
	
	Robot(String name, Color color){
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
	 * @return the damages on this robot
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * apply damage to this robot
	 */
	public void applyDamage() {
		damage += 1;
		if(damage == 9) {
			lives -=1;
			damage = 0;
		}
		System.out.println(toString());
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
		return getName() +" has "+ getRemainingLives() + " "
				+ "lives and has " +getDamage()+" damage.";
	}
	
	/**
	 * 
	 * @return true if robot won, e.g. visited all 3 flags, false otherwise 
	 */
	public boolean hasWon() {
		return flags==3;
	}
	
	/**
	 * Should only be called when the robot visited a flag
	 */
	public void flagVisited() {
		flags ++;
	}
	
	
	
}
