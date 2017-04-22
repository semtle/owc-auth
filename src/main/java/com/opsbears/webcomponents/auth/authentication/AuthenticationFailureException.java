package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
abstract public class AuthenticationFailureException extends AuthenticationException {
    public AuthenticationFailureException(Throwable e) {
        super(e);
    }
}
