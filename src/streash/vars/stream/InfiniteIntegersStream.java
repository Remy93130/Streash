package streash.vars.stream;

import java.util.stream.Stream;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;

public class InfiniteIntegersStream implements NumberStreamVar{
	private long from;
	private boolean reversed;
	private Stream<Value> stream;
	
	public InfiniteIntegersStream(long n, boolean reversed) {
		this.stream = (Stream.iterate((Value) new Number(n), i -> ((Number)i).add(new Number(reversed ? -1 : 1))));
		this.from = n;
		this.reversed = reversed;
	}
	
	@Override
	public String getConsoleString() {
		return "Numbers from "+from+" to "+(reversed ? "less" : "plus")+" infinity";
	}
	@Override
	public StreamVar duplicate() {
		return new InfiniteIntegersStream(from, reversed);
	}
	@Override
	public long print() {
		return stream.mapToLong(i -> {System.out.println(i); return 1L; }).sum();
	}
	
	@Override
	public Stream<Value> getStream() {
		return stream;
	}
}
