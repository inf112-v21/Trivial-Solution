package NetworkMultiplayer.Messages;


import GameBoard.Robot;

import java.io.Serializable;

/**
 * Gir en beskjed til alle andre klienter om at en klient ble disconnectet.
 * Vi sender den disconnetede roboten over nettverket
 */
public class ClientDisconnected implements IMessage, Serializable {

    Robot disconnectedBot;

    public ClientDisconnected(Robot disconnectedBot) {
        this.disconnectedBot = disconnectedBot;
    }

    /**
     * @return - Henter ut roboten til konnekjonen som ble avbrutt
     */
    public Robot getDisconnectedBot() {
        return disconnectedBot;
    }
}
