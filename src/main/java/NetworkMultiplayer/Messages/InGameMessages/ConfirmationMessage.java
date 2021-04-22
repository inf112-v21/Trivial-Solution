package NetworkMultiplayer.Messages.InGameMessages;

import NetworkMultiplayer.Messages.IMessage;

public enum ConfirmationMessage implements IMessage {
    CONNECTION_WAS_SUCCESSFUL,
    GAME_WAS_STARTED_AND_CLIENT_IS_READY_TO_RECEIVE_CARDS,
    TEST_MESSAGE, //Brukes kun til testing
    SERVER_CHOOSE_TO_DISCONNECTED
}
