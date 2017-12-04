package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

import java.util.Objects;

public class QuitGameRequest extends SessionRequest {

    private long gameId;

    public QuitGameRequest() {
    }

    public QuitGameRequest(long gameId) {
        this.gameId = gameId;
    }

    public QuitGameRequest(AuthToken authToken, long gameId) {
        super(authToken);
        this.gameId = gameId;
    }

    public long getGameId() {
        return gameId;
    }

    public QuitGameRequest setGameId(long gameId) {
        this.gameId = gameId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuitGameRequest that = (QuitGameRequest) o;
        return getGameId() == that.getGameId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getGameId());
    }
}
