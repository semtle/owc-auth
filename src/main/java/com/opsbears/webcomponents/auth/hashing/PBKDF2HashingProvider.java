package com.opsbears.webcomponents.auth.hashing;

import com.opsbears.webcomponents.auth.random.SaltGenerator;

import javax.annotation.Nonnull;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Passlib-compatible PBKDF2 hashing implementation. Creates an MCF-format password hash.
 *
 * @link https://pythonhosted.org/passlib/modular_crypt_format.html
 */
public class PBKDF2HashingProvider implements PasswordHashingProvider {
    private final Integer rounds;
    private final Integer saltLength;
    private static final String  defaultAlgorithm = "SHA512";
    private final SaltGenerator saltGenerator;

    public PBKDF2HashingProvider(
        Integer rounds,
        Integer saltLength,
        SaltGenerator saltGenerator

    ) {
        this.rounds = rounds;
        this.saltLength = saltLength;
        this.saltGenerator = saltGenerator;
    }


    @Override
    public String[] getMCFHashPrefixes() {
        return new String[] {
            "$pbkdf2$",
            "$pbkdf2-sha1$",
            "$pbkdf2-sha256$",
            "$pbkdf2-sha512$"
        };
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Nonnull
    public Boolean verify(@Nonnull String plainTextPassword, @Nonnull String hash) {
        String[] parts = hash.split("\\$");
        String algo;
        if (parts.length < 2) {
            return false;
        }
        switch (parts[1]) {
            case "pbkdf2":
            case "pbkdf2-sha1":
                algo = "SHA1";
                break;
            case "pbkdf2-sha256":
                algo = "SHA256";
                break;
            case "pbkdf2-sha512":
                algo = "SHA512";
                break;
            default:
                return false;
        }
        Integer rounds = Integer.parseInt(parts[2]);
        byte[] salt = CryptBase64.decode(parts[3]);
        return generateHashBinary(plainTextPassword, rounds, salt, algo).equals(parts[4]);
    }

    @Nonnull
    public Boolean needsRehash(@Nonnull String hash) {
        String[] parts = hash.split("\\$");
        switch (parts[1]) {
            case "pbkdf2":
            case "pbkdf2-sha1":
                return true;
            case "pbkdf2-sha256":
                return true;
            case "pbkdf2-sha512":
                return (Integer.parseInt(parts[2]) < rounds || CryptBase64.decode(parts[3]).length < saltLength);
            default:
                throw new RuntimeException("Invalid hashing PBKDF defaultAlgorithm " + parts[1] + " in hash: " + hash);
        }
    }

    @Nonnull
    public String hash(@Nonnull String plainTextPassword) {
        byte[] salt = saltGenerator.generateSalt(saltLength);
        return "$pbkdf2-" + defaultAlgorithm.toLowerCase() + "$" +
               rounds.toString() +
               "$" +
               CryptBase64.encode(salt) +
               "$" +
               generateHashBinary(plainTextPassword, rounds, salt, defaultAlgorithm);
    }

    @Override
    public String raw(String plainTextPassword) {
        return generateHashBinary(plainTextPassword, rounds, saltGenerator.generateSalt(saltLength), defaultAlgorithm);
    }

    @Nonnull
    private String generateHashBinary(
            @Nonnull String plainTextPassword,
            @Nonnull Integer rounds,
            @Nonnull byte[] salt,
            @Nonnull String algo
    ) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmac" + algo);
            int keyLength;
            switch (algo) {
                case "SHA512":
                    keyLength = 512;
                    break;
                case "SHA256":
                    keyLength = 256;
                    break;
                case "SHA1":
                    keyLength = 160;
                    break;
                default:
                    throw new RuntimeException("Invalid hash defaultAlgorithm: " + algo);
            }
            PBEKeySpec spec = new PBEKeySpec(
                    plainTextPassword.toCharArray(),
                    salt,
                    rounds,
                    keyLength
            );
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            return CryptBase64.encode(res);
        } catch (NoSuchAlgorithmException e) {
            throw new UnsupportedHashException("PBKDF2WithHmac" + algo, e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
