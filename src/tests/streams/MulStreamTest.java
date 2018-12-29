package tests.streams;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import streash.vars.Number;
import streash.vars.Value;
import streash.vars.stream.InfiniteIntegersStream;
import streash.vars.stream.MulStream.MulNumberStream;
import streash.vars.stream.SliceStream.SliceNumberStream;

class MulStreamTest {

	@Test
	void testLen() {
		SliceNumberStream s1 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 0, 9);
		SliceNumberStream s2 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 10, 19);
		MulNumberStream s = new MulNumberStream(s1, s2);
		assertEquals(10, s.len());
	}

	@Test
	void testMinMax() {
		SliceNumberStream s1 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 0, 9);
		SliceNumberStream s2 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 10, 19);
		MulNumberStream s = new MulNumberStream(s1, s2);
		assertEquals(new Number(0), s.min());
		assertEquals(new Number(171), s.max());
	}

	@Test
	void testAverage() {
		SliceNumberStream s1 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 0, 4);
		SliceNumberStream s2 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 0, 4);
		MulNumberStream s = new MulNumberStream(s1, s2);
		assertEquals(new Number(6), s.average());
	}

	@Test
	void testCollect() {
		SliceNumberStream s1 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 0, 4);
		SliceNumberStream s2 = new SliceNumberStream(new InfiniteIntegersStream(0, false), 0, 4);
		MulNumberStream s = new MulNumberStream(s1, s2);
		List<Value> v = s.collect();
		assertEquals(5, v.size());
		assertEquals(new Number(16), v.get(4));
	}
}
