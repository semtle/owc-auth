package com.opsbears.webcomponents.auth.random;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface SaltGenerator {
    byte[] generateSalt(int saltLength);
}
