package streash.vars.stream;

import java.util.Random;
import java.util.stream.Stream;

import streash.vars.StreamVar;
import streash.vars.Number;
import streash.vars.Value;

public class RandomStream implements NumberStreamVar{
	private long seed;
	private long from;
	private long to;
	
	public RandomStream(long from, long to, long seed) {
		if (from > to)
			throw new IllegalArgumentException("Cannot use random() with left margin higher than right margin");
		this.from = from;
		this.to= to;
		this.seed = seed;
		if (seed == -1)
			this.seed = new Random().nextLong();
	}
	
	@Override
	public StreamVar duplicate() {
		return new RandomStream(from, to, seed);
	}
	@Override
	public Stream<Value> getStream() {
		return new Random(seed).ints((int) from, (int) to).mapToObj(x -> new Number(x));
	}
	@Override
	public String getConsoleString() {
		return "Random Stream of ints from "+from+" to "+to+" with seed : "+seed;
	}
	@Override
	public long print() {
		return this.duplicate().getStream().mapToLong(x ->{
			System.out.println(x.getConsoleString());
			return 1L;
		}).sum();
	}
}
