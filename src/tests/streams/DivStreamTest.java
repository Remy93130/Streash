package tests.streams;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import streash.vars.Number;
import streash.vars.stream.DivStream;
import streash.vars.stream.InfiniteIntegersStream;
import streash.vars.stream.SliceStream.SliceNumberStream;

class DivStreamTest {

	@Test
	void testGetConsoleString() {
		SliceNumberStream s1 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 1, 10);
		SliceNumberStream s2 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 11, 20);
		DivStream s = new DivStream(s1, s2);
		assertEquals(
				"Division of Slice of Numbers from 0 to plus infinity from the 1th to the 10th element and Slice of Numbers from 0 to plus infinity from the 11th to the 20th element",
				s.getConsoleString());
	}
	
	@Test
	void testAverage() {
		SliceNumberStream s1 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 1, 5);
		SliceNumberStream s2 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 1, 5);
		DivStream s = new DivStream(s1, s2);
		assertEquals(new Number(1), s.average());
	}
	
	@Test
	void testMinmax() {
		SliceNumberStream s1 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 3, 30);
		SliceNumberStream s2 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 3, 30);
		DivStream s = new DivStream(s1, s2);
		assertEquals(new Number(1), s.min());
		assertEquals(new Number(1), s.max());
	}
	
	@Test
	void testLen() {
		SliceNumberStream s1 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 1, 10);
		SliceNumberStream s2 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 11, 20);
		DivStream s = new DivStream(s1, s2);
		assertEquals(10, s.len());
	}
}
