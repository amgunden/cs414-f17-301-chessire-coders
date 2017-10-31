package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.responses;


import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.game.Player;

public class GetPlayerResponse extends Response {
    private Player player;

    public GetPlayerResponse(Player player) {
        this.player = player;
    }

    public GetPlayerResponse(int statusCode, String errMsg, Player player) {
        super(statusCode, errMsg);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
