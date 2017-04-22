package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TemporaryAuthenticationFailureException extends AuthenticationFailureException {
    public TemporaryAuthenticationFailureException(Throwable e) {
        super(e);
    }
}
