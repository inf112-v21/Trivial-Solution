package NetworkMultiplayer;

import GameBoard.Robot;
import NetworkMultiplayer.Messages.InGameMessages.ChosenRobot;
import NetworkMultiplayer.Messages.InGameMessages.SanityCheck;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import NetworkMultiplayer.Messages.PreGameMessages.SetupRobotNameDesignMessage;
import NetworkMultiplayer.Messages.ConfirmationMessages;
import NetworkMultiplayer.Messages.InGameMessages.ChosenCards;
import NetworkMultiplayer.Messages.IMessage;
import NetworkMultiplayer.Messages.PreGameMessages.RobotInfo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


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
    private HashMap<Connection, Robot> connectionsAndRobots = new HashMap<>();

    //Valgene de ulike klientene/robotenes tar.
    private TreeMap<Robot,IMessage> robotActions = new TreeMap<>();

    private HashMap<Connection,Pair<String,Integer>> connectionAndNameDesign= new HashMap<>();

    private String hostName;
    private int hostDesign;

    public void setHostDesign(int hostDesign) {
        this.hostDesign = hostDesign;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    private Robot hostRobot;



    //Portene som data sendes imellom. Valgfrie porter kan velges.
    final static int DEFAULT_UDP_PORT = 54777;
    final static int DEFAULT_TCP_PORT = 54555;

    //Brukes for å sjekke at alt er riktig
    SanityCheck checkAllIsRight;

    //Gi serveren beskjed om å dele ut kort
    boolean simulationIsOver;





    /**
     * @return - HashMap med robotene mappet til hvilke kort de valgte
     */
    public TreeMap<Robot, IMessage> getRobotActions() {
        return robotActions;
    }


    /**
     *
     * @return konneksjonen med dens tilhørende robot.
     */
    public HashMap<Connection, Robot> getConnectionsAndRobots() {
        return connectionsAndRobots;
    }


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
            public void received(Connection connection, Object object) {
                System.out.println("Received " + object.getClass());

                if (object instanceof ConfirmationMessages) {
                    ConfirmationMessages message = ((ConfirmationMessages) object);
                    switch (message) {
                        case CONNECTION_WAS_SUCCESSFUL:
                            System.out.println("Woho!");

                        case SIMULATION_IS_OVER:
                            simulationIsOver = true;
                    }

                }

                //Her sjekker vi hva som skal skje med kortene hvis vi finner noen.
                else if (object instanceof ChosenCards) {
                    ChosenCards cards = (ChosenCards) object;

                    //Gjør dette for å lagre kortene klienten sendte
                    //Nå er de lagret i robotActions som vi kan sende med en gang
                    //over nettverket.
                    Robot thisRobotsAction = connectionsAndRobots.get(connection);
                    robotActions.put(thisRobotsAction, cards);

                }


                //Her sjekker vi om roboten blir registrert riktig
                //I spillet vår har vi bestemt det at kun en robot kan ha en type design
                //Send melding på hvilken designs som er tilgjengelige?
                else if (object instanceof RobotInfo) {
                    RobotInfo bot = (RobotInfo) object;
                    String newRobotName = bot.getBotName();
                    int chosenDesign = bot.getBotDesignNr();

                    if (robotActions.keySet().stream().map(Robot::getDesign).collect(Collectors.toList()).contains(chosenDesign)){
                        sendToClient(connection, SetupRobotNameDesignMessage.UNAVAILABLE_DESIGN);
                        return;
                    }
                    if (robotActions.keySet().stream().map(Robot::getName).collect(Collectors.toList()).contains(newRobotName)){
                        sendToClient(connection, SetupRobotNameDesignMessage.UNAVAILABLE_NAME);
                        return;
                    }
                    Robot newbot = new Robot(newRobotName, chosenDesign, false);
                    connectionsAndRobots.put(connection, newbot);
                    robotActions.put(newbot, null);
                    sendToClient(connection, SetupRobotNameDesignMessage.ROBOT_DESIGN_AND_NAME_ARE_OKEY);
                }

                if (object instanceof ChosenRobot){
                    Robot newBot = ((ChosenRobot) object).getRobot();
                    connectionsAndRobots.put(connection, newBot);
                    robotActions.put(newBot, null);

                }

                //Test (sanity check)
                if (object instanceof SanityCheck) {
                    checkAllIsRight = (SanityCheck) object;
                }
            }

            //Kalles når vi oppretter en konneksjon med en klient
            public void connected(Connection connection) {
                System.out.println("Server connected to client " + connection.getID());
                numberOfConnections++;
                System.out.println(connection);

                //Vi registrer kommunikasjonslinken (connection). Robot opprettes senere da.
                connectionsAndRobots.put(connection, null);

            }
        });
    }

    public SetupRobotNameDesignMessage setHostRobot(RobotInfo info){
        if (robotActions.keySet().stream().map(Robot::getDesign).collect(Collectors.toList()).contains(info.getBotDesignNr())) return SetupRobotNameDesignMessage.UNAVAILABLE_DESIGN;
        if (robotActions.keySet().stream().map(Robot::getName  ).collect(Collectors.toList()).contains(info.getBotName()    )) return SetupRobotNameDesignMessage.UNAVAILABLE_NAME;
        hostRobot = new Robot(info.getBotName(), info.getBotDesignNr(), false);
        robotActions.put(hostRobot, null);
        return SetupRobotNameDesignMessage.ROBOT_DESIGN_AND_NAME_ARE_OKEY;
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

     /**
     * WIP
     * Starter opp spillet for alle.
     * @param mapname
     */
    public GameInfo startTheGame(String mapname){
        List<Robot> robots = Collections.unmodifiableList(new ArrayList<>(robotActions.keySet()));
        for (Connection con : connectionsAndRobots.keySet()){
            sendToClient(con, new GameInfo(robots, mapname, robots.indexOf(connectionsAndRobots.get(con))));
        }
        return new GameInfo(robots, mapname, robots.indexOf(hostRobot));
    }


}
