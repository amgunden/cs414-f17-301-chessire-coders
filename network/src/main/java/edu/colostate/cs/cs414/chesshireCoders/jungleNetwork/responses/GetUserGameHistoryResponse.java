package edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses;

import java.util.ArrayList;

public class GetUserGameHistoryResponse {
    int playerID;
    ArrayList<GetGameResponse> history;

    public GetUserGameHistoryResponse(int playerID, ArrayList<GetGameResponse> history) {
        this.playerID = playerID;
        this.history = history;
    }
}
