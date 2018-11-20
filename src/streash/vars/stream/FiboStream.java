package streash.vars.stream;

import java.util.stream.Stream;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;

public class FiboStream implements StreamVar{
	private long from;
	private long to;
	private Stream<Value> stream;
	
	public FiboStream(long from, long to) {
		this.from = from;
		this.to = to;
		this.stream = Stream.iterate(new long[] {from, to}, p->new long[] {p[1], p[0]+p[1] })
			.map(l -> { return new Number(l[0]); });
	}
	
	@Override
	public String getConsoleString() {
		return "Fibonacci sequence with "+from+" and "+to+" as firsts terms";
	}
	@Override
	public StreamVar duplicate() {
		return new FiboStream(from, to);
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
