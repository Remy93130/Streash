package tests.streams;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import streash.vars.stream.FiboStream;

class FiboStreamTest {

	@Test
	void testGetConsoleString() {
		FiboStream s = new FiboStream(5, 10);
		assertEquals("Fibonacci sequence with 5 and 10 as firsts terms", s.getConsoleString());
	}
}
