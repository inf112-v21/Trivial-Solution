package NetworkMultiplayer;


import GameBoard.Cards.ProgramCard;
import GameBoard.Components.Flag;
import GameBoard.Position;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.ClientDisconnected;
import NetworkMultiplayer.Messages.IMessage;
import NetworkMultiplayer.Messages.InGameMessages.*;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import NetworkMultiplayer.Messages.PreGameMessages.RobotInfo;
import NetworkMultiplayer.Messages.PreGameMessages.SetupRobotNameDesign;
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
        kryo.register(ChosenCards.class, new JavaSerializer());
        kryo.register(DistributedCards.class, new JavaSerializer());
        kryo.register(GameInfo.class, new JavaSerializer());
        kryo.register(IMessage.class);
        kryo.register(RobotInfo.class, new JavaSerializer());
        kryo.register(ConfirmationMessage.class);
        kryo.register(ProgramCard.class, new JavaSerializer());
        kryo.register(AllChosenCardsFromAllRobots.class, new JavaSerializer());
        kryo.register(SetupRobotNameDesign.class);
        kryo.register(ClientDisconnected.class, new JavaSerializer());
        kryo.register(String.class, new JavaSerializer());
        kryo.register(Position.class, new JavaSerializer());
        kryo.register(Flag.class,new JavaSerializer());
        kryo.register(SanityCheck.class, new JavaSerializer());
    }
}
