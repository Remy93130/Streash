package streash.vars.functions.sources;

import org.json.JSONArray;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;

public class InfiniteIntegers extends AbstractFunction{
	private boolean reversed;
	
	public InfiniteIntegers(boolean reversed) {
		super(1);
		this.reversed = reversed;
	}
	
	/**
	 * 
	 * @return a stream containing the number from the value to the infinite or from
	 *         minus infinite to the value
	 */
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof Number) {
			Number n = (Number) args[0];
			if (!n.isInteger()) { throw new IllegalArgumentException("Cannot use integer() with a floating Number"); }
			return new Stream(n.getValue(), reversed);
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return getJSONName(reversed);
	}
	
	public static String getJSONName(boolean reversed) {
		if (reversed)
			return "revintegers";
		return "integers";
	}
	
	public static class Stream extends AbstractStreamVar implements NumberStreamVar{
		private long from;
		private boolean reversed;
		private Number current;
		
		public Stream(long n, boolean reversed) {
			this.from = n;
			this.reversed = reversed;
			if (reversed)
				current = new Number(n + 1);
			else
				current = new Number(n - 1);
		}
		
		public boolean hasNext() {
			return true;
		}
		
		public Value next() {
			if (reversed)
				current = current.sub(new Number(1));
			else
				current = current.add(new Number(1));
			
			return current;
		}
		
		@Override
		public String getConsoleString() {
			return "Numbers from "+from+" to "+(reversed ? "less" : "plus")+" infinity";
		}
		
		@Override
		public StreamVar duplicate() {
			return new Stream(from, reversed);
		}
		
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(new Number(from).getJSONObject());
			return array;
		}
		
		@Override
		public String getJSONName() {
			return InfiniteIntegers.getJSONName(reversed);
		}
	}
}
