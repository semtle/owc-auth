package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.Future;

@ParametersAreNonnullByDefault
public interface GetAndStoreUserStorage extends UserStorage {
    Future<User> get(String userId) throws UserNotFoundException, StorageCurrentlyNotAvailableException;
    Future<Void> updatePassword(String userId, String hashedPassword) throws StorageCurrentlyNotAvailableException;
}
