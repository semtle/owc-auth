package com.opsbears.webcomponents.auth.otp;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TOTP implements OTPAlgorithm, OTPAuthenticator {

    @Override
    public boolean authenticate(String secret, String response) {
        return false;
    }

    @Override
    public String calculate(String secret) {
        return null;
    }
}
