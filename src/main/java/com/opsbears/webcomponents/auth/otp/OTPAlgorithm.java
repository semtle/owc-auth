package com.opsbears.webcomponents.auth.otp;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface OTPAlgorithm {
    String calculate(String secret);
}
