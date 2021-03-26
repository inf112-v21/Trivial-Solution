package NetworkMultiplayer;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

//Kortene er i deck
//Hva må jeg sende:
// 1. spiller posisjonene frem og tilbake
// 2. kort fra server til client (kan slettes etterpå, går det an)
// 3.

public class NetworkServer {

    //Selve serveren
    private Server gameHost;

    //Portene som data sendes imellom. Disse er beskrevet som standard på kryonet nettsiden
    //Kanskje vi skal sette de som custom et annet sted i koden.
    //Metoder for det er definert lengre ned i tilfelle
    final static int udpPort = 54777;
    final static int tcpPort = 54555;


    /**
     * Starter game-hosten vår aka. serveren i nettverket. Bør kalles når spillet starter
     */
    NetworkServer(){
        gameHost = new Server();

        //Starter nettverk tråden som gjør det mulig å få informasjon fra et nettverk, også sende
        gameHost.start();
    }

    /**
     * Metode som skaper listeners
     * Serveren har også en slik metode. Vi må kanskje customize denne
     */
    private void addListeners() {
        //Lage denne eller bruke kryonet sine listeners? Må researche litt til på det.
    }


    /**
     * starter konneksjonen
     */
    private void connection(){}



    /**
     *  stenger nettverk konneksjonen og stopper nettverk tråden
     */
    public void stopServer(){ gameHost.stop();}


    /**
     *For avsluttning
     */
    public void deleteServer() throws IOException { gameHost.dispose();}


    /**
     * Lage custom konneksjoner
     * @param tcp
     * @param udp
     * @throws IOException
     */
    public void setPort(int tcp, int udp) throws IOException {
        gameHost.bind(tcp, udp);
    }



}
