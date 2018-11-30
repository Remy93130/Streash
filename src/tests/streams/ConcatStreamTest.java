package tests.streams;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import streash.vars.stream.ConcatStream;
import streash.vars.stream.InfiniteIntegersStream;
import streash.vars.stream.SliceStream;

class ConcatStreamTest {

	@Test
	void testGetConsoleString() {
		SliceStream s1 = new SliceStream(new InfiniteIntegersStream(0, false), 0, 9);
		SliceStream s2 = new SliceStream(new InfiniteIntegersStream(0, false), 10, 19);
		ConcatStream f = new ConcatStream(s1, s2);
		assertEquals(
				"Slice of Numbers from 0 to plus infinity from the 0th to the 9th element concatened with Slice of Numbers from 0 to plus infinity from the 10th to the 19th element",
				f.getConsoleString());
	}

	@Test
	void testPrint() {
		SliceStream s1 = new SliceStream(new InfiniteIntegersStream(0, false), 0, 9);
		SliceStream s2 = new SliceStream(new InfiniteIntegersStream(0, false), 10, 19);
		ConcatStream f = new ConcatStream(s1, s2);
		assertEquals(20, f.print());
	}

	@Test
	void testLen() {
		SliceStream s1 = new SliceStream(new InfiniteIntegersStream(0, false), 0, 9);
		SliceStream s2 = new SliceStream(new InfiniteIntegersStream(0, false), 10, 19);
		ConcatStream f = new ConcatStream(s1, s2);
		assertEquals(20, f.len());
	}

}
