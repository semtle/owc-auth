package com.opsbears.webcomponents.auth.otp;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class HOTPAuthenticator implements OTPAuthenticator {
    @Override
    public boolean authenticate(String secret, String response) {
        return false;
    }
}
