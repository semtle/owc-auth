package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface GetAndStoreUserStorage extends UserStorage {
    User get(String userId) throws UserNotFoundException, StorageCurrentlyNotAvailableException;
    void updatePassword(String userId, String hashedPassword) throws StorageCurrentlyNotAvailableException;
}
