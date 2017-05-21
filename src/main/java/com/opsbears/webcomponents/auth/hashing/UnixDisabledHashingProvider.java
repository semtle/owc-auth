package com.opsbears.webcomponents.auth.hashing;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class UnixDisabledHashingProvider implements PasswordHashingProvider {
    @Override
    public String[] getMCFHashPrefixes() {
        return new String[]{
            "*",
            "!"
        };
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String hash(String plainTextPassword) {
        return "*";
    }

    @Override
    public String raw(String plainTextPassword) {
        return "*";
    }

    @Override
    public Boolean verify(String plainTextPassword, String hashedPassword) {
        return false;
    }

    @Override
    public Boolean needsRehash(String hashedPassword) {
        return false;
    }
}
