package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TwoFactorAuthenticationRequired extends Exception {
    private final String userId;

    public TwoFactorAuthenticationRequired(String userId) {
        this.userId = userId;
    }
}
