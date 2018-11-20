package streash.vars.stream;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Random;

import streash.vars.StreamVar;
import streash.vars.Value;

public class ShuffleStream implements StreamVar{
	private long seed;
	private StreamVar s;
	
	public ShuffleStream(StreamVar s, long seed) {
		this.seed = seed;
		this.s = s.duplicate();
	}
	@Override
	public StreamVar duplicate() {
		return new ShuffleStream(s.duplicate(), seed);
	}
	@Override
	public String getConsoleString() {
		return "Shuffled Stream of "+s.getConsoleString()+" with seed "+seed;
	}
	@Override
	public long print() {
		List<Value> list = s.duplicate().getStream().collect(Collectors.toList());
		Collections.shuffle(list, new Random(seed));
		return list.stream().mapToLong(x -> {
			System.out.println(x.getConsoleString());
			return 1L;
		}).sum();
	}
	@Override
	public Stream<Value> getStream() {
		List<Value> list = s.duplicate().getStream().collect(Collectors.toList());
		Collections.shuffle(list, new Random(seed));
		return list.stream();
	}
	@Override
	public long len() {
		return s.len();
	}
}
