package NetworkMultiplayer;


import NetworkMultiplayer.Messages.Message;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.net.InetAddress;

public class NetworkClient {

    public Client client;

    final static int DEFAULT_UDP_PORT = 54777;
    final static int DEFAULT_TCP_PORT = 54555;


    //Roboter startes inne i networkclient og networkserver.
    //private Robot = new Robot();


    public NetworkClient() {
        client = new Client();

        //start klienten
        client.start();

        //Registrer klienten i nettverket
        LanNetwork.register(client);


    }


    /**
     * Vi må konnektere oss til serveren etterhvert. TCP fikser dette med en handshake runde først.
     * Den etablerer konneksjonen før den faktiskt sender packer frem og tilbake
     * @param ipAdress
     * @return
     */
    public boolean connect(String ipAdress) {
        boolean connectionEstablished = true;
        try {
            //connecter til hosten
            client.connect(4500, ipAdress, DEFAULT_TCP_PORT, DEFAULT_UDP_PORT);

        } catch (IllegalStateException e) {
            System.out.println(e.toString() + ": Connect ble kalt fra konneksjonens update thread");
            connectionEstablished = false;
        }
        catch (IOException e) {
            System.out.println(e.toString() + ": klienten kunne ikke opprette en konneksjon eller så gikk tiden ut");
            connectionEstablished = false;
        }
        return connectionEstablished;
    }


    /**
     * Sjekk om en client er connected til en server
     * @return true hvis clienten er connected, false ellers
     */
    public boolean isConnected() {
        return client.isConnected();
    }


    /**
     * Sender en UDP melding over LAN'et for å finne alle kjørende servere.
     * Den innebygde metoden i kryonet bruker UDP porten til serveren for å finne den
     *
     * @return ip-adressen til serveren, hvis tiden har gått ut returnerer den null.
     *
     */
    public InetAddress findServer(){
        return client.discoverHost(DEFAULT_UDP_PORT,5000);
    }


    /**
     * Sender meldinger fra klienten til serveren
     * @param m meldingen vi sender
     */
    public void sendToServer(Message m){
        client.sendTCP(m);
    }

}
