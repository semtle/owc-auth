package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDateTime;

@ParametersAreNonnullByDefault
public class CheckAndExtendRequest extends CheckRequest {
    @Nullable
    private final LocalDateTime newExpiryTime;

    public CheckAndExtendRequest(String accessTokenId) {
        super(accessTokenId);
        this.newExpiryTime = null;
    }

    public CheckAndExtendRequest(
        String accessTokenId,
        @Nullable LocalDateTime newExpiryTime
    ) {
        super(accessTokenId);
        this.newExpiryTime = newExpiryTime;
    }

    @Nullable
    public LocalDateTime getNewExpiryTime() {
        return newExpiryTime;
    }
}
