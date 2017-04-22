package com.opsbears.webcomponents.auth.auth;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class CheckAccessTokenResponse {
    private final AccessToken accessToken;

    public CheckAccessTokenResponse(
        AccessToken accessToken
    ) {
        this.accessToken = accessToken;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }
}
