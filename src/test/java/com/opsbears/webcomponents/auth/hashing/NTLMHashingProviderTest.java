package com.opsbears.webcomponents.auth.hashing;

import org.junit.Test;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertTrue;

@ParametersAreNonnullByDefault
public class NTLMHashingProviderTest {
    @Test
    public void testHash() {
        List<String> testCases = new ArrayList<>();
        testCases.add("test");
        testCases.add("asdf");

        NTLMHashingProvider provider = new NTLMHashingProvider();
        for (String testCase : testCases) {
            String hash = provider.hash(testCase);
            assertNotSame(hash, testCase);
            assertTrue(provider.verify(testCase, hash));
        }
    }

    @Test
    public void testRaw() {
        Map<String, String> testCases = new HashMap<>();
        testCases.put("test", "0CB6948805F797BF2A82807973B89537");
        testCases.put("asdf", "E5810F3C99AE2ABB2232ED8458A61309");

        NTLMHashingProvider provider = new NTLMHashingProvider();
        for (Map.Entry<String,String> testCase : testCases.entrySet()) {
            assertEquals(testCase.getValue(), provider.raw(testCase.getKey()));
        }
    }
}
