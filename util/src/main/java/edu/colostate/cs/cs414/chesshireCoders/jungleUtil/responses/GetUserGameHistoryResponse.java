package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;
import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.User;

import java.util.ArrayList;

public class GetUserGameHistoryResponse extends Response {

    private User user;
    private ArrayList<Game> games;

    public GetUserGameHistoryResponse(User user, ArrayList<Game> games) {
        this.user = user;
        this.games = games;
    }

    public GetUserGameHistoryResponse(int statusCode, String errMsg, User user, ArrayList<Game> games) {
        super(statusCode, errMsg);
        this.user = user;
        this.games = games;
    }

    public GetUserGameHistoryResponse() {
    }

    public GetUserGameHistoryResponse(int statusCode, String errMsg) {
        super(statusCode, errMsg);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }
}
