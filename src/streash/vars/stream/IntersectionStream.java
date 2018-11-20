package streash.vars.stream;

import java.util.stream.Stream;

import streash.vars.StreamVar;
import streash.vars.Value;

public class IntersectionStream implements StreamVar{
	private StreamVar s1;
	private StreamVar s2;
	
	public IntersectionStream(StreamVar s1, StreamVar s2) {
		if (!(s1.getType().equals(s2.getType())))
			throw new IllegalStateException("Cannot intersect two Streams of different generics");
		this.s1 = s1.duplicate();
		this.s2 = s2.duplicate();
	}
	
	@Override
	public StreamVar duplicate() {
		return new IntersectionStream(s1, s2);
	}
	@Override
	public String getConsoleString() {
		return "Intersection of "+s1.getConsoleString()+" and "+s2.getConsoleString();
	}
	@Override
	public String getType() {
		return s1.getType();
	}
	@Override
	public long print() {
		return s1.duplicate()
				.getStream()
				.filter(
				x -> s2.duplicate()
					.getStream()
					.anyMatch(y -> x.equals(y)))
				.mapToLong(z -> {
					System.out.println(z.getConsoleString());
					return 1L;
				}).sum();
	}
	@Override
	public Stream<Value> getStream() {
		return s1.duplicate().getStream().filter(x -> s2.duplicate().getStream().anyMatch(y -> x.equals(y)));
	}
}
