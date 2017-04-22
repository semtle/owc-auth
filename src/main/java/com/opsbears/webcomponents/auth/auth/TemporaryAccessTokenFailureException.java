package com.opsbears.webcomponents.auth.auth;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TemporaryAccessTokenFailureException extends AccessTokenException {
    public TemporaryAccessTokenFailureException(StorageCurrentlyNotAvailableException e) {
        super(e);
    }
}
