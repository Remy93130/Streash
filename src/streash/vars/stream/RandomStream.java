package streash.vars.stream;

import java.util.Iterator;
import java.util.Random;

import streash.vars.StreamVar;
import streash.vars.Number;
import streash.vars.Value;

public class RandomStream implements NumberStreamVar{
	private long seed;
	private long from;
	private long to;
	private Iterator<Number> rand;
	
	public RandomStream(long from, long to, long seed) {
		if (from > to)
			throw new IllegalArgumentException("Cannot use random() with left margin higher than right margin");
		this.from = from;
		this.to = to;
		this.seed = seed;
		if (seed == -1)
			this.seed = new Random().nextLong();
		this.rand = new Random(seed).ints((int) from, (int) to).mapToObj(x -> new Number(x)).iterator();
	}
	
	@Override
	public StreamVar duplicate() {
		return new RandomStream(from, to, seed);
	}
	
	@Override
	public boolean hasNext() {
		return rand.hasNext();
	}
	
	@Override
	public Value next() {
		return rand.next();
	}
	
	@Override
	public String getConsoleString() {
		return "Random Stream of ints from "+from+" to "+to+" with seed : "+seed;
	}
}
