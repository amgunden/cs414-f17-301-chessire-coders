package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;

import java.util.ArrayList;

public class GetUserGameHistoryResponse {
    private int playerID;
    private ArrayList<GetGameResponse> history;

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public ArrayList<GetGameResponse> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<GetGameResponse> history) {
        this.history = history;
    }

    public GetUserGameHistoryResponse(int playerID, ArrayList<GetGameResponse> history) {
        this.playerID = playerID;
        this.history = history;
    }
}
