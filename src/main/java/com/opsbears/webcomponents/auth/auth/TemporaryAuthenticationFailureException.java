package com.opsbears.webcomponents.auth.auth;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TemporaryAuthenticationFailureException extends AuthenticationFailureException {
    public TemporaryAuthenticationFailureException(Throwable e) {
        super(e);
    }
}
