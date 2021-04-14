package NetworkMultiplayer;


import GameBoard.Cards.ICard;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.*;
import NetworkMultiplayer.Messages.InGameMessages.AllChosenCardsFromAllRobots;
import NetworkMultiplayer.Messages.InGameMessages.ChosenCards;
import NetworkMultiplayer.Messages.InGameMessages.DistributedCards;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import NetworkMultiplayer.Messages.PreGameMessages.RobotInfo;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryonet.EndPoint;

import java.util.ArrayList;
import java.util.List;

public class LanNetwork {


    /**
     * Gjør serveren og klienten beviste på hva de skal motta
     * og sende via nettverket.
     *
     * Klassene/Packene/Meldingene som skal sendes over nettverket finner du her.
     * Kort forklart så serializes og desirializes klassene/meldingene
     * takk vare kryo.register() metoden. Dette er det som gjør det
     * mulig for oss å sende de via nettverket.
     *
     * @param device- (Server eller klient)
     */
    public static void register(EndPoint device) {
        Kryo kryo = device.getKryo();
        kryo.register(ArrayList.class);
        kryo.register(List.class);
        kryo.register(Robot.class, new JavaSerializer());
        kryo.register(ChosenCards.class);
        kryo.register(DistributedCards.class);
        kryo.register(GameInfo.class, new JavaSerializer());
        kryo.register(IMessage.class);
        kryo.register(RobotInfo.class, new JavaSerializer());
        kryo.register(ConfirmationMessages.class);
        kryo.register(ICard.class, new JavaSerializer());
        kryo.register(AllChosenCardsFromAllRobots.class, new JavaSerializer());


    }
}
