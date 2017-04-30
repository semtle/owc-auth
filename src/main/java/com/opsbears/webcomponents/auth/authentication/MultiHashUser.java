package com.opsbears.webcomponents.auth.authentication;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface MultiHashUser extends User {
    /**
     * @return a list of possibly valid hashes for users.
     */
    String[] getPasswordHashes();
}
