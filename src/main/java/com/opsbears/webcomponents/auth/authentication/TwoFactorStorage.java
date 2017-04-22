package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface TwoFactorStorage {
    void store(TwoFactorSettings twoFactorSettings) throws StorageCurrentlyNotAvailableException;
    TwoFactorSettings get(String userId) throws TwoFactorSettingsNotFoundException, StorageCurrentlyNotAvailableException;
}
