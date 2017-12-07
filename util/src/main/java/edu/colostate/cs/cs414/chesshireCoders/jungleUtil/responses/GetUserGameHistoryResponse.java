package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;

import java.util.List;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Game;

public class GetUserGameHistoryResponse extends Response {

    private String nickname;
    private List<Game> games;

    public GetUserGameHistoryResponse(String nickname, List<Game> games) {
        this.nickname = nickname;
        this.games = games;
    }

    public GetUserGameHistoryResponse(int statusCode, String errMsg, String nickname, List<Game> games) {
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

    public GetUserGameHistoryResponse setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public List<Game> getGames() {
        return games;
    }

    public GetUserGameHistoryResponse setGames(List<Game> games) {
        this.games = games;
        return this;
    }
}
