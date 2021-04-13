package TestClasses;

import NetworkMultiplayer.Messages.ConfirmationMessages;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import NetworkMultiplayer.NetworkClient;

import java.net.InetAddress;

public class MockClient {


    /**
     *Kjør main for å teste om server og klient kan kjøres sammen.
     * MockServer må startes først da.
     */
    public static void main(String[] args) {
        NetworkClient client = new NetworkClient(null);

        //Finner Ip-addressen til hosten.
        InetAddress hostIpadress = client.findServer();

        /*
        GameInfo game = new GameInfo()
        System.out.println("Robots are: " + game.getRobots());
        System.out.println("Map is: " + game.getMapName());
        System.out.println("Robots are: " + game.getRobots().get(0).getHP());


                    System.out.println("Received object");
                    GameInfo2 game = (GameInfo2) object;
                    System.out.println("Robots are: " + game.robots);
                    System.out.println("Map is: " + game.mapName);
                    System.out.println("Robots are: " + game.robots);
                    sendToServer(ConfirmationMessages.GAME_WAS_STARTED_AND_CLIENT_IS_READY_TO_RECEIVE_CARDS);
                    */



        //Connect to client
        if(client.connect(hostIpadress.getHostName())){
            System.out.println("Connection is established");
        }

    }
}

class MockClient2{
    public static void main(String[] args) {
        NetworkClient client = new NetworkClient(null);

        //Finner Ip-addressen til hosten.
        InetAddress hostIpadress = client.findServer();



        //Connect to client
        if(client.connect(hostIpadress.getHostName())){
            System.out.println("Connection is established");
            client.sendToServer(ConfirmationMessages.TEST_MESSAGE);
        }
    }
}
