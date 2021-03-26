package NetworkMultiplayer;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Client;

import javax.swing.*;
import java.io.IOException;

public class NetworkClient {
    public Client client;

    final static int udpPort = 54777;
    final static int tcpPort = 54555;


    public NetworkClient() {
        client = new Client();

        //Registrer clientene
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
            //Vi trenger nokk en update metode etterpå også
            //connecter til hosten
            client.connect(4500, ipAdress, tcpPort, udpPort);

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

}
