package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface User {
    /**
     * @return a unique user identifier in the context of all authentication backends in the current setup.
     */
    String getUniqueUserId();

    /**
     * @return the primary password hash.
     *
     * @see MultiHashUser
     */
    String getHashedPassword();

    /**
     * @return true if the user has set up multi factor authentication.
     */
    boolean needsTwoFactorAuthentication();
}
