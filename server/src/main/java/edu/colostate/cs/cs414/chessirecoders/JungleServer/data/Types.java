package edu.colostate.cs.cs414.chessirecoders.JungleServer.data;

public class Types {

    public enum InvitationResponseType {
        ACCEPT,
        REJECT
    }
    public enum GameOutcomeType {
        P1_WON,
        P2_WON,
        DRAW
    }

    public enum PlayerColor {
        RED,
        BLACK
    }

    public enum GameStatus {
        WAITING,
        IN_PROGRESS,
        DRAW,
        P1_WINNER,
        P2_WINNER
    }

    public enum PlayerStatus {
        INVITED,
        ACCEPTED,
        REJECTED
    }
}
