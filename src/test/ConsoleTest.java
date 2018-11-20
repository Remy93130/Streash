package test;

import static org.junit.jupiter.api.Assertions.*;
import javax.naming.directory.NoSuchAttributeException;
import org.junit.jupiter.api.Test;
import streash.console.Console;

class ConsoleTest {

    @Test
    void testcomputeCommand() throws NoSuchAttributeException {
        Console c = new Console();
        c.computeCommand();
        fail("Not yet implemented");
        
    }

}
