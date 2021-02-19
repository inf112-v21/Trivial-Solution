package Player;

import Cards.Deck;
import Cards.ProgramCard;
import Cards.ICard;

import java.util.ArrayList;

/*
* This class gets 9 cards from the Game-class, and needs to show these to the player. The player should then be able
* to pick 5 of these cards, and put them in their own register.
 */
public class Register {

    private ArrayList<ICard> registerCards = new ArrayList<ICard>();
    private Integer lifeTokens;
    private Integer damageTokens;
    private Boolean powerDown;

    //Constructor
    public Register(Integer numberOfLifeTokens, Integer numberOfDamageTokens, Boolean initializePowerDown){
        lifeTokens = numberOfLifeTokens;
        damageTokens = numberOfDamageTokens;
        powerDown = initializePowerDown;
    }

    /**
    * Denne metoden "setter" de 9 kortene som registerer får inn.
     */
    public void setRegisterCards(ArrayList<ICard> cards){
        registerCards = cards;
    }

    /**
    * Denne metoden returnerer de 9 kortene som registeret holder.
     */
    public ArrayList<ICard> getRegisterCards(){
        return registerCards;
    }

    /**
     * Metode for å "cleare" alle kortene i registerne før en nye runde starter.
     */
    public void clearAllRegisterCards(){
        registerCards.clear();
    }

    //TODO
    /**
     * Returnerer antallet "life tokens" som registeret har.
     * @return lifeTokens
     */
    public Integer getLifeTokens(){
        return lifeTokens;
    }

    //TODO
    /**
     * Returnerer antallet "damage tokens" som registeret inneholder.
     * @return damageTokens
     */
    public Integer getDamageTokens(){
        return damageTokens;
    }

    /**
     * Returnerer "true" hvis roboten har en "powerdown", og "false" hvis den ikke har det.
     * @return Tilstanden til roboten
     */
    public Boolean isPowerDownAnnounced(){
        return powerDown;
    }

}
