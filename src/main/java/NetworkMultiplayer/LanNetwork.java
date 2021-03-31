package NetworkMultiplayer;


import NetworkMultiplayer.Messages.*;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

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
        kryo.register(ChosenCards.class);
        kryo.register(DistributedCards.class);
        kryo.register(GameInfo.class);
        kryo.register(IMessage.class);
        kryo.register(Name.class);
        kryo.register(OtherRobotChoices.class);
        kryo.register(ServerError.class);

    }
}
