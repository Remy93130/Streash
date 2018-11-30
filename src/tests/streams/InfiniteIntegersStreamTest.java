package tests.streams;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import streash.vars.stream.InfiniteIntegersStream;

class InfiniteIntegersStreamTest {

	@Test
	void testGetConsoleString() {
		InfiniteIntegersStream s = new InfiniteIntegersStream(-5, false);
		assertEquals("Numbers from -5 to plus infinity", s.getConsoleString());
	}
}
