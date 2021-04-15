package GameBoard;

import GUIMain.Screens.GameScreen;
import GameBoard.Cards.ICard;
import GameBoard.Components.Flag;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Vi implementerer Serializable for at Kryonet skal kunne
 * gjøre Robotene om til bytes og sende de over nettverket.
 * Vi lager også et JavaSerializable objekt i LanNetwork
 * for å få det til å virke.
 * Vi kunne også lage en tom konstruktør her for å få
 * det til å virke.
 */

public class Robot implements Serializable, Comparable<Robot> {
    public static final int INITIAL_HP = 10;
    public static final int INITIAL_LIVES = 3;
    public static final int MAX_AVAILABLE_CARDS = 9;
    public static final int RESPAWN_HANDICAP = 2;
    public static final int TAU = 4;
    public static final int INITIAL_DIRECTION = 0;
    public static final int NUMBER_OF_DESIGNS = 8;

    private int lives = INITIAL_LIVES;
	private int hp = INITIAL_HP;

	private int design;
	private final String name;
	private TextureRegion image;
	private int direction = INITIAL_DIRECTION;
	private Position respawnPoint;
	private final boolean isControlledByAI;
	private boolean powerDown;

	private final ArrayList<Flag> flagsVisited = new ArrayList<>();
	private ArrayList<ICard> availableCards = new ArrayList<>(); //alle kortene som ble utdelt
	private final ArrayList<ICard> chosenCards = new ArrayList<>(BoardController.PHASES_PER_ROUND); //De valgte kortene, rekkefølgen er samme som den spilleren valgte dem



	public Robot(String name, int design, boolean isControlledByAI){
	    this.design = design;
		this.name = name;
		this.isControlledByAI = isControlledByAI;
		powerDown = false;
		try{
		setImage();
		} catch (Exception e){
			System.out.println("Something went wrong" + e);
		}
	}

	private void setImage() {
		image = new TextureRegion(new Texture("Robotdesigns/robots.png")).split(GameScreen.CELL_SIZE, GameScreen.CELL_SIZE)[0][design];
	}

	public Robot(String name, boolean isControlledByAI){
        this.name = name;
        this.isControlledByAI = isControlledByAI;
        powerDown = false;
    }

	public int getLives() {
		return lives;
	}
	public int getHP() {
		return hp;
	}

	public int getDesign() {
		return design;
	}

	public void repairRobot(int repairPoints){hp = Math.min(INITIAL_HP, hp + repairPoints); }
	public void applyDamage(int dmg) { hp -= dmg; }
	public void takeLife(){ lives--; }

	public boolean isDestroyed() {
		return hp < 1;
	}
	public boolean hasRemainingLives(){ return lives > 0; }

    public void respawn(){ hp = INITIAL_HP - RESPAWN_HANDICAP; }

	public int getDirection(){ return direction; }
	public void setDirection(int dir){
        if ( dir < TAU && dir >= 0) direction = dir;
        else throw new IllegalArgumentException("Direction has to be 0 <= dir < " + TAU + ", but was " + dir);
    }

	@Override 
	public String toString() {
		return "Name: " + name
            +  "\nLives: " + lives
            +  "\nHP: " + hp
            +  "\nDirection: " + direction;
	}

	/** Listen over flaggene som roboten har besøkt. */
	public ArrayList<Flag> getVisitedFlags(){ return flagsVisited;}

	public Position getRespawnPoint(){
	    if(respawnPoint == null) throw new NullPointerException("This robot has no spawnpoint, " +
                "make sure you spawn it with board.spawnRobot() and not board.placeRobot() " +
                "if you want it to have a spawnpoint.");
	    return respawnPoint;
	}
	public void setRespawnPoint(Position pos){ respawnPoint = pos; }
	
	/** Legger flagget til i listen over flagg roboten har passert. */
	public void addToFlagsVisited(Flag flag) { flagsVisited.add(flag);}

    /** Roterer roboten. degree=1 betyr 90 grader mot høyre, degree=2 betyr 180 grader, etc. */
    public void rotate(int degree) { direction = Math.floorMod(direction + degree, TAU); }

	public TextureRegion getImage() {
	    if (image == null) throw new IllegalStateException("This robot has no image. If it should have one, please use the other Robot constructor instead.");
		return image;
	}

	public boolean isControlledByAI(){return isControlledByAI; }

