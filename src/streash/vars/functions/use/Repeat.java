package streash.vars.functions.use;

import org.json.JSONArray;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;
import streash.vars.stream.StringStreamVar;

public class Repeat extends AbstractFunction{
	
	public Repeat() {
		super(2);
	}
	
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar && args[1] instanceof Number) {
			Number a = ((Number) args[1]);
			StreamVar s = (StreamVar) args[0];
			
			return Stream.getVar(s, a);
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "repeat";
	}
	
	public static class Stream extends AbstractStreamVar{
		private float repeat;
		private Number origin;
		private StreamVar s;
		private int index = 0;
		private int point = 0;
		private int len;
		private StreamVar current;
		
		private Stream(StreamVar s, Number a) {
			this.repeat = origin.getFloatingValue();
			this.origin = a;
			if (repeat < 0)
				throw new IllegalArgumentException("Cannot use repeat() with a negative value");
			this.s = s.duplicate();
			this.len = (int) s.len();
			this.current = s.duplicate();
		}
		
		public static StreamVar getVar(StreamVar s, Number a) {
			if (s instanceof NumberStreamVar)
				return new NumberStream(s, a);
			if (s instanceof StringStreamVar)
				return new StringStream(s, a);
			return null;
		}
	 	
		@Override
		public StreamVar duplicate() {
			return new Stream(s.duplicate(), origin);
		}
		
		@Override
		public String getConsoleString() {
			return s.getConsoleString()+" repeated "+repeat+" times";
		}
		
		@Override
		public boolean hasNext() {
			if (index <= (int) repeat) {
				if (current.hasNext())
					return true;
				current = s.duplicate();
				index++;
				point = 0;
				return hasNext();
			}
			if (((float) point / len) >= repeat - (int) repeat)
				return false;
			if (current.hasNext())
				return true;
			return false;
		}
		
		@Override
		public Value next() {
			if (hasNext()) {
				point++;
				return current.next();
			}
			throw new IllegalStateException("No such element");
		}
		
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(s.getJSONObject());
			array.put(origin.getJSONObject());
			return array;
		}
		
		@Override
		public String getJSONName() {
			return Repeat.getJSONName();
		}
		
		public static class NumberStream extends Stream implements NumberStreamVar {
			public NumberStream(StreamVar s, Number a) {
				super(s, a);
			}
		}
		public static class StringStream extends Stream implements NumberStreamVar {
			public StringStream(StreamVar s, Number a) {
				super(s, a);
			}
		}
	}
}
