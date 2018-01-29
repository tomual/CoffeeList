package sample;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class MyHash {

    private String hash;
    private String salt;

    public MyHash(String password) {
        String salt = null;
        try {
            salt = getNewSalt();
            this.hash = getEncryptedPassword(password, salt);
            this.salt = salt;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }

    private boolean check(String password, String hash, String salt) throws Exception {
        String calculatedHash = getEncryptedPassword(password, salt);
        if (calculatedHash.equals(hash)) {
            return true;
        } else {
            return false;
        }
    }

    private String getEncryptedPassword(String password, String salt) throws Exception {
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 160;
        int iterations = 20000;

        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, derivedKeyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

        byte[] encBytes = f.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(encBytes);
    }

    private String getNewSalt() throws Exception {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
