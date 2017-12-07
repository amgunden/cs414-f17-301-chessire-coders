package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

import java.util.ArrayList;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;

public class GetUserGameHistoryResponse extends Response {

    private String nickname;
    private ArrayList<Game> games;

    public GetUserGameHistoryResponse(String nickname, ArrayList<Game> games) {
        this.nickname = nickname;
        this.games = games;
    }

    public GetUserGameHistoryResponse(int statusCode, String errMsg, String nickname, ArrayList<Game> games) {
        super(statusCode, errMsg);
        this.nickname = nickname;
        this.games = games;
    }

    public GetUserGameHistoryResponse() {
    }

    public GetUserGameHistoryResponse(int statusCode, String errMsg) {
        super(statusCode, errMsg);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }
}
