package NetworkMultiplayer;

import GameBoard.Cards.Deck;
import NetworkMultiplayer.Messages.Message;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class LanNetwork {

    //Serveren og klienten som utgjør denne konneksjonen. Vi må ha en
    //konneksjon per klient.
    NetworkServer server;
    NetworkClient client;





    /**
     * Vi trenger en metode som registrer klienter over nettverket
     * @param device- Kan være server eller klient
     */
    public static void register(EndPoint device) {
        Kryo kryo = device.getKryo();
        kryo.register(Deck.class);
        kryo.register(Message.class);
    }
}
