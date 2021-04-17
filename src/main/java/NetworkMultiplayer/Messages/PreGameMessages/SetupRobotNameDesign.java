package NetworkMultiplayer.Messages.PreGameMessages;

import NetworkMultiplayer.Messages.IMessage;

/**
 * Sender feilmelding fra server til klientene om de har valgt en robotdesign eller navn som er
 * valgt tidligere.
 */
public enum SetupRobotNameDesign implements IMessage {

    UNAVAILABLE_DESIGN, //Om klienten valgte et opptatt design for roboten
    UNAVAILABLE_NAME,   //Om klienten valgte et opptatt navn på roboten
    ROBOT_DESIGN_AND_NAME_ARE_OKEY // Alt er good!

}
