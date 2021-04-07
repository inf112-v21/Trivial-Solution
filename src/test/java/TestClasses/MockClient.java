package TestClasses;

import NetworkMultiplayer.Messages.ConfirmationMessages;
import NetworkMultiplayer.NetworkClient;

import java.net.InetAddress;

public class MockClient {


    /**
     *Kjør main for å teste om server og klient kan kjøres sammen.
     * MockServer må startes først da.
     */
    public static void main(String[] args) {
        NetworkClient client = new NetworkClient();

        //Finner Ip-addressen til hosten.
        InetAddress hostIpadress = client.findServer();



        //Connect to client
        if(client.connect(hostIpadress.getHostName())){
            System.out.println("Connection is established");
        }

    }
}

class MockClient2{
    public static void main(String[] args) {
        NetworkClient client = new NetworkClient();

        //Finner Ip-addressen til hosten.
        InetAddress hostIpadress = client.findServer();



        //Connect to client
        if(client.connect(hostIpadress.getHostName())){
            System.out.println("Connection is established");
            client.sendToServer(ConfirmationMessages.TEST_MESSAGE);
        }
    }
}
