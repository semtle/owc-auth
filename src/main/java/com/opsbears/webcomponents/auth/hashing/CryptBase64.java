package com.opsbears.webcomponents.auth.hashing;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Base64;

@ParametersAreNonnullByDefault
class CryptBase64 {
    static String encode(byte[] data) {
        String encoded = new String(Base64.getEncoder().encode(data));
        return encoded.replace('+', '.').replaceAll("=+$", "");
    }

    static byte[] decode(String data) {
        return Base64.getDecoder().decode(data.replace('.', '+').getBytes());
    }
}
