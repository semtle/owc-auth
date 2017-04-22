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
public class MD5CryptHashingProviderTest {
    @Test
    public void testHash() {
        List<String> testCases = new ArrayList<>();
        testCases.add("test");
        testCases.add("asdf");

        MD5CryptHashingProvider provider = new MD5CryptHashingProvider(new SecureRandomSaltGenerator());
        for (String testCase : testCases) {
            String hash = provider.hash(testCase);
            assertNotSame(hash, testCase);
            assertTrue(provider.verify(testCase, hash));
        }
    }

    @Test
    public void testVerify() {
        Map<String, String> testCases = new HashMap<>();
        testCases.put("test", "$1$hDvD8hS8$zKel.NMWo8UPSe8ijd22D0");
        testCases.put("asdf", "$1$T.Tjlcn6$QykhrRq3whW72vjgSqJKJ/");

        MD5CryptHashingProvider provider = new MD5CryptHashingProvider(new SecureRandomSaltGenerator());
        for (Map.Entry<String,String>  testCase : testCases.entrySet()) {
            assertTrue(provider.verify(testCase.getKey(), testCase.getValue()));
        }
    }
}
