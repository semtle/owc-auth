package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * This user storage obtains the user record by performing the authentication itself instead of returning the hash.
 * This is typically used with LDAP servers.
 */
@ParametersAreNonnullByDefault
public interface PassthruUserStorage extends UserStorage {
    User authenticate(
        String userId,
        String password
    ) throws UserNotFoundException, StorageCurrentlyNotAvailableException, AuthenticationFailureException;

    void updatePassword(
        String userId,
        String newPassword
    ) throws StorageCurrentlyNotAvailableException;
}
