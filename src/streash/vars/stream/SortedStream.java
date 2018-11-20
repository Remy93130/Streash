package streash.vars.stream;

import java.util.stream.Stream;

import streash.vars.StreamVar;
import streash.vars.Value;

public class SortedStream implements StreamVar{
	private StreamVar s;
	
	public SortedStream(StreamVar s) {
		this.s = s.duplicate();
	}
	
	@Override
	public StreamVar duplicate() {
		return new SortedStream(s.duplicate());
	}
	@Override
	public String getConsoleString() {
		return "Sorted stream of "+s.getConsoleString();
	}
	@Override
	public Stream<Value> getStream() {
		return s.duplicate().getStream().sorted();
	}
	@Override
	public String getType() {
		return s.getType();
	}
	@Override
	public long print() {
		return s.duplicate().getStream().sorted().mapToLong(x -> {
			System.out.println(x.getConsoleString());
			return 1L;
		}).sum();
	}
	@Override
	public long len() {
		return s.len();
	}
}
