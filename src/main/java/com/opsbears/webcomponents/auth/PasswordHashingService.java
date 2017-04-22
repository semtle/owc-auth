package com.opsbears.webcomponents.auth;

import com.opsbears.webcomponents.auth.hashing.*;
import com.opsbears.webcomponents.auth.random.SecureRandomSaltGenerator;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;

@ParametersAreNonnullByDefault
public class PasswordHashingService {
    private final List<PasswordHashingProvider> passwordHashingProviders;

    public PasswordHashingService(
        List<PasswordHashingProvider> passwordHashingProviders
    ) {
        this.passwordHashingProviders = passwordHashingProviders;
    }

    public static PasswordHashingService getDefaultHashingConfiguration() {
        return new PasswordHashingService(
            Arrays.asList(
                new UnixDisabledHashingProvider(),
                new PBKDF2HashingProvider(1000, 16, new SecureRandomSaltGenerator()),
                new BCryptHashingProvider(),
                new MD5CryptHashingProvider(new SecureRandomSaltGenerator()),
                new NTLMHashingProvider(),
                new UnixCryptHashingProvider()
            )
        );
    }

    /**
     * Hash a password and return the MCF format. May throw an exception if the encryption method should no longer
     * be used for encryption for security reasons.
     *
     * @param plainTextPassword the plain text password to hash
     *
     * @return the hashed password in MCF format
     */
    public String hash(String plainTextPassword) {
        for (PasswordHashingProvider passwordHashingProvider : passwordHashingProviders) {
            if (passwordHashingProvider.isSecure()) {
                return passwordHashingProvider.hash(plainTextPassword);
            }
        }
        throw new NoSecureHashingProviderFoundException();
    }

    private PasswordHashingProvider findHashingProvider(String hash) {
        for (PasswordHashingProvider passwordHashingProvider : passwordHashingProviders) {
            for (String prefix : passwordHashingProvider.getMCFHashPrefixes()) {
                if (hash.startsWith(prefix)) {
                    return passwordHashingProvider;
                }
            }
        }
        throw new NoMatchingHashingProviderFoundException();
    }

    /**
     * Compare a plain text password to a hashed variant and return true if the two match.
     *
     * @param plainTextPassword the plain text password
     * @param hashedPassword the hashed password to compare to.
     *
     * @return true if the passwords match, false otherwise.
     */
    public Boolean verify(String plainTextPassword, String hashedPassword) {
        return findHashingProvider(hashedPassword).verify(plainTextPassword, hashedPassword);
    }

    /**
     * Check if a given password needs rehashing. This is usually the case if the hash was generated with a weaker hash.
     *
     * @param hashedPassword the hash to check
     *
     * @return true if the password needs rehashing, false otherwise.
     */
    public Boolean needsRehash(String hashedPassword) {
        return findHashingProvider(hashedPassword).needsRehash(hashedPassword);
    }
}
