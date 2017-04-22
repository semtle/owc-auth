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
public class PBKDF2HashingProviderTest {
    @Test
    public void testHash() {
        List<String> testCases = new ArrayList<>();
        testCases.add("test");
        testCases.add("asdf");

        PBKDF2HashingProvider provider = new PBKDF2HashingProvider(1000, 16, new SecureRandomSaltGenerator());
        for (String testCase : testCases) {
            String hash = provider.hash(testCase);
            assertNotSame(hash, testCase);
            assertTrue(provider.verify(testCase, hash));
        }
    }

    @Test
    public void testVerify() {
        Map<String, String> testCases = new HashMap<>();
        testCases.put("test", "$pbkdf2-sha256$1000$0Tqn1LqXck5JSUmpNSYEIA$tUpP5UB2Dw3iZEL8ZJZb2lIVCXQkmais1YzHJErlp8s");
        testCases.put("asdf", "$pbkdf2-sha256$1000$976X8h6DcI6xthZijHFOiQ$viuADwgpzyqJhY6nrctn2KA5IrI4lxOemb8sc9AVWvM");
        testCases.put("password", "$pbkdf2-sha256$1000$nPMeg3AOgZByTqm1dm6NsQ$y9OL3X8BBzOH.EPlyKYLRKbnTyd2joM6UjiCliJnCW8");

        PBKDF2HashingProvider provider = new PBKDF2HashingProvider(1000, 16, new SecureRandomSaltGenerator());
        for (Map.Entry<String,String>  testCase : testCases.entrySet()) {
            assertTrue(provider.verify(testCase.getKey(), testCase.getValue()));
        }
    }
}
