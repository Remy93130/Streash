package streash.vars.functions.use;

import java.util.ArrayList;

import org.json.JSONArray;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;
import streash.vars.stream.StringStreamVar;

public class Slice extends AbstractFunction{
	
	public Slice() {
		super(3);
	}
	
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar && args[1] instanceof Number && args[2] instanceof Number) {
			Number a = Number.requireNonFloat((Number) args[1], "Cannot slice a stream with floating Number");
			Number b = Number.requireNonFloat((Number) args[2], "Cannot slice a stream with floating Number");
			StreamVar s = (StreamVar) args[0];
			
			return Stream.getVar(s, a.getValue(), b.getValue());
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "slice";
	}
	
	public static abstract class Stream extends AbstractStreamVar{
		private final long a;
		private final long b;
		private final boolean reversed;
		private final StreamVar s;
		private ArrayList<Value> slice;
		private long index;
		private long len;
		
		private Stream(StreamVar s, long a, long b) {
			if (a > b) { this.b = a; this.a = b; reversed = true; }
			else       { this.b = b; this.a = a; reversed = false; }

			this.s = s.duplicate();
			
			this.index = 0;
			this.len = 0;
			this.slice = new ArrayList<Value>();
			
			if (a < 0 || b < 0)
				throw new IllegalArgumentException("Illegal bounds for slice()");
			
			for (int i = 0; i < this.a; i++) {
				if (s.hasNext())
					s.next();
				else
					break;
			}
			
			for (int i = 0; i < this.b - this.a + 1; i++) {
				if (!(s.hasNext()))
					break;
				if (reversed)
					slice.add(0, s.next());
				else
					slice.add(s.next());
				len++;
			}
		}
		
		public static StreamVar getVar(StreamVar s, long a, long b) {
			if (s instanceof NumberStreamVar)
				return new NumberStream(s, a, b);
			if (s instanceof StringStreamVar)
				return new StringStream(s, a, b);
			return null;
		}
		
		@Override
		public StreamVar duplicate() {
			if (reversed)
				return getVar(s.duplicate(), b, a);
			return getVar(s.duplicate(), a, b);
		}

		@Override
		public boolean hasNext() {
			return (index < len);
		}
		
		@Override
		public Value next() {
			Value to = slice.get((int) index);
			index++;
			return to;
		}
		
		@Override
		public String getConsoleString() {
			return "Slice of "+s.getConsoleString()+" from the "+a+"th to the "+b+"th element";
		}
		
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(s.getJSONObject());
			if (reversed) {
				array.put(new Number(b).getJSONObject());
				array.put(new Number(a).getJSONObject());
			} else {
				array.put(new Number(a).getJSONObject());
				array.put(new Number(b).getJSONObject());
			}
			return array;
		}
		
		@Override
		public String getJSONName() {
			return Slice.getJSONName();
		}

		public static class NumberStream extends Stream implements NumberStreamVar {
			public NumberStream(StreamVar s, long a, long b) {
				super(s, a, b);
			}
		}
		public static class StringStream extends Stream implements StringStreamVar {
			public StringStream(StreamVar s, long a, long b) {
				super(s, a, b);
			}
		}
	}
}
