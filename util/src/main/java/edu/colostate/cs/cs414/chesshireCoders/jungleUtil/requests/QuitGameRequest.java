package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.requests;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.AuthToken;

import java.util.Objects;

public class QuitGameRequest {

    private AuthToken token;
    private long gameId;

    public QuitGameRequest() {
    }

    public QuitGameRequest(AuthToken token, long gameId) {
        this.token = token;
        this.gameId = gameId;
    }

    public AuthToken getToken() {
        return token;
    }

    public QuitGameRequest setToken(AuthToken token) {
        this.token = token;
        return this;
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
        return getGameId() == that.getGameId() &&
                Objects.equals(getToken(), that.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken(), getGameId());
    }
}
