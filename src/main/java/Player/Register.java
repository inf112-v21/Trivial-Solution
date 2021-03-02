package Player;

import Cards.ICard;

import java.util.ArrayList;

/*
* Denne klassen får inn 9 kort fra Game-klassen, og må vise disse 9 kortene til spilleren.
* Spilleren skal dermed plukke ut 5 kort av disse, og putte den i sitt eget register.
* @author sandersig
 */
public class Register {

    private ArrayList<ICard> allRegisterCards = new ArrayList<ICard>(); //alle 9 kortene som spilleren får utdelt
    private ArrayList<ICard> registerCards = new ArrayList<ICard>(5); //de 5 kortene som spilleren velger
    // Det første kortet i listen er kort nr.1 i registeret, og det siste kortet er kort nr.5.
    private boolean powerDown;
    private Robot bot;

    //Constructor
    public Register(Robot robot){
        powerDown = false;
        bot = robot;
    }

    /**
    * Denne metoden "setter" de 9 kortene som registerer får inn.
     */
    public void setRegisterCards(ArrayList<ICard> cards){
        allRegisterCards = cards;
    }

    /**
    * Denne metoden returnerer de 9 kortene som registeret holder.
     */
    public ArrayList<ICard> getRegisterCards(){
        return allRegisterCards;
    }

    /**
     * Denne metoden legger til et og et kort i rekkefølge i registeret utifra hva spilleren velger.
     * @param chosenCard
     */
    public void addCardToRegister(ICard chosenCard){
        registerCards.add(chosenCard);
    }

    public void removeCardFromRegister(ICard unchosenCard){ registerCards.remove(unchosenCard); }

    /**
     * @return returnerer maks 5 kort fra registeret, kan returnere færre dersom roboten har mye damage.
     */
    public ArrayList<ICard> getMaxFiveCardsFromRegister(){
        return registerCards;
    }

    /**
     * Metode for å "cleare" alle kortene i registerne før en nye runde starter.
     */
    public void clearAllRegisterCards(){
        allRegisterCards.clear();
    }

    /**
     * Returnerer antallet "life tokens" som registeret har.
     * @return lifeTokens
     */
    public int getLifeTokens(){
        return bot.getRemainingLives();
    }

    /**
     * Returnerer antallet "damage tokens" som registeret inneholder.
     * @return damageTokens
     */
    public int getDamageTokens(){
        return bot.getHP();
    }

    /**
     * Returnerer "true" hvis roboten har en "powerdown", og "false" hvis den ikke har det.
     * @return Tilstanden til roboten
     */
    public Boolean isPowerDownAnnounced(){
        return powerDown;
    }

    /**
     * Denne metoden endrer powerDown til true dersom knappen for powerDown blir trykket på i GUI-en for registeret.
     */
    public void powerDownRobot(){
        powerDown = true;
    }
}
