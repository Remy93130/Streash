package streash.vars.functions.use;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import org.json.JSONArray;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;
import streash.vars.stream.StringStreamVar;

public class Shuffle extends AbstractFunction{
	
	public Shuffle() {
		super(2);
	}
	
	/**
	 * 
	 * @return a new stream in which the values are in a random order
	 */
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar && args[1] instanceof Number) {
			Number.requireNonFloat((Number) args[1], "Needing an integer seed");
			return Stream.getVar((StreamVar) args[0], ((Number) args[1]).getValue());
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "shuffle";
	}
	
	public static class Stream extends AbstractStreamVar{
		private long seed;
		private StreamVar s;
		private Iterator<Value> it;
		
		private Stream(StreamVar s, long seed) {
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
			return new Stream(s.duplicate(), seed);
		}
		
		public static StreamVar getVar(StreamVar s, long seed) {
			if (s instanceof NumberStreamVar)
				return new NumberStream(s, seed);
			if (s instanceof StringStreamVar)
				return new StringStream(s, seed);
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
		
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(s.getJSONObject());
			array.put(new Number(seed).getJSONObject());
			return array;
		}
		
		@Override
		public String getJSONName() {
			return Shuffle.getJSONName();
		}
		
		public static class NumberStream extends Stream implements NumberStreamVar {
			public NumberStream(StreamVar s, long seed) {
				super(s, seed);
			}
		}
		public static class StringStream extends Stream implements StringStreamVar {
			public StringStream(StreamVar s, long seed) {
				super(s, seed);
			}
		}
	}
}
