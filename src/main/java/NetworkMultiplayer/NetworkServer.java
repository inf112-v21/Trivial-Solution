package NetworkMultiplayer;


import GUIMain.GUI;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.MinorErrorMessage;
import NetworkMultiplayer.Messages.ConfirmationMessages;
import NetworkMultiplayer.Messages.InGameMessages.ChosenCards;
import NetworkMultiplayer.Messages.IMessage;
import NetworkMultiplayer.Messages.PreGameMessages.RobotInfo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;


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

    //Mappinger som sjekker at vi har alt på plass
    private HashMap<Connection,IMessage> connectedClients = new HashMap<>();
    private HashMap<Connection, Robot> clientRobots = new HashMap<>();

    //Brukes for å opprette design og navn
    private HashSet<Robot> robots = new HashSet<>();


    //Det vi sender til klientene for at de skal kunne simulere trekken
    //TODO: Lag en set metode?
    private HashMap<Robot,IMessage> robotActions = new HashMap<>();

    //Portene som data sendes imellom. Valgfrie porter kan velges.
    final static int DEFAULT_UDP_PORT = 54777;
    final static int DEFAULT_TCP_PORT = 54555;

    /**
     * @return - HashMap med robotene mappet til hvilke kort de valgte
     */
    public HashMap<Robot, IMessage> getRobotActions(HashMap<Robot, IMessage> robotActions) {
        return robotActions;
    }

    /**
     * @return konneksjonen med dens tilhørende melding
     */
    public HashMap<Connection, IMessage> getConnectedClients() {
        return connectedClients;
    }

    /**
     *
     * @return konneksjonen med dens tilhørende robot.
     */
    public HashMap<Connection, Robot> getClientRobots() {
        return clientRobots;
    }

    /**
     * Starter game-hosten vår aka. serveren i nettverket. Bør kalles når spillet starter
     */
    public NetworkServer(){

        server = new Server();

        //Starter en ny tråd som gjør det mulig å sende og motta informasjon fra et nettverk
        new Thread(server).start();

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
                    switch (message){
                        case CONNECTION_WAS_SUCCESSFUL:
                            System.out.println("Woho!");
                    }

                }

                //Her sjekker vi hva som skal skje med kortene hvis vi finner noen.
                else if (object instanceof ChosenCards){
                    ChosenCards cards = (ChosenCards) object;
                    connectedClients.put(connection,cards);

                    //Gjør dette for å lagre kortene klienten sendte
                    //Nå er de lagret i robotActions som vi kan sende med en gang
                    //over nettverket.
                    Robot thisRobotsAction = clientRobots.get(connection);
                    robotActions.put(thisRobotsAction,cards);

                }


                //Her sjekker vi om roboten blir registrert riktig
                //I spillet vår har vi bestemt det at kun en robot kan ha en type design
                //Send melding på hvilken designs som er tilgjengelige?
                else if (object instanceof RobotInfo){
                    RobotInfo bot = (RobotInfo) object;
                    String newRobotName = bot.getBotName();
                    int chosenDesign = bot.getBotDesignNr();

                    for(Robot registeredBot: robots){
                        if (registeredBot.getDesign() == chosenDesign){
                            sendToClient(connection, MinorErrorMessage.UNAVAILABLE_DESIGN);
                        }
                        if (registeredBot.getName().equals(newRobotName)){
                            sendToClient(connection, MinorErrorMessage.UNAVAILABLE_NAME);
                        }

                    }
                    //Hvis alt er greit så legger vi til roboten.
                    Robot newBot = new Robot(newRobotName,chosenDesign,false);
                    clientRobots.put(connection,newBot);

                }



            }
            //Kalles når vi oppretter en konneksjon
            //Da registrerer vi den konneksjonen og Roboten som tilhører konneksjonen.
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
     *Sender en melding fra serveren til en klient
     *
     * @param con - konneksjonen (representerer klienten som skal få meldingen)
     * @param m - meldingen vi vil sende
     */
    public void sendToClient(Connection con, IMessage m){
        server.sendToTCP(con.getID(),m);
    }




    /**
     * Sender data til alle klientene via TCP
     */
    public void sendMessageToAllClients(IMessage m){
            server.sendToAllTCP(m);
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