	public void resetState(){
	    resetAllCards();
	    hp = INITIAL_HP;
	    lives = INITIAL_LIVES;
	    direction = 0;
    }

    /** Setter listen over kort roboten kan velge mellom. */
	public void setAvailableCards(ArrayList<ICard> cards){
	    if (cards.size() > getAvailableCardSlots()) throw new IllegalArgumentException("Cannot hold more than " + getAvailableCardSlots() + " atm, but was given " + cards.size());
	    availableCards = new ArrayList<>(cards);
	}

	public void setChosenCards(ArrayList<ICard> chosen){
	    if ( ! chosenCards.isEmpty()) throw new IllegalStateException("My list of chosen cards are non-empty, and yet I got a new set of cards");
	    chosenCards.addAll(chosen);
	}

    /** Valgt kort nummer n. */
    public ICard getNthChosenCard(int n){
        if (n >= chosenCards.size()) return null;
        return chosenCards.get(n);
    }

	public void resetAllCards(){ availableCards.clear(); chosenCards.clear(); }
	public void resetChosenCards(){ chosenCards.clear(); }

    /** En kopi av listen over tilgjengelige kort. */
	public ArrayList<ICard> getAvailableCards(){ return new ArrayList<>(availableCards); }

	/**
	 * Denne metoden legger til et og et kort i rekkefølge i registeret utifra hva spilleren velger.
	 * @param chosenCard kortet som ble valgt
     * @return true om kortet ble satt, false ellers
	 */
	public boolean chooseCard(ICard chosenCard){
	    if (! availableCards.contains(chosenCard)) throw new IllegalArgumentException("This card isn't in the list of available cards for som reason");
	    if (chosenCards.size() >= getChosenCardSlots()) throw new IllegalStateException("I already have the maximum number of cards!");
		chosenCards.add(chosenCard);
		return true;
	}

	public void unchooseCard(ICard unchosenCard){
	    if (! chosenCards.contains(unchosenCard)) throw new IllegalArgumentException("This card wasn't chosen, so I cannot unchoose it.");
	    chosenCards.remove(unchosenCard); }

	public int getNumberOfChosenCards(){ return chosenCards.size(); }

	public boolean isPowerDownAnnounced(){ return powerDown; }
	public void togglePowerDown(){ powerDown = !powerDown; }

    public String getName() { return name; }

    public int getAvailableCardSlots(){ return Math.min(hp, MAX_AVAILABLE_CARDS); }
    public int getChosenCardSlots(){ return Math.min(hp, BoardController.PHASES_PER_ROUND); }

    public static ArrayList<Robot> getDefaultRobots(int numberOfRobots, int playersDesign){
        if (numberOfRobots < 0 || numberOfRobots > 8) throw new IllegalArgumentException("Expecteded >0 and <9 robots, but was " + numberOfRobots);
        //Denne her kunne vært en statisk feltvariabel, men da kan vi ikke kjøre tester som bruker roboter uten at denne blir ærklert,
        // og om GUI-en ikke har startet opp ennå får vi da feil når vi laster inn bildene.
        final Robot[] defaultRobots = {
                new Robot("Nebuchadnezzar", 0, true),
                new Robot("Alexstrasza", 1, true),
                new Robot("Gilgamesh", 2, true),
                new Robot("Ashurbarnipal", 7, true),
                new Robot("Andromeda", 4, true),
                new Robot("Hephaistion", 5, true),
                new Robot("Styxifus", 6, true),
                new Robot("Prometheus", 3, true)
        };
        List<Robot> ret = Arrays.stream(defaultRobots).filter(i -> i.getDesign() != playersDesign).collect(Collectors.toList());
        Collections.shuffle(ret);
        for (int i = 0; i < numberOfRobots; i++) defaultRobots[i].resetState();

        return new ArrayList<>(ret.subList(0, numberOfRobots));
    }


    public Robot copy(){
        Robot copy = new Robot(name, design, isControlledByAI);
        copy.lives = lives;
        copy.hp = hp;
        copy.direction = direction;
        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Robot){
            Robot o = (Robot) obj;
            return this.name.equals(o.name) && this.hp == o.hp && this.lives == o.lives && this.design == o.design && this.direction == o.direction;
        }
        return false;
    }

    @Override
    public int compareTo(Robot o) {
        if ( ! this.name.equals(o.name)) return this.name.compareTo(o.name);
        return 0;
    }
}
