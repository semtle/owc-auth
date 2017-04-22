package com.opsbears.webcomponents.auth.hashing;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class UnsupportedHashException extends RuntimeException {
    private final String algorithm;

    public UnsupportedHashException(String algorithm) {
        super("Unsupported hash algorithm: " + algorithm);
        this.algorithm = algorithm;
    }

    public UnsupportedHashException(String algorithm, Throwable cause) {
        super("Unsupported hash algorithm: " + algorithm, cause);
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
