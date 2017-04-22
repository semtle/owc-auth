package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDateTime;

@ParametersAreNonnullByDefault
public class CheckAndExtendRequest {
    private final String accessToken;
    @Nullable
    private final LocalDateTime newExpiryTime;

    public CheckAndExtendRequest(
        String accessToken
    ) {
        this.accessToken = accessToken;
        this.newExpiryTime = null;
    }

    public CheckAndExtendRequest(
        String accessToken,
        @Nullable LocalDateTime newExpiryTime
    ) {
        this.accessToken = accessToken;
        this.newExpiryTime = newExpiryTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    @Nullable
    public LocalDateTime getNewExpiryTime() {
        return newExpiryTime;
    }
}
