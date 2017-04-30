package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.Future;

/**
 * This user storage obtains the user record by performing the authentication itself instead of returning the hash.
 * This is typically used with LDAP servers.
 */
@ParametersAreNonnullByDefault
public interface PassthruUserStorage extends UserStorage {
    Future<User> authenticate(
        String userId,
        String password
    ) throws UserNotFoundException, StorageCurrentlyNotAvailableException, AuthenticationFailureException;

    Future<Void> updatePassword(
        String userId,
        String oldPassword,
        String newPassword
    ) throws StorageCurrentlyNotAvailableException;
}
