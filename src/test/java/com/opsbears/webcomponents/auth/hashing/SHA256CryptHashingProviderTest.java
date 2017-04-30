package com.opsbears.webcomponents.auth.hashing;

import com.opsbears.webcomponents.auth.random.SecureRandomSaltGenerator;
import org.junit.Test;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertTrue;

@ParametersAreNonnullByDefault
public class SHA256CryptHashingProviderTest {
    @Test
    public void testHash() {
        List<String> testCases = new ArrayList<>();
        testCases.add("test");
        testCases.add("asdf");

        SHA256CryptHashingProvider provider = new SHA256CryptHashingProvider(new SecureRandomSaltGenerator());
        for (String testCase : testCases) {
            String hash = provider.hash(testCase);
            assertNotSame(hash, testCase);
            assertTrue(provider.verify(testCase, hash));
        }
    }

    @Test
    public void testVerify() {
        Map<String, String> testCases = new HashMap<>();
        testCases.put("test", "$5$Yp1Qr367FoTwSAB9$YrYim6UmBbPZy8QP7kWvEItVNRhCnGo3a5P.h5nL/2C");
        testCases.put("asdf", "$5$a0byWqJLSo0Mi6UK$mgUdBbkLqGjrlbR5qf/KZv7Sk6/tMu6C.LDLcIthBF4");

        SHA256CryptHashingProvider provider = new SHA256CryptHashingProvider(new SecureRandomSaltGenerator());
        for (Map.Entry<String,String>  testCase : testCases.entrySet()) {
            assertTrue(provider.verify(testCase.getKey(), testCase.getValue()));
        }
    }
}
