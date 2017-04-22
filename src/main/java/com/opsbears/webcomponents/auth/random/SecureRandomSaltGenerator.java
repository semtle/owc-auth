package com.opsbears.webcomponents.auth.random;

import javax.annotation.ParametersAreNonnullByDefault;
import java.security.SecureRandom;
import java.util.Random;

@ParametersAreNonnullByDefault
public class SecureRandomSaltGenerator implements SaltGenerator {
    private static final Random r = new SecureRandom();

    @Override
    public byte[] generateSalt(int saltLength) {
        byte[] salt = new byte[saltLength];
        r.nextBytes(salt);
        return salt;
    }
}
