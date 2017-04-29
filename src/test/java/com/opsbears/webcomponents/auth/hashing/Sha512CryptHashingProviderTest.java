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
public class Sha512CryptHashingProviderTest {
    @Test
    public void testHash() {
        List<String> testCases = new ArrayList<>();
        testCases.add("test");
        testCases.add("asdf");

        Sha512CryptHashingProvider provider = new Sha512CryptHashingProvider(new SecureRandomSaltGenerator());
        for (String testCase : testCases) {
            String hash = provider.hash(testCase);
            assertNotSame(hash, testCase);
            assertTrue(provider.verify(testCase, hash));
        }
    }

    @Test
    public void testVerify() {
        Map<String, String> testCases = new HashMap<>();
        testCases.put("test", "$6$N6xRKPd8dOpOa93T$MUJhQAw7VedAsm6jzgNoJFjRYizJAtAgmGO9OJp60fA7CXnELLjBmVQxGzvVHODfcKSET.wQTFaQxB5tZO3lU0");
        testCases.put("asdf", "$6$GLxIsuOejT4Sz8U6$Kybr7yM8qRs9mv/SVM/GWhO6P9OysGEBY5Ibfs15duBycvKSuG/i4yGK3XKXv2IjAlJE/2th4rrJe20WE8fxc/");

        Sha512CryptHashingProvider provider = new Sha512CryptHashingProvider(new SecureRandomSaltGenerator());
        for (Map.Entry<String,String>  testCase : testCases.entrySet()) {
            assertTrue(provider.verify(testCase.getKey(), testCase.getValue()));
        }
    }
}
