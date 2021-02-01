package com.suprun.periodicals.util;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Utility class for managing passwords.
 */
public class PasswordUtil {

    private static final int ITERATIONS = 20*1000;
    private static final int SALT_LENGTH = 32;
    private static final int DESIRED_KEY_LENGTH = 256;
    private static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
    private static final String SECRET_KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final String SALT_HASH_DELIMITER = "$";
    private static final String SALT_HASH_DELIMITER_REGEX = "\\$";

    /**
     * Provides a hash for a given String.
     *
     * @param password a password String
     * @return a hashed String
     *
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM).generateSeed(SALT_LENGTH);
        return Base64.getEncoder().encodeToString(salt) + SALT_HASH_DELIMITER + hash(password, salt);
    }
    /**
     * Checks whether a given password match those of the user.
     *
     * @param password a password represented as an array of chars
     * @param passwordHash User hash password
     * @return true if password  and password hash matches
     */
    public static boolean checkSecurePassword(String password,
                                              String passwordHash) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String[] saltAndHash = passwordHash.split(SALT_HASH_DELIMITER_REGEX);
        if (saltAndHash.length != 2) {
            throw new IllegalStateException("The stored password must have the form 'salt$hash'");
        }
        String hashOfInput = hash(password, Base64.getDecoder().decode(saltAndHash[0]));
        return hashOfInput.equals(saltAndHash[1]);
    }

    /**
     * Provides a hash for a given password with given salt.
     *
     * @param password a password String
     * @return a hashed String
     *
     */
    private static String hash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Empty passwords are not supported.");
        SecretKeyFactory f = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_ALGORITHM);
        SecretKey key = f.generateSecret(new PBEKeySpec(
                password.toCharArray(), salt, ITERATIONS, DESIRED_KEY_LENGTH));
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String hash = PasswordUtil.hashPassword("password");
        System.out.println(hash);
    }
}
