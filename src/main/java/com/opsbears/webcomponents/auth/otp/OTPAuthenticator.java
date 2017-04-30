package com.opsbears.webcomponents.auth.otp;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface OTPAuthenticator {
    boolean authenticate(String secret, String response);
}
