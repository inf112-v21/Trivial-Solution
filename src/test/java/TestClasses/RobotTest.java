package TestClasses;

import AIs.Randbot;
import GameBoard.Cards.Deck;
import GameBoard.Cards.ICard;
import GameBoard.Cards.ProgramCard;
import GameBoard.Robot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RobotTest {

    private Robot bot;
    private Deck deck;

    @BeforeEach
    public void reset(){
        bot = new Robot("Nebuchadnezzar", false);
        deck = new Deck(false);
    }

    @Test
    public void robotCrashesWhenGivenMoreCardsThanItCanHold(){
        ArrayList<ICard> cards = new ArrayList<>();
        for (int i = 0; i < Robot.MAX_AVAILABLE_CARDS + 1; i++) {
            cards.add(deck.drawCard());
        }
        try{
            bot.setAvailableCards(cards);
            fail();
        }catch (IllegalArgumentException e){
            //Yay it worked
        }
    }

    @Test
    public void robotCrashesWhenChoosingMoreCardsThanPossible(){
        setAvailableCards();
        for (int i = 0; i < bot.getChosenCardSlots(); i++) bot.chooseCard(bot.getAvailableCards().get(i));

        try{
            bot.chooseCard(bot.getAvailableCards().get(bot.getChosenCardSlots() + 1));
            fail();
        }catch (IllegalStateException e){
            //Yay
        }
    }

    @Test
    public void canOnlyChooseAvailableCards(){
        try{
            bot.chooseCard(new ProgramCard(69, 0, 420, null)); //Dette kortet er antagelig ikke i listen
        }catch (IllegalArgumentException e){
            //Yay
        }
    }

    @Test
    public void respawningResetsHPWithHandiCap(){
        bot.applyDamage(1);
        bot.respawn();

        assertEquals(Robot.INITIAL_HP - Robot.RESPAWN_HANDICAP, bot.getHP());
    }

    private void setAvailableCards(){
        ArrayList<ICard> cards = new ArrayList<>();
        for (int i = 0; i < Robot.MAX_AVAILABLE_CARDS; i++) cards.add(deck.drawCard());
        bot.setAvailableCards(cards);
    }

    @Test
    public void cannotHealRobotMoreThanMaxHP(){
        bot.applyDamage(2);

        bot.repairRobot(9001);

        assertEquals(Robot.INITIAL_HP, bot.getHP());
    }

}
