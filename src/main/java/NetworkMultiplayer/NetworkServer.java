package NetworkMultiplayer;


import GameBoard.Robot;
import NetworkMultiplayer.Messages.ConfirmationMessages;
import NetworkMultiplayer.Messages.InGameMessages.ChosenCards;
import NetworkMultiplayer.Messages.IMessage;
import NetworkMultiplayer.Messages.PreGameMessages.RobotInfo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashMap;


//Kortene er i deck
//Hva må jeg sende:
// 1. spiller posisjonene frem og tilbake
// 2. kort fra server til client (kan slettes etterpå, går det an)
// 3.

public class NetworkServer extends Listener {

    //Selve serveren
    final private Server server;

    //antall tilkoblinger
    private int numberOfConnections = 0;
    private HashMap<Connection,IMessage> connectedClients = new HashMap<>();
    private HashMap<Connection, Robot> clientRobots = new HashMap<>();

    //Portene som data sendes imellom. Valgfrie porter kan velges.
    final static int DEFAULT_UDP_PORT = 54777;
    final static int DEFAULT_TCP_PORT = 54555;


    /**
     * Starter game-hosten vår aka. serveren i nettverket. Bør kalles når spillet starter
     */
    public NetworkServer(){
        server = new Server();

        //Starter en ny tråd som gjør det mulig å sende og motta informasjon fra et nettverk
        server.start();

        //Bind serveren til port
        bind();

        addListeners();

        //Registrer serveren i nettverket
        LanNetwork.register(server);



    }

    /**
     * Binder serveren til UDP og TCP port slik at vi veit hvilken
     * applikasjon TCP skal sende daten vår til.
     *
     * (En socket er interfacet mellom nettverket og apllikasjonen).
     *
     * UDP porten brukes av klientene for å finne serveren.
     * Se discoverhostmetoden i NetworkClient
     *
     */
    private void bind() {
        try{
            server.bind(DEFAULT_TCP_PORT, DEFAULT_UDP_PORT);
        } catch (IOException e) {
            System.err.println("Noe gikk galt med binden. Prøv annen Port?");
        }
    }

    /**
     * Metode som skaper listeners
     * Serveren har også en slik metode. Vi må kanskje customize denne
     */
    private void addListeners() {
        server.addListener(new Listener() {

            //Kalles når vi mottar ting i nettverket
            public void received (Connection connection, Object object) {

                if(object instanceof ConfirmationMessages) {
                    ConfirmationMessages message = ((ConfirmationMessages) object);
                    //skriv confirmations messages here.

                } else if (object instanceof ChosenCards){
                    ChosenCards cards = (ChosenCards) object;
                    connectedClients.put(connection,cards);
                }

                else if (object instanceof RobotInfo){
                    RobotInfo ri = (RobotInfo) object;


                }


            }
            //Kalles når vi oppretter en konneksjon
            public void connected(Connection connection) {
                System.out.println("Server connected to client " + connection.getID());
                numberOfConnections++;

                if (!connectedClients.containsKey(connection)){
                    connectedClients.put(connection,ConfirmationMessages.CONNECTION_WAS_SUCCESSFUL);
                }
                if (!clientRobots.containsKey(connection)){
                    clientRobots.put(connection,null);
                }



            }
        });

    }


    /**
     * @return antall klienter serveren er connectet til. Brukes for
     * å sjekke at alle klientene har sendt en melding
     */
    public int getNumberOfConnections(){ return numberOfConnections;}




    /**
     * Sender data til alle klientene via TCP
     */
    public void sendMessageToAllClients(IMessage m){
        if (connectedClients.size()>1){
            server.sendToAllTCP(m);
        }else if (connectedClients.size() >0){
            //server.sendToTCP(connectedClients.get().getID(),m);
        }

    }


    /**
     *  stenger nettverk konneksjonen og stopper nettverk tråden
     */
    public void stopServer(){ server.stop();}


    /**
     *For avsluttning
     *
     *
     */
    public void deleteServer() throws IOException { server.dispose();}




}
