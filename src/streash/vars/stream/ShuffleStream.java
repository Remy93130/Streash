package streash.vars.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import streash.vars.StreamVar;
import streash.vars.Value;

public class ShuffleStream implements StreamVar{
	private long seed;
	private StreamVar s;
	private Iterator<Value> it;
	
	private ShuffleStream(StreamVar s, long seed) {
		this.seed = seed;
		if (seed == -1)
			seed = new Random().nextLong();
		
		this.s = s.duplicate();
		ArrayList<Value> list = new ArrayList<Value>();
		while(this.s.hasNext())
			list.add(this.s.next());
		Collections.shuffle(list, new Random(seed));
		it = list.iterator();
	}
	@Override
	public boolean hasNext() {
		return it.hasNext();
	}
	
	@Override
	public Value next() {
		return it.next();
	}
	
	@Override
	public StreamVar duplicate() {
		return new ShuffleStream(s.duplicate(), seed);
	}
	
	public static StreamVar getVar(StreamVar s, long seed) {
		if (s instanceof NumberStreamVar)
			return new ShuffleNumberStream(s, seed);
		if (s instanceof StringStreamVar)
			return new ShuffleStringStream(s, seed);
		return null;
	}
	
	@Override
	public String getConsoleString() {
		return "Shuffled Stream of "+s.getConsoleString()+" with seed "+seed;
	}
	
	@Override
	public long len() {
		return s.len();
	}
	
	public static class ShuffleNumberStream extends ShuffleStream implements NumberStreamVar {
		public ShuffleNumberStream(StreamVar s, long seed) {
			super(s, seed);
		}
	}
	public static class ShuffleStringStream extends ShuffleStream implements NumberStreamVar {
		public ShuffleStringStream(StreamVar s, long seed) {
			super(s, seed);
		}
	}
}
