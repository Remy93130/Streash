package streash.vars.functions.sources;

import java.util.Iterator;
import java.util.Random;

import org.json.JSONArray;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;

public class RandomStreamFunc extends AbstractFunction{
	
	public RandomStreamFunc() {
		super(3);
	}
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof Number && args[1] instanceof Number && args[2] instanceof Number) {
			Number a = Number.requireNonFloat((Number) args[0], "Cannot use random() with floating Number");
			Number b = Number.requireNonFloat((Number) args[1], "Cannot use random() with floating Number");
			Number seed = Number.requireNonFloat((Number) args[2], "Cannot use random() with floating Number");
			
			return new Stream(a.getValue(), b.getValue(), seed.getValue());
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "random";
	}
	
	public static class Stream extends AbstractStreamVar implements NumberStreamVar{
		private long seed;
		private long from;
		private long to;
		private Iterator<Number> rand;
		
		public Stream(long from, long to, long seed) {
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
			return new Stream(from, to, seed);
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
		
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(new Number(from).getJSONObject());
			array.put(new Number(to).getJSONObject());
			array.put(new Number(seed).getJSONObject());
			return array;
		}
		
		@Override
		public String getJSONName() {
			return RandomStreamFunc.getJSONName();
		}
	}
}
