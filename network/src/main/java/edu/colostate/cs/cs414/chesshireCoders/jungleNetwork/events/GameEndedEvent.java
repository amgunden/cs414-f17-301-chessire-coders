package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.events;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.GameOutcomeType;

public class GameEndedEvent {
    int gameID;
    GameOutcomeType outcomeType;

    public GameEndedEvent(int gameID, GameOutcomeType gameOutcomeType) {
        this.gameID = gameID;
        this.outcomeType = gameOutcomeType;
    }
}
