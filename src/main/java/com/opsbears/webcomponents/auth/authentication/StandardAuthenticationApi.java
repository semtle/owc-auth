package com.opsbears.webcomponents.auth.authentication;

import com.opsbears.webcomponents.auth.hashing.PasswordHashingService;

import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;

@ParametersAreNonnullByDefault
public class StandardAuthenticationApi implements AuthenticationApi {
    private final UserStorage            userStorage;
    private final AccessTokenStorage     accessTokenStorage;
    private final PasswordHashingService passwordHashingService;
    private final AccessTokenIDGenerator accessTokenIDGenerator;
    private final int defaultAccessTokenLifeTime;
    private final TemporalAccessor temporalAccessor;

    public StandardAuthenticationApi(
        UserStorage userStorage,
        AccessTokenStorage accessTokenStorage,
        PasswordHashingService passwordHashingService,
        AccessTokenIDGenerator accessTokenIDGenerator,
        TemporalAccessor temporalAccessor
    ) {
        this.userStorage = userStorage;
        this.accessTokenStorage = accessTokenStorage;
        this.passwordHashingService = passwordHashingService;
        this.accessTokenIDGenerator = accessTokenIDGenerator;
        this.temporalAccessor = temporalAccessor;
        defaultAccessTokenLifeTime = 1800;
    }

    public StandardAuthenticationApi(
        UserStorage userStorage,
        AccessTokenStorage accessTokenStorage,
        PasswordHashingService passwordHashingService,
        AccessTokenIDGenerator accessTokenIDGenerator,
        int defaultAccessTokenLifeTime,
        TemporalAccessor temporalAccessor
    ) {
        this.userStorage = userStorage;
        this.accessTokenStorage = accessTokenStorage;
        this.passwordHashingService = passwordHashingService;
        this.accessTokenIDGenerator = accessTokenIDGenerator;
        this.defaultAccessTokenLifeTime = defaultAccessTokenLifeTime;
        this.temporalAccessor = temporalAccessor;
    }

    @Override
    public AuthenticateResponse authenticate(AuthenticateRequest request)
        throws InvalidCredentialsException, TemporaryAuthenticationFailureException {
        User user;
        try {
            user = userStorage.get(request.getUserId());
        } catch (UserNotFoundException e) {
            throw new InvalidCredentialsException();
        } catch (StorageCurrentlyNotAvailableException e) {
            throw new TemporaryAuthenticationFailureException(e);
        }
        if (!passwordHashingService.verify(request.getPassword(), user.getHashedPassword())) {
            throw new InvalidCredentialsException();
        }
        if (passwordHashingService.needsRehash(user.getHashedPassword())) {
            try {
                userStorage.updatePassword(request.getUserId(), passwordHashingService.hash(request.getPassword()));
            } catch (StorageCurrentlyNotAvailableException ignored) {
            }
        }

        AccessToken accessToken;
        int tries = 0;
        while (true) {
            try {
                AccessToken at = new AccessToken(
                    accessTokenIDGenerator.generate(),
                    request.getUserId(),
                    LocalDateTime.from(temporalAccessor),
                    LocalDateTime.from(temporalAccessor).plus(defaultAccessTokenLifeTime, ChronoUnit.SECONDS)
                );
                accessTokenStorage.insert(at);
                accessToken = at;
                break;
            } catch (StorageCurrentlyNotAvailableException e) {
                throw new TemporaryAuthenticationFailureException(e);
            } catch (AccessTokenAlreadyExistsException e) {
                if (tries++ > 3) {
                    throw new TemporaryAuthenticationFailureException(e);
                }
            }
        }
        return new AuthenticateResponse(accessToken);
    }

    @Override
    public CheckAccessTokenResponse check(String accessTokenId)
        throws InvalidAccessTokenException, TemporaryAccessTokenFailureException {
        AccessToken accessToken;
        try {
            accessToken = accessTokenStorage.get(accessTokenId);
        } catch (AccessTokenNotFoundException e) {
            throw new InvalidAccessTokenException();
        } catch (StorageCurrentlyNotAvailableException e) {
            throw new TemporaryAccessTokenFailureException(e);
        }
        return new CheckAccessTokenResponse(accessToken);
    }

    @Override
    public CheckAccessTokenResponse checkAndExtend(
        CheckAndExtendRequest request
    ) throws InvalidAccessTokenException, TemporaryAccessTokenFailureException {
        AccessToken accessToken = check(request.getAccessToken()).getAccessToken();
        LocalDateTime newExpiryTime;
        if (request.getNewExpiryTime() != null) {
            newExpiryTime = request.getNewExpiryTime();
        } else {
            newExpiryTime = LocalDateTime.from(temporalAccessor).plus(defaultAccessTokenLifeTime, ChronoUnit.SECONDS);
        }
        accessToken = new AccessToken(
            accessToken.getId(),
            accessToken.getUserId(),
            accessToken.getCreatedAt(),
            newExpiryTime
        );
        try {
            accessTokenStorage.update(accessToken);
        } catch (StorageCurrentlyNotAvailableException e) {
            throw new TemporaryAccessTokenFailureException(e);
        }
        return new CheckAccessTokenResponse(accessToken);
    }

    @Override
    public DeauthenticateResponse deauthenticate(String accessToken)
        throws InvalidAccessTokenException, TemporaryAccessTokenFailureException {
        try {
            accessTokenStorage.delete(accessToken);
        } catch (StorageCurrentlyNotAvailableException e) {
            throw new TemporaryAccessTokenFailureException(e);
        } catch (AccessTokenNotFoundException e) {
            throw new InvalidAccessTokenException();
        }
        return new DeauthenticateResponse();
    }
}
