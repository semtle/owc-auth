package com.opsbears.webcomponents.auth.hashing;

import com.opsbears.webcomponents.auth.random.SaltGenerator;
import org.apache.commons.codec.digest.Sha2Crypt;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SHA256CryptHashingProvider implements PasswordHashingProvider {
    private final int saltLength = 16;
    private final SaltGenerator saltGenerator;

    public SHA256CryptHashingProvider(SaltGenerator saltGenerator) {
        this.saltGenerator = saltGenerator;
    }

    @Override
    public String[] getMCFHashPrefixes() {
        return new String[] { "$5$" };
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String hash(String plainTextPassword) {
        //todo is there a nicer way of doing this?
        String salt = CryptBase64.encode(saltGenerator.generateSalt(saltLength)).substring(0, saltLength);
        return "$5$" + salt + "$" + raw(
                "$5$",
                plainTextPassword,
                salt
        );
    }

    @Override
    public String raw(String plainTextPassword) {
        return raw(
                "$5$",
                plainTextPassword,
                CryptBase64.encode(saltGenerator.generateSalt(saltLength))
        );
    }

    @Override
    public Boolean verify(String plainTextPassword, String hashedPassword) {
        String[] passwordParts = hashedPassword.split("\\$");
        if (passwordParts.length != 4 || !passwordParts[1].equals("5")) {
            throw new InvalidPasswordHashException();
        }
        String salt = passwordParts[2];
        String hash = passwordParts[3];
        String raw = raw("$" + passwordParts[1] + "$", plainTextPassword, salt);

        return raw.equals(hash);
    }

    @Override
    public Boolean needsRehash(String hashedPassword) {
        return false;
    }

    private String raw(String magic, String plainTextPassword, String salt) {
        String crypt = Sha2Crypt.sha256Crypt(plainTextPassword.getBytes(), "$5$" + salt + "$");
        String[] cryptParts = crypt.split("\\$");
        return cryptParts[3];
    }
}
