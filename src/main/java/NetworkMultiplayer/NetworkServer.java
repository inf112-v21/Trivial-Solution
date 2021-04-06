package NetworkMultiplayer;


import NetworkMultiplayer.Messages.InGameMessages.ChosenCards;
import NetworkMultiplayer.Messages.IMessage;
import NetworkMultiplayer.Messages.PreGameMessages.Name;
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
    private Server server;

    //Portene som data sendes imellom. Disse er beskrevet som standard på kryonet nettsiden
    //Kanskje vi skal sette de som custom et annet sted i koden.
    //Metoder for det er definert lengre ned i tilfelle
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
            server.bind(DEFAULT_TCP_PORT,DEFAULT_UDP_PORT);
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
            public void received (Connection connection, Object object) {

                if (object instanceof ChosenCards){
                    //simuler runde
                }

                else if (object instanceof Name){
                    //Sjekk om navnet og designen er god
                }


            }
        });

    }

    /**
     * @return alle konneksjonene, altså alle klientene serveren
     * har en konneksjon til.
     */
    public Connection[] getConnections(){
        return server.getConnections();
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