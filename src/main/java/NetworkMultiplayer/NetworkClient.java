package NetworkMultiplayer;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Client;

import javax.swing.*;

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
     * @param ip
     * @return
     */
    public boolean connect(String ip) {
        try {
            //Vi trenger nokk en update metode til etterpå
            client.connect(1500, ip, tcpPort, udpPort);
            System.out.println("IP Address: "+ ip);
            return true;
        } catch (Exception e) {
            //Send en errormelding?
            return false;
        }
    }

    /**
     * Sjekk om en client er connected til en server
     * @return
     */
    public boolean isConnected() {
        return client.isConnected();
    }

}
