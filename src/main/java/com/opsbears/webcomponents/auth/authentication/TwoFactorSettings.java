package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TwoFactorSettings {
    private final String userId;
    private final boolean enableTwoFactor;

    public TwoFactorSettings(String userId, boolean enableTwoFactor) {
        this.userId = userId;
        this.enableTwoFactor = enableTwoFactor;
    }

    public String getUserId() {
        return userId;
    }

    public boolean getEnableTwoFactor() {
        return enableTwoFactor;
    }
}
