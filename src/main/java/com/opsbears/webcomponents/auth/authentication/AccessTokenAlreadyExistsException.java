package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AccessTokenAlreadyExistsException extends AccessTokenException {
    public AccessTokenAlreadyExistsException() {
        super("Access token already exists");
    }

    public AccessTokenAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
