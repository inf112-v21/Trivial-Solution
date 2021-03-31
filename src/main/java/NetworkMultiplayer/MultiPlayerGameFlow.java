package NetworkMultiplayer;

import NetworkMultiplayer.Messages.ConfirmationMessages;
import com.esotericsoftware.kryonet.Server;

import java.net.InetAddress;

public class MultiPlayerGameFlow {

     public void  serverStart(){

         NetworkServer server = new NetworkServer();


    }

    public void clientStart(){
         NetworkClient client = new NetworkClient();

         //Finner Ip-addressen til hosten.
        InetAddress hostIpadress = client.findServer();

        //Connect to client
        client.connect(hostIpadress.toString());

        client.sendToServer(ConfirmationMessages.CONNECTION_WAS_SUCCESSFUL);

    }
}
