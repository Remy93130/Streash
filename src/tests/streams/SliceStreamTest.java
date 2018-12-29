package tests.streams;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import streash.vars.Number;
import streash.vars.stream.InfiniteIntegersStream;
import streash.vars.stream.SliceStream.SliceNumberStream;

class SliceStreamTest {

	@Test
	void testGetConsoleString() {
		InfiniteIntegersStream s0 = new InfiniteIntegersStream(0, false);
		SliceNumberStream s = new SliceNumberStream(s0, 0, 9);
		assertEquals("Slice of Numbers from 0 to plus infinity from the 0th to the 9th element", s.getConsoleString());
	}

	@Test
	void testPrint() {
		InfiniteIntegersStream s0 = new InfiniteIntegersStream(0, false);
		SliceNumberStream s = new SliceNumberStream(s0, 0, 9);
		assertEquals(10, s.print());
	}

	@Test
	void testLen() {
		InfiniteIntegersStream s0 = new InfiniteIntegersStream(0, false);
		SliceNumberStream s = new SliceNumberStream(s0, 0, 9);
		assertEquals(10, s.len());
	}

	@Test
	void testMinMax() {
		InfiniteIntegersStream s0 = new InfiniteIntegersStream(0, false);
		SliceNumberStream s = new SliceNumberStream(s0, 0, 9);
		assertEquals(new Number(9), s.max());
		assertEquals(new Number(0), s.min());
	}

	@Test
	void testSum() {
		InfiniteIntegersStream s0 = new InfiniteIntegersStream(0, false);
		SliceNumberStream s = new SliceNumberStream(s0, 0, 9);
		assertEquals(new Number(45), s.sum());
	}
}
