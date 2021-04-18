package NetworkMultiplayer;

import GameBoard.Cards.ProgramCard;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.InGameMessages.*;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import NetworkMultiplayer.Messages.PreGameMessages.SetupRobotNameDesign;
import NetworkMultiplayer.Messages.IMessage;
import NetworkMultiplayer.Messages.PreGameMessages.RobotInfo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class NetworkServer extends Listener {


    //Selve serveren
    final private Server server;

    //antall tilkoblinger
    private int numberOfConnections = 0;

    //antall mottate kortset
    private int numberOfSetsOfCardsReceived = 0;

    //antall klienter som er klare til å begynne
    private int numberOfReadyClients = 0;

    //Mappinger som sjekker at vi har alt på plass
    private HashMap<Connection, Robot> connectionsAndRobots = new HashMap<>();

    //Valgene de ulike klientene/robotenes tar.
    private TreeMap<Robot,ArrayList<ProgramCard>> robotActions = new TreeMap<>();



    private Robot hostRobot;



    //Portene som data sendes imellom. Valgfrie porter kan velges.
    final static int DEFAULT_UDP_PORT = 54777;
    final static int DEFAULT_TCP_PORT = 54555;

    //Brukes for å sjekke at alt er riktig
    SanityCheck checkAllIsRight;

    /**
     * Metode som sjekker at alle klientene er klare til å begynne spillet.
     * Denne metoden setter deretter
     * @return- true hvis alle er klare til å starte spillet, false ellers.
     */
    public boolean areAllClientsReady(){
        return numberOfReadyClients == numberOfConnections;
    }

    public void distributeCards(){
        for(Connection con : connectionsAndRobots.keySet()){
            sendToClient(con, new DistributedCards(connectionsAndRobots.get(con).getAvailableCards()));
        }
        numberOfReadyClients = 0;
    }

    /**
     * Sjekker om alle klientene har sendt kortene sine
     * til serveren/hosten
     * @return true hvis alle har sendt, false ellers.
     */
    public boolean haveAllClientSentTheirChosenCards(){
        return numberOfSetsOfCardsReceived == numberOfConnections+1;

    }


    /**
     * Sender alle kortene alle valgte til hver klient
     * slik at de kan begynne simuleringen
     */
    public void sendAllChosenCardsToEveryone(){
        for(Connection con : connectionsAndRobots.keySet()){
            sendToClient(con, new AllChosenCardsFromAllRobots(robotActions));
        }
        numberOfSetsOfCardsReceived = 0;
    }


    /**
     * Brukes til å oppdattere kortene hostens robot valgte.
     */
    public void setHostsChosenCards(){
        robotActions.put(hostRobot,hostRobot.getChosenCards());
        numberOfSetsOfCardsReceived++;
    }



    /**
     * @return - HashMap med robotene mappet til hvilke kort de valgte
     */
    public TreeMap<Robot, ArrayList<ProgramCard>> getRobotActions() {
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
            public void received(Connection connection, Object object) {
                System.out.println("Received " + object.getClass());

                if (object instanceof ConfirmationMessage) {
                    ConfirmationMessage message = ((ConfirmationMessage) object);
                    switch (message) {
                        case CONNECTION_WAS_SUCCESSFUL:
                            System.out.println("Woho!");
                            return;


                        case GAME_WAS_STARTED_AND_CLIENT_IS_READY_TO_RECEIVE_CARDS:
                            numberOfReadyClients++;
                            return;
                    }

                }

                //Her sjekker vi hva som skal skje med kortene hvis vi finner noen.
                else if (object instanceof ChosenCards) {
                    ChosenCards cards = (ChosenCards) object;

                    //Gjør dette for å lagre kortene klienten sendte
                    //Nå er de lagret i robotActions som vi kan sende med en gang
                    //over nettverket.
                    Robot thisRobotsAction = connectionsAndRobots.get(connection);
                    robotActions.put(thisRobotsAction, cards.getChosenCards());
                    numberOfSetsOfCardsReceived++;
                    thisRobotsAction.setChosenCards(cards.getChosenCards());
                    if (cards.getChosenCards().isEmpty()) thisRobotsAction.setPowerDown(true);
                }


                //Her sjekker vi om roboten blir registrert riktig
                //I spillet vår har vi bestemt det at kun en robot kan ha en type design
                //Send melding på hvilken designs som er tilgjengelige?
                else if (object instanceof RobotInfo) {
                    RobotInfo bot = (RobotInfo) object;
                    String newRobotName = bot.getBotName();
                    int chosenDesign = bot.getBotDesignNr();


                    if(!robotActions.isEmpty()) {
                        for (Robot registeredBot : robotActions.keySet()) {

                            System.out.println(bot);
                            if (registeredBot.getDesign() == chosenDesign) {
                                sendToClient(connection, SetupRobotNameDesign.UNAVAILABLE_DESIGN);
                                return;
                            }
                            if (registeredBot.getName().equals(newRobotName)) {
                                sendToClient(connection, SetupRobotNameDesign.UNAVAILABLE_NAME);
                                return;
                            }

                        }
                    }

                    Robot newbot = new Robot(newRobotName, chosenDesign, false);
                    connectionsAndRobots.put(connection, newbot);
                    robotActions.put(newbot, null);
                    sendToClient(connection, SetupRobotNameDesign.ROBOT_DESIGN_AND_NAME_ARE_OKEY);
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

    public SetupRobotNameDesign setHostRobot(RobotInfo info){
        if (robotActions.keySet().stream().map(Robot::getDesign).collect(Collectors.toList()).contains(info.getBotDesignNr())) return SetupRobotNameDesign.UNAVAILABLE_DESIGN;
        if (robotActions.keySet().stream().map(Robot::getName  ).collect(Collectors.toList()).contains(info.getBotName()    )) return SetupRobotNameDesign.UNAVAILABLE_NAME;
        hostRobot = new Robot(info.getBotName(), info.getBotDesignNr(), false);
        robotActions.put(hostRobot, null);
        return SetupRobotNameDesign.ROBOT_DESIGN_AND_NAME_ARE_OKEY;
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
        System.out.println("Sent message " + m.getClass());
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
        List<Robot> robottttts = new ArrayList<>(robotActions.keySet());
        Collections.shuffle(robottttts);
        List<Robot> robots = Collections.unmodifiableList(robottttts);
        for (Connection con : connectionsAndRobots.keySet()){
            sendToClient(con, new GameInfo(robots, mapname, robots.indexOf(connectionsAndRobots.get(con))));
        }
        return new GameInfo(robots, mapname, robots.indexOf(hostRobot));
    }


}
