package tests.streams;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import streash.vars.Number;
import streash.vars.stream.ConcatStream.ConcatNumberStream;
import streash.vars.stream.InfiniteIntegersStream;
import streash.vars.stream.SliceStream.SliceNumberStream;

/**
 * Check why don't work correctly
 * @author remy
 *
 */
class ConcatStreamTest {

	@Test
	void testGetConsoleString() {
		SliceNumberStream s1 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 0, 9);
		SliceNumberStream s2 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 10, 19);
		ConcatNumberStream s = new ConcatNumberStream(s1, s2);
		System.out.println(s.getConsoleString());
		assertEquals(
				"Slice of Numbers from 0 to plus infinity from the 0th to the 9th element concatened with Slice of Numbers from 0 to plus infinity from the 0th to the 9th element",
				s.getConsoleString());
	}

	@Test
	void testPrint() {
		SliceNumberStream s1 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 0, 9);
		SliceNumberStream s2 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 10, 19);
		ConcatNumberStream s = new ConcatNumberStream(s1, s2);
		assertEquals(20, s.print());
	}

	@Test
	void testLen() {
		SliceNumberStream s1 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 0, 9);
		SliceNumberStream s2 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 10, 19);
		ConcatNumberStream s = new ConcatNumberStream(s1, s2);
		assertEquals(20, s.len());
	}

	@Test
	void testAverage() {
		SliceNumberStream s1 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 0, 10);
		SliceNumberStream s2 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 11, 15);
		ConcatNumberStream s = new ConcatNumberStream(s1, s2);
		assertEquals(new Number(5), s.average());
	}
	
	@Test
	void testMinMax() {
		SliceNumberStream s1 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 0, 9);
		SliceNumberStream s2 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 10, 19);
		ConcatNumberStream s = new ConcatNumberStream(s1, s2);
		assertEquals(new Number(0), s.min());
		assertEquals(new Number(19), s.max());
	}

}
