package streash.vars.functions.primitive;

import org.json.JSONArray;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;

public class Div extends AbstractFunction{

	public Div() {
		super(2);
	}

	/**
	 * Caculate the quotient of the two values in the function
	 * 
	 * @return the quotient of the two values if Number.
	 */
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof Number && args[1] instanceof Number)
			return ((Number) args[0]).div((Number) args[1]);
		if (args[0] instanceof NumberStreamVar && args[1] instanceof NumberStreamVar)
			return new Stream((StreamVar) args[0], (StreamVar) args[1]);
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "div";
	}
	
	public static class Stream extends AbstractStreamVar implements NumberStreamVar{
		private StreamVar s1;
		private StreamVar s2;
		
		public Stream(StreamVar s1, StreamVar s2) {
			this.s1 = s1.duplicate();
			this.s2 = s2.duplicate();
		}
		
		@Override
		public StreamVar duplicate() {
			return new Stream(s1, s2);
		}
		
		@Override
		public String getConsoleString() {
			return "Division of "+s1.getConsoleString()+" and "+s2.getConsoleString();
		}
		
		@Override
		public boolean hasNext() {
			return (s1.hasNext() || s2.hasNext());
		}
		
		@Override
		public Value next() {
			if (s1.hasNext() && s2.hasNext())
				return ((Number) s1.next()).div((Number) s2.next());
			if (s1.hasNext())
				return s1.next();
			if (s2.hasNext())
				return (new Number(1)).div((Number) s2.next());
			throw new IllegalStateException("No such element");
		}
		
		@Override
		public long len() {
			long l1, l2;
			l1 = s1.len();
			l2 = s2.len();
			if (l1 > l2)
				return l1;
			return l2;
		}
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(s1.getJSONObject());
			array.put(s2.getJSONObject());
			return array;
		}
		@Override
		public String getJSONName() {
			return Div.getJSONName();
		}
	}

}
