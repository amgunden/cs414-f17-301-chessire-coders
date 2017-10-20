package edu.colostate.cs.cs414.chessireCoders.jungleNetwork.events;

import edu.colostate.cs.cs414.chessireCoders.jungleNetwork.types.GameOutcomeType;

public class GameEndedEvent {
    int gameID;
    GameOutcomeType outcomeType;

    public GameEndedEvent(int gameID, GameOutcomeType gameOutcomeType) {
        this.gameID = gameID;
        this.outcomeType = gameOutcomeType;
    }
}
