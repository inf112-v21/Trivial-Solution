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

    //metode for å "cleare" alle kortene i registerne for en nye runde

}
