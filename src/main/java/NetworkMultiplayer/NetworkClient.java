package NetworkMultiplayer;

import GameBoard.Cards.ProgramCard;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.ClientDisconnected;
import NetworkMultiplayer.Messages.InGameMessages.AllChosenCardsFromAllRobots;
import NetworkMultiplayer.Messages.PreGameMessages.SetupRobotNameDesign;
import NetworkMultiplayer.Messages.InGameMessages.ConfirmationMessage;
import NetworkMultiplayer.Messages.InGameMessages.DistributedCards;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import NetworkMultiplayer.Messages.IMessage;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.TreeMap;

public class NetworkClient {

    private Client client = null;

    //Porter som meldinger blir sendt til
    private final static int DEFAULT_UDP_PORT = 54777;
    private final static int DEFAULT_TCP_PORT = 54555;

    //Pre-game meldinger
    private GameInfo setup;

    //In-game meldinger
    private ArrayList<ProgramCard> cardsToChoseFrom;
    private TreeMap<Robot,ArrayList<ProgramCard>> allChoseRobotCards;
    private ConfirmationMessage serverIsDown;

    //pre-game melding
    //Denne forteller oss om det går fint å sette opp en robot
    private SetupRobotNameDesign state;

    public Robot disconnectedRobot;




    public NetworkClient() {

        client = new Client();

        //start klienten --> åpner opp en tråd for at den skal kunne sende og motta meldinger over nettverket.
        new Thread(client).start();

        //Registrer klienten i nettverket
        LanNetwork.register(client);

        addListeners();

    }

    public ConfirmationMessage getServerIsDown() {
        if(serverIsDown == null) return null;
        ConfirmationMessage dummy = serverIsDown;
        serverIsDown = null;
        return dummy;
    }

    /**
     * @return - Roboten til klienten som ble disconnected. Denne roboten må fjernes
     * fra spillet
     */
    public Robot getDisconnectedRobot() {
        if(disconnectedRobot == null) return null;
        Robot reserve = disconnectedRobot;
        disconnectedRobot = null;
        return reserve;
    }

    /**
     * @return GameInfo som vi trenger for å starte opp spillet hos klienten
     * Bruk getMapName(), getRobots() eller getThisPlayersBotIndex() for å hente ut verdier
     */
    public GameInfo getSetup() {
        return setup;
    }



    /**
     * @return Henter robotene og kortene hver robot valgte
     */
    public TreeMap<Robot, ArrayList<ProgramCard>> getAllChosenCards() {
        if (allChoseRobotCards == null) return null;
        TreeMap<Robot, ArrayList<ProgramCard>> ret = new TreeMap<>(allChoseRobotCards);
        allChoseRobotCards = null;
        return ret;
    }

    /**
     * @return De utdelte kortene som roboten kan velge mellom
     */
    public ArrayList<ProgramCard> getCardsToChoseFrom() {

        if(cardsToChoseFrom == null) return null;
        ArrayList<ProgramCard> ret = new ArrayList<>(cardsToChoseFrom);
        cardsToChoseFrom = null;
        return ret;
    }

    /**
     * Her re-setter vi staten til null slik at vi kan sjekke om robot designen eller
     * navnet blir unike. State blir satt tilbake til en av SetupRobotNameDesign
     */
    public void resetState(){ state = null; }

    /**
     *
     * @return hvilken state design og navnet er i nå
     */
    public SetupRobotNameDesign getState() {
        return state;
    }


    /**
     * Metode som skaper listeners
     * Disse håndterer mottate packer/Messages som kommer fra
     * serveren til klienten vår
     */
    private void addListeners() {
        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {

                if(object instanceof SetupRobotNameDesign){
                    SetupRobotNameDesign message = ((SetupRobotNameDesign) object);
                    switch(message){
                        case UNAVAILABLE_DESIGN:
                            state = SetupRobotNameDesign.UNAVAILABLE_DESIGN;
                            return;
                        case UNAVAILABLE_NAME:
                            state = SetupRobotNameDesign.UNAVAILABLE_NAME;
                            return;
                        case ROBOT_DESIGN_AND_NAME_ARE_OKEY:
                            state = SetupRobotNameDesign.ROBOT_DESIGN_AND_NAME_ARE_OKEY;
                            return;
                    }
                }

                else if(object instanceof ConfirmationMessage){
                    ConfirmationMessage message = ((ConfirmationMessage) object);
                    switch(message){

                        //Brukes kun for testing.
                        case TEST_MESSAGE:
                            System.out.println("Client received message. All good!");
                            return;

                        case SERVER_CHOOSE_TO_DISCONNECTED:
                            serverIsDown = ConfirmationMessage.SERVER_CHOOSE_TO_DISCONNECTED;
                            return;
                    }

                }

                //Alle de valgte kortene fra hver robot
                else if(object instanceof AllChosenCardsFromAllRobots){
                    allChoseRobotCards = ((AllChosenCardsFromAllRobots) object).getAllDesicions();
                }

                //Kortene som klienten kan velge mellom
                else if(object instanceof DistributedCards){
                    cardsToChoseFrom =((DistributedCards) object).getChosenCards();
                }

                //Setter opp spillet hos klienten
                else if (object instanceof GameInfo){
                    setup = (GameInfo) object;
                }

                //Her sletter vi roboten som ble disconnected.
                else if (object instanceof ClientDisconnected){
                    disconnectedRobot = ((ClientDisconnected) object).getDisconnectedBot();
                }

                else{
                    System.out.println("Received unidenitified message: " + object.getClass());
                }


            }

            //Det som skjer når en konnkesjon blir etablert
            @Override
            public void connected(Connection connection){
                sendToServer(ConfirmationMessage.CONNECTION_WAS_SUCCESSFUL);
            }

            //Det som skjer når en klient disconnecter
            @Override
            public void disconnected(Connection connection) {
                System.out.println("Client was disconnected" + client.getID());
            }


        });

    }


    /**
     * Vi må konnektere oss til serveren etterhvert. TCP fikser dette med en handshake runde først.
     * Den etablerer konneksjonen før den faktiskt sender packer frem og tilbake
     * @param ipAdress - Ip-adressen til hosten som en streng
     * @return - true hvis konneksjonen ble etablert, false ellers.
     */
    public boolean connect(String ipAdress) {
        System.out.println(ipAdress);
        boolean connectionEstablished = true;
        try {
            //connecter til hosten
            client.connect(10000, ipAdress, DEFAULT_TCP_PORT, DEFAULT_UDP_PORT);

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


    /**
     * Sender en UDP melding over LAN'et for å finne alle kjørende servere.
     * Den innebygde metoden i kryonet bruker UDP porten til serveren for å finne den
     *
     * @return ip-adressen til serveren, hvis tiden har gått ut returnerer den null.
     *
     */
    public InetAddress findServer(){
        return client.discoverHost(DEFAULT_UDP_PORT,5000);
    }


    /**
     * Sender meldinger fra klienten til serveren
     * @param m meldingen vi sender
     */
    public void sendToServer(IMessage m){
        client.sendTCP(m);
    }

    /**
     * Disconnecter fra serveren og stopper network threaden.
     * Når networkthreaden er stoppet kan vi reconnecte på nytt etterpå.
     */
    public void disconnectAndStopClientThread(){
        client.stop();
    }

    /**
     * 
     */

}
