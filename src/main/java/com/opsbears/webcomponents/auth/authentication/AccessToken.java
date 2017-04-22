package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDateTime;

@ParametersAreNonnullByDefault
public class AccessToken {
    private final String id;
    private final String userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime validUntil;

    public AccessToken(
        String id,
        String userId,
        LocalDateTime createdAt,
        LocalDateTime validUntil
    ) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.validUntil = validUntil;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public String toString() {
        return id;
    }

    public String getUserId() {
        return userId;
    }
}
