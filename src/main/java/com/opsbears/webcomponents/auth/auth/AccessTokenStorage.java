package com.opsbears.webcomponents.auth.auth;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface AccessTokenStorage {
    /**
     * Insert a new access token
     *
     * @param accessToken The access token to be stored.
     */
    void insert(AccessToken accessToken) throws StorageCurrentlyNotAvailableException, AccessTokenAlreadyExistsException;

    void update(AccessToken accessToken) throws StorageCurrentlyNotAvailableException;

    /**
     * Return an access token by its id. This function must check the expiry of the access token.
     *
     * @param accessToken The access token ID
     *
     * @return The full access token.
     *
     * @throws AccessTokenNotFoundException if the access token does not exist in storage.
     */
    AccessToken get(String accessToken) throws AccessTokenNotFoundException, StorageCurrentlyNotAvailableException;

    /**
     * Remove an access token if it exists.
     *
     * @param accessToken the access token ID to remove.
     */
    void delete(String accessToken) throws AccessTokenNotFoundException, StorageCurrentlyNotAvailableException;

    /**
     * Remove expired access tokens from storage.
     */
    void cleanup() throws StorageCurrentlyNotAvailableException;
}
