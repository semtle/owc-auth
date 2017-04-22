package com.opsbears.webcomponents.auth.hashing;

import com.opsbears.webcomponents.auth.random.SaltGenerator;
import org.apache.commons.codec.digest.Md5Crypt;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class MD5CryptHashingProvider implements PasswordHashingProvider {
    private final int saltLength = 8;
    private final SaltGenerator saltGenerator;

    public MD5CryptHashingProvider(SaltGenerator saltGenerator) {
        this.saltGenerator = saltGenerator;
    }

    @Override
    public String[] getMCFHashPrefixes() {
        return new String[] { "$1$", "$apr1$" };
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String hash(String plainTextPassword) {
        //todo is there a nicer way of doing this?
        String salt = CryptBase64.encode(saltGenerator.generateSalt(saltLength)).substring(0, saltLength);
        return "$1$" + salt + "$" + raw(
            "$1$",
            plainTextPassword,
            salt
        );
    }

    @Override
    public String raw(String plainTextPassword) {
        return raw(
            "$1$",
            plainTextPassword,
            CryptBase64.encode(saltGenerator.generateSalt(saltLength))
        );
    }

    @Override
    public Boolean verify(String plainTextPassword, String hashedPassword) {
        String[] passwordParts = hashedPassword.split("\\$");
        if (passwordParts.length != 4 || !passwordParts[1].equals("1")) {
            throw new InvalidPasswordHashException();
        }
        String salt = passwordParts[2];
        String hash = passwordParts[3];
        String raw = raw("$" + passwordParts[1] + "$", plainTextPassword, salt);

        return raw.equals(hash);
    }

    @Override
    public Boolean needsRehash(String hashedPassword) {
        // Always needs rehash, don't use this algorithm
        return true;
    }

    private String raw(String magic, String plainTextPassword, String salt) {
        String crypt;
        if (magic.equals("$apr1$")) {
            crypt = Md5Crypt.apr1Crypt(plainTextPassword.getBytes(), "$apr1$" + salt + "$");
        } else {
            crypt = Md5Crypt.md5Crypt(plainTextPassword.getBytes(), "$1$" + salt + "$");
        }
        String[] cryptParts = crypt.split("\\$");
        return cryptParts[3];
    }
}
