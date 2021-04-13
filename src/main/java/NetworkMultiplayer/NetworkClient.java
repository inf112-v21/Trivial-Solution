package NetworkMultiplayer;


import GUIMain.GUI;
import GUIMain.Screens.MultiplayerLoadingScreen;
import GUIMain.Screens.WaitingForHostScreen;
import GameBoard.Cards.ICard;
import GameBoard.Robot;
import NetworkMultiplayer.Messages.MinorErrorMessage;
import NetworkMultiplayer.Messages.ConfirmationMessages;
import NetworkMultiplayer.Messages.InGameMessages.DistributedCards;
import NetworkMultiplayer.Messages.PreGameMessages.GameInfo;
import NetworkMultiplayer.Messages.IMessage;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class NetworkClient {

    public Client client;

    final static int DEFAULT_UDP_PORT = 54777;
    final static int DEFAULT_TCP_PORT = 54555;

    private HashMap<Robot,IMessage> AllChooseRobotCards;
    private GameInfo setup;
    private boolean design;
    private boolean botName;



    //Roboter startes inne i networkclient og networkserver.
    //private Robot = new Robot();


    public NetworkClient() {

        client = new Client();
        new Thread(client).start();

        //start klienten --> åpner opp en tråd for at den skal kunne sende og motta meldinger over nettverket.

        //Registrer klienten i nettverket
        LanNetwork.register(client);

        addListeners();
    }

    /**
     * @return Henter robotene og kortene hver robot valgte
     */
    public HashMap<Robot, IMessage> getChooseCards() {
        return AllChooseRobotCards;
    }



    /**
     * @return true hvis navnet er valgt
     */
    public boolean isBotName() {
        return botName;
    }

    /**
     * @return true hvis designet er tatt
     */
    public boolean isDesign() {
        return design;
    }

    public GameInfo getSetup() {
        return setup;
    }

    /**
     * Metode som skaper listeners
     * Disse håndterer mottate packer/Messages som kommer fra
     * serveren til klienten vår
     */
    private void addListeners() {
        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {

                if(object instanceof MinorErrorMessage){
                    MinorErrorMessage message = ((MinorErrorMessage) object);
                    switch(message){
                        case UNAVAILABLE_DESIGN:
                            design = true;
                        case UNAVAILABLE_NAME:
                            botName = true;
                    }
                }

                if(object instanceof ConfirmationMessages){
                    ConfirmationMessages message = ((ConfirmationMessages) object);
                    switch(message){
                        case TEST_MESSAGE:
                            System.out.println("Client received message. Now sending Message to server");
                            sendToServer(ConfirmationMessages.CONNECTION_WAS_SUCCESSFUL);
                        case CONNECTION_WAS_SUCCESSFUL:
                            System.out.println("The message returned again");
                    }

                }

                //Klienten velger kortene sine
                if(object instanceof DistributedCards){
                    DistributedCards cardsRecieved =((DistributedCards) object);
                    //AllChooseRobotCards = cardsRecieved.getChosenCards();
                }

                //Setter opp spillet hos klienten
                else if (object instanceof GameInfo){
                    GameInfo gi = (GameInfo) object;
                    setup = gi;
                }



            }

            @Override
            public void connected(Connection connection){
                sendToServer(ConfirmationMessages.CONNECTION_WAS_SUCCESSFUL);
            }
        });

    }


    /**
     * Vi må konnektere oss til serveren etterhvert. TCP fikser dette med en handshake runde først.
     * Den etablerer konneksjonen før den faktiskt sender packer frem og tilbake
     * @param ipAdress
     * @return
     */
    public boolean connect(String ipAdress) {
        System.out.println(ipAdress);
        boolean connectionEstablished = true;
        try {
            //connecter til hosten
            client.connect(9999999, ipAdress, DEFAULT_TCP_PORT, DEFAULT_UDP_PORT);

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

}
