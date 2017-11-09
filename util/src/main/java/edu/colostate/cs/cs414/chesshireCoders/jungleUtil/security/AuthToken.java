package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security;

public class AuthToken {

    private static AuthToken token;

    private String authenticationToken;

    private AuthToken() {
    }

    public static AuthToken getInstance() {
        if (token == null) {
            synchronized (AuthToken.class) {
                token = new AuthToken();
            }
        }

        return token;
    }

    public static void load(String file) {
    }

    public static void save(String file) {
    }

    public String getToken() {
        return authenticationToken;
    }

    public void setToken(String token) {
        this.authenticationToken = authenticationToken;
    }
}
