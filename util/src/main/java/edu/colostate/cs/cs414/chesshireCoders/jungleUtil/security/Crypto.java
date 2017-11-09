package edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public final class Crypto {

    public static final int AUTH_TOKEN_LENGTH = 16;
    public static final long AUTH_TOKEN_MILLIS_VALID = 12 * 60 * 60 * 1000; // 12 hours

    public static AuthToken generateAuthToken() {
        SecureRandom random = new SecureRandom();
        byte[] token = new byte[AUTH_TOKEN_LENGTH];
        random.nextBytes(token);

        AuthToken authToken = new AuthToken();

        authToken.setToken(Base64.getEncoder().encodeToString(token));
        authToken.setExpiration(System.currentTimeMillis() + AUTH_TOKEN_MILLIS_VALID);

        return authToken;
    }

    public static String hashSHA256(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] digestedBytes = digest.digest(bytes);
        return Base64.getEncoder().encodeToString(digestedBytes);
    }
}
