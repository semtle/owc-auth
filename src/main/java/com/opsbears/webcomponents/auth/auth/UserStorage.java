package com.opsbears.webcomponents.auth.auth;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface UserStorage {
    User get(String userId) throws UserNotFoundException, StorageCurrentlyNotAvailableException;
    void updatePassword(String userId, String hashedPassword) throws StorageCurrentlyNotAvailableException;
}
