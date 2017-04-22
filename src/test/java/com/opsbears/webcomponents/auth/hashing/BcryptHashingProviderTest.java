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
public class BcryptHashingProviderTest {
    @Test
    public void testHash() {
        List<String> testCases = new ArrayList<>();
        testCases.add("test");
        testCases.add("asdf");

        BCryptHashingProvider provider = new BCryptHashingProvider();
        for (String testCase : testCases) {
            String hash = provider.hash(testCase);
            assertNotSame(hash, testCase);
            assertTrue(provider.verify(testCase, hash));
        }
    }

    @Test
    public void testVerify() {
        Map<String, String> testCases = new HashMap<>();
        testCases.put("test", "$2a$10$5T5YeyjGBeLyGvBdDcKqkeWHrFOy4vfDEsji5./7NSM20QpLHfbbW");
        testCases.put("asdf", "$2a$10$MlEMMm/4wtZBQi6CHVYAGOxvM28bYNmVFB5vXh3WyAIffrqNGvJ8.");

        BCryptHashingProvider provider = new BCryptHashingProvider();
        for (Map.Entry<String,String>  testCase : testCases.entrySet()) {
            assertTrue(provider.verify(testCase.getKey(), testCase.getValue()));
        }
    }
}
