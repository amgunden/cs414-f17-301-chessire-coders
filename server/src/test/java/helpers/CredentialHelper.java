package helpers;

import edu.colostate.cs.cs414.chesshireCoders.jungleUtil.security.Crypto;
import gherkin.lexer.Ru;

import java.security.NoSuchAlgorithmException;
import java.util.*;

public class CredentialHelper {

    private Map<String, Credential> emailCredentialMap;
    private Map<String, Credential> nickNameCredentialMap;

    public CredentialHelper() {
        this.emailCredentialMap = new HashMap<>();
        this.nickNameCredentialMap = new HashMap<>();
    }

    public void add(String email, String nickName, String password) {
        try {
            Credential credential = new Credential()
                    .setEmail(email)
                    .setHashedPassword(Crypto.hashSHA256(password.getBytes()))
                    .setNickName(nickName);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Credential getCredentialByEmail(String email) {
        return emailCredentialMap.get(email);
    }

    public Credential getCredentialByNickName(String nickName) {
        return nickNameCredentialMap.get(nickName);
    }

    public Collection<Credential> getCredentialSet() {
        return emailCredentialMap.values();
    }

    public void clearCredentials() {
        emailCredentialMap.clear();
        nickNameCredentialMap.clear();
    }

    public Set<String> getEmails() {
        return emailCredentialMap.keySet();
    }

    private static class Credential {
        private String email;
        private String nickName;
        private String hashedPassword;

        public String getEmail() {
            return email;
        }

        public Credential setEmail(String email) {
            this.email = email;
            return this;
        }

        public String getNickName() {
            return nickName;
        }

        public Credential setNickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public String getHashedPassword() {
            return hashedPassword;
        }

        public Credential setHashedPassword(String hashedPassword) {
            this.hashedPassword = hashedPassword;
            return this;
        }
    }
}
