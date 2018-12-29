package streash.vars.functions.sources;

import org.json.JSONArray;

import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;

public class Prime extends AbstractFunction{
	
	public Prime() {
		super(1);
	}
	
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof Number) {
			Number n = (Number) args[0];
			if (!n.isInteger()) { throw new IllegalArgumentException("Cannot use prime() with a floating Number"); }
			if (n.getValue() < 1) { throw new IllegalArgumentException("Cannot call prime with a null or negative number"); }
			return new Stream(n.getValue());
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "prime";
	}
	
	public static class Stream extends AbstractStreamVar implements NumberStreamVar{
		private long from;
		private Number current;
		
		public Stream(long n) {
			this.from = n;
			this.current = getNextPrime(n);
		}
		
		public boolean hasNext() {
			return true;
		}
		
		public Value next() {
			Value to = current;
			current = getNextPrime(current.getValue());
			return to;
		}
		
		private Number getNextPrime(long old) {
			old++;

		    for(int i=2; i < old; i++) {
		        if(old % i ==0  ) {
		            old++;
		            i=2;
		        }
		        else
		            continue;
		    }
		    return new Number(old);
		}
		
		@Override
		public String getConsoleString() {
			return "Prime numbers starting to "+from;
		}
		
		@Override
		public StreamVar duplicate() {
			return new Stream(from);
		}
		
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(new Number(from).getJSONObject());
			return array;
		}
		
		@Override
		public String getJSONName() {
			return Prime.getJSONName();
		}
	}
}
