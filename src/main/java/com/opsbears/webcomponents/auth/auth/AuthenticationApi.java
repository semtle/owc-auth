package com.opsbears.webcomponents.auth.auth;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * The authentication API provides authentication services.
 */
@ParametersAreNonnullByDefault
public interface AuthenticationApi {
    /**
     * Authenticate based on username and password.
     *
     * @param request the authentication request containing the username and password.
     *
     * @return The authentication response containing the access token.
     *
     * @throws InvalidCredentialsException if the authentication fails.
     */
    AuthenticateResponse authenticate(
        AuthenticateRequest request
    ) throws InvalidCredentialsException, TemporaryAuthenticationFailureException;

    /**
     * Check if an access token is valid.
     *
     * @param accessToken the access token to check
     *
     * @return The response containing the full access token object.
     *
     * @throws InvalidAccessTokenException if the access token is invalid.
     */
    CheckAccessTokenResponse check(
        String accessToken
    ) throws InvalidAccessTokenException, TemporaryAccessTokenFailureException;

    /**
     * Check and update the expiry of an access token.
     *
     * @param request     the expiry information requested.
     *
     * @return The response with the updated access token.
     *
     * @throws InvalidAccessTokenException if the access token is invalid.
     */
    CheckAccessTokenResponse checkAndExtend(
        CheckAndExtendRequest request
    ) throws InvalidAccessTokenException, TemporaryAccessTokenFailureException;

    /**
     * Invalidate an access token.
     *
     * @param accessToken the access token to invalidate.
     *
     * @return the invalidation result.
     *
     * @throws InvalidAccessTokenException if the access token is not valid.
     */
    DeauthenticateResponse deauthenticate(
        String accessToken
    ) throws InvalidAccessTokenException, TemporaryAccessTokenFailureException;
}
