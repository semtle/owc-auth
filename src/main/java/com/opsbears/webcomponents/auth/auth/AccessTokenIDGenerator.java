package com.opsbears.webcomponents.auth.auth;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface AccessTokenIDGenerator {
    String generate();
}
