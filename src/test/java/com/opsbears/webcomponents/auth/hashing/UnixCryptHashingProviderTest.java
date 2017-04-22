package com.opsbears.webcomponents.auth.hashing;

import org.junit.Test;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertTrue;

@ParametersAreNonnullByDefault
public class UnixCryptHashingProviderTest {
    @Test
    public void testHash() {
        List<String> testCases = new ArrayList<>();
        testCases.add("test");
        testCases.add("asdf");

        UnixCryptHashingProvider provider = new UnixCryptHashingProvider();
        for (String testCase : testCases) {
            String hash = provider.hash(testCase);
            assertNotSame(hash, testCase);
            assertTrue(provider.verify(testCase, hash));
        }
    }

    @Test
    public void testVerify() {
        Map<String, String> testCases = new HashMap<>();
        testCases.put("test", "HUSViVkSlO2r.");
        testCases.put("asdf", "XD8aCYxtrk3v.");

        UnixCryptHashingProvider provider = new UnixCryptHashingProvider();
        for (Map.Entry<String,String>  testCase : testCases.entrySet()) {
            assertTrue(provider.verify(testCase.getKey(), testCase.getValue()));
        }
    }
}
