package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class CheckRequest {
    private final String accessTokenId;

    public CheckRequest(String accessTokenId) {
        this.accessTokenId = accessTokenId;
    }

    public String getAccessTokenId() {
        return accessTokenId;
    }
}
