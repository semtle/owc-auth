package com.opsbears.webcomponents.auth.hashing;

import org.apache.commons.codec.digest.UnixCrypt;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class UnixCryptHashingProvider implements PasswordHashingProvider {
    @Override
    public String[] getMCFHashPrefixes() {
        return new String[] { "" };
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String hash(String plainTextPassword) {
        return UnixCrypt.crypt(plainTextPassword);
    }

    @Override
    public String raw(String plainTextPassword) {
        return UnixCrypt.crypt(plainTextPassword);
    }

    @Override
    public Boolean verify(String plainTextPassword, String hashedPassword) {
        return UnixCrypt.crypt(plainTextPassword, hashedPassword).equals(hashedPassword);
    }

    @Override
    public Boolean needsRehash(String hashedPassword) {
        //Always needs rehash, no longer secure
        return true;
    }
}
