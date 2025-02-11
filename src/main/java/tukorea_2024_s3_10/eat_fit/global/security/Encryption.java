package tukorea_2024_s3_10.eat_fit.global.security;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class Encryption {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String getSalt() {
        byte[] salt = new byte[16];
        SECURE_RANDOM.nextBytes(salt);
        return new String(salt, StandardCharsets.UTF_8);
    }

    public static String sha256Encode(String data, String salt) {
        return Hashing.sha256().hashString(data + salt, StandardCharsets.UTF_8).toString();
    }
}
