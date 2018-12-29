package streash.vars.functions.sources;

import org.json.JSONArray;
import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;

public class Fibo extends AbstractFunction{
	
	public Fibo() {
		super(2);
	}
	
	/**
	 * 
	 * @return a stream containing the number Fibonacci starting with the values a
	 *         and b
	 */
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof Number && args[1] instanceof Number) {
			Number.requireNonFloat((Number) args[0], "Cannot use fibo() with floating Number");
			Number.requireNonFloat((Number) args[1], "Cannot use fibo() with floating Number"); 
			return new Stream(((Number) args[0]).getValue(), ((Number) args[1]).getValue());
		}
		super.illegalTypesException();
		return null;
	}

	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "fibo";
	}
	
	public static class Stream extends AbstractStreamVar implements NumberStreamVar{
		private final long first;
		private final long second;
		private Number current1;
		private Number current2;
		
		
		public Stream(long first, long second) {
			this.first = first;
			this.second = second;
			this.current1 = new Number(first);
			this.current1 = new Number(second);
		}
		
		@Override
		public String getConsoleString() {
			return "Fibonacci sequence with "+first+" and "+second+" as firsts terms";
		}
		
		@Override
		public StreamVar duplicate() {
			return new Stream(first, second);
		}
		
		@Override
		public boolean hasNext() {
			return true;
		}
		
		@Override
		public Value next() {
			Number to = current1.add(current2);
			current1 = current2;
			current2 = to;
			return to;
		}
		
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(new Number(first).getJSONObject());
			array.put(new Number(second).getJSONObject());
			return array;
		}
		
		@Override
		public String getJSONName() {
			return Fibo.getJSONName();
		}
	}
}
