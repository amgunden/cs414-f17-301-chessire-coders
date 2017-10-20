package edu.colostate.cs.cs414.chessirecoders.jungleNetwork.events;

import edu.colostate.cs.cs414.chessirecoders.jungleNetwork.types.Types;

public class GameEndedEvent {
    int gameID;
    Types.GameOutcomeType outcomeType;

    public GameEndedEvent(int gameID, Types.GameOutcomeType gameOutcomeType) {
        this.gameID = gameID;
        this.outcomeType = gameOutcomeType;
    }
}
