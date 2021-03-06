package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AccessTokenException extends AuthenticationException {
    public AccessTokenException(Throwable cause) {
        super(cause);
    }

    public AccessTokenException(String s) {
        super();
    }
}
