package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

@ParametersAreNonnullByDefault
public class UUIDAccessTokenIDGenerator implements AccessTokenIDGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
