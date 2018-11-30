package tests.streams;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import streash.vars.stream.InfiniteIntegersStream;
import streash.vars.stream.SliceStream;

class SliceStreamTest {

	@Test
	void testGetConsoleString() {
		InfiniteIntegersStream s0 = new InfiniteIntegersStream(0, false);
		SliceStream s = new SliceStream(s0, 0, 9);
		assertEquals("Slice of Numbers from 0 to plus infinity from the 0th to the 9th element", s.getConsoleString());
	}

	@Test
	void testPrint() {
		InfiniteIntegersStream s0 = new InfiniteIntegersStream(0, false);
		SliceStream s = new SliceStream(s0, 0, 9);
		assertEquals(10, s.print());
	}
}
