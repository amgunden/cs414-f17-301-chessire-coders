package edu.colostate.cs.cs414.chesshireCoders.jungleServer.data;

import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.GameOutcomeType;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.types.PlayerColor;

public class Player {

    private int gameId;
    private int userId;
    private PlayerColor color;
    private GameOutcomeType outcomeType;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public GameOutcomeType getOutcomeType() {
        return outcomeType;
    }

    public void setOutcomeType(GameOutcomeType outcomeType) {
        this.outcomeType = outcomeType;
    }
}
