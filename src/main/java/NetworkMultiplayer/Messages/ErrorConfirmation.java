package NetworkMultiplayer.Messages;

/**
 * Sender feilmelding fra server til klientene om de har valgt en robotdesign eller navn som er
 * valgt tidligere.
 */
public enum ErrorConfirmation implements IMessage {

    UNAVAILABLE_DESIGN, //Om klienten valgte et opptatt design for roboten
    UNAVAILABLE_NAME,   //Om klienten valgte et opptatt navn på roboten
}
