package com.opsbears.webcomponents.auth.auth;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
abstract public class AuthenticationFailureException extends AuthenticationException {
    public AuthenticationFailureException(Throwable e) {
        super(e);
    }
}
