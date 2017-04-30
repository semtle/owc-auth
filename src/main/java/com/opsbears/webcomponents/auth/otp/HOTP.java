package com.opsbears.webcomponents.auth.otp;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class HOTP {
    public String calculate(String secret, int counter) {
        
    }

    public boolean authenticate(String secret, int counter, String response) {
        return false;
    }
}
