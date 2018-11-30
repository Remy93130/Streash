package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import streash.vars.CharChain;

class CharChainTest {

    @Test
    void testConcat() {
        CharChain c1 = new CharChain("John");
        CharChain c2 = new CharChain("Doe");
        CharChain except = new CharChain("JohnDoe");
        assertTrue(c1.concat(c2).equals(except));
    }

    @Test
    void testSubSuffix() {
        CharChain c1 = new CharChain("azertyuiop");
        CharChain c2 = new CharChain("uiop");
        CharChain except = new CharChain("azerty");
        CharChain bad = new CharChain("John");
        assertTrue(c1.subSuffix(c2).equals(except));
        assertThrows(IllegalArgumentException.class, () -> {
            c1.subSuffix(bad);
        });
    }
    
    @Test
    void testTime() {
        CharChain base = new CharChain("One");
        CharChain except = new CharChain("OneOneOne");
        assertTrue(base.time(3).equals(except));
        assertThrows(IllegalArgumentException.class, () -> {
            base.time(-5);
        });
    }
}
