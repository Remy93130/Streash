package streash.vars.stream;

import java.util.ArrayList;
import java.util.stream.Stream;

import streash.vars.StreamVar;
import streash.vars.Value;

public class SliceStream implements StreamVar{
	private long a;
	private long b;
	private StreamVar s;
	
	public SliceStream(StreamVar s, long a, long b) {
		this.a = a;
		this.b = b;
		this.s = s.duplicate();
	}
	@Override
	public StreamVar duplicate() {
		return new SliceStream(s.duplicate(), a, b);
	}
	@Override
	public String getConsoleString() {
		return "Slice of "+s.getConsoleString()+" from the "+a+"th to the "+b+"th element";
	}
	@Override
	public long print() {
		if (a > b) {
			ArrayList<Value> array = new ArrayList<Value>();
			s.duplicate().getStream().skip(b).limit(a - b + 1).iterator().forEachRemaining(x->array.add(0, x));
			return array.stream().mapToLong(x -> {System.out.println(x); return 1L;}).sum();
		}
		return s.getStream().skip(a).limit(b - a + 1).mapToLong(x -> {System.out.println(x); return 1L;}).sum();
	}
	@Override
	public Stream<Value> getStream() {
		if (a > b)
			return s.getStream().skip(b).limit(a - b + 1);
		return s.getStream().skip(a).limit(b - a + 1);
	}
}
