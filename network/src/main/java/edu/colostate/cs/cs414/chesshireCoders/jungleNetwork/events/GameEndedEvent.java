package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.events;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.GameOutcomeType;

public class GameEndedEvent {
    private int gameID;
    private GameOutcomeType outcomeType;

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public GameOutcomeType getOutcomeType() {
        return outcomeType;
    }

    public void setOutcomeType(GameOutcomeType outcomeType) {
        this.outcomeType = outcomeType;
    }

    public GameEndedEvent(int gameID, GameOutcomeType gameOutcomeType) {
        this.gameID = gameID;
        this.outcomeType = gameOutcomeType;
    }
}
