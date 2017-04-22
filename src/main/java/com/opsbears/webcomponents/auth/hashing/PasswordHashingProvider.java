package com.opsbears.webcomponents.auth.hashing;

/**
 * A password encryption provider is a class that provides hashing services for passwords.
 */
public interface PasswordHashingProvider {
    /**
     * @return the modular crypt format hash prefixes this provider can handle
     */
    String[] getMCFHashPrefixes();

    /**
     * Returns if the hash is cryptographically secure. This can be used to determine if the hashing scheme should be
     * changed.
     *
     * @return true if the hash is considered cryptographically secure.
     */
    boolean isSecure();

    /**
     * Hash a password and return the MCF format. May throw an exception if the encryption method should no longer
     * be used for encryption for security reasons.
     *
     * @param plainTextPassword the plain text password to hash
     *
     * @return the hashed password in MCF format
     */
    String hash(String plainTextPassword);

    /**
     * Hash a password and return the hash in raw format. This hash is not suitable for verification with this library
     * and should only be used for legacy applications.
     *
     * @param plainTextPassword the plain text password to hash
     *
     * @return the hashed password in raw form
     */
    String raw(String plainTextPassword);

    /**
     * Compare a plain text password to a hashed variant and return true if the two match.
     *
     * @param plainTextPassword the plain text password
     * @param hashedPassword the hashed password to compare to.
     *
     * @return true if the passwords match, false otherwise.
     */
    Boolean verify(String plainTextPassword, String hashedPassword);

    /**
     * Check if a given password needs rehashing. This is usually the case if the hash was generated with a weaker hash.
     *
     * @param hashedPassword the hash to check
     *
     * @return true if the password needs rehashing, false otherwise.
     */
    Boolean needsRehash(String hashedPassword);
}
