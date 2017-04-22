package com.opsbears.webcomponents.auth.hashing;

import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BCryptHashingProvider implements PasswordHashingProvider {
    @Override
    public String[] getMCFHashPrefixes() {
        return new String[] {
            "$2$",
            "$2a$",
            "$2x$",
            "$2y$"
        };
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String hash(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    @Override
    public String raw(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    @Override
    public Boolean verify(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    @Override
    public Boolean needsRehash(String hashedPassword) {
        return !hashedPassword.startsWith("$2a$");
    }
}
