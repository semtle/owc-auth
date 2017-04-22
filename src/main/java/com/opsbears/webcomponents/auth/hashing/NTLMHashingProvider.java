package com.opsbears.webcomponents.auth.hashing;

import sun.security.provider.MD4;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.UnsupportedEncodingException;

@ParametersAreNonnullByDefault
public class NTLMHashingProvider implements PasswordHashingProvider {
    @Override
    public String[] getMCFHashPrefixes() {
        return new String[] {
            "$3$"
        };
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String hash(String plainTextPassword) {
        return "$3$" + raw(plainTextPassword);
    }

    @Override
    public String raw(String plainTextPassword) {
        try {
            byte[]        bytes     = MD4.getInstance().digest(plainTextPassword.getBytes("UTF-16LE"));
            StringBuilder hexString = new StringBuilder();

            for (byte aByte : bytes) {
                String hex = Integer.toHexString(0xFF & aByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString().toUpperCase();
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedHashException("UTF-16LE");
        }
    }

    @Override
    public Boolean verify(String plainTextPassword, String hashedPassword) {
        return hash(plainTextPassword).equalsIgnoreCase(hashedPassword);
    }

    @Override
    public Boolean needsRehash(String hashedPassword) {
        return true;
    }
}
