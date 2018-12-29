package streash.vars.functions.use;

import org.json.JSONArray;

import streash.vars.CharChain;
import streash.vars.LambdaVar;
import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;
import streash.vars.stream.StringStreamVar;

public class Map extends AbstractFunction{
	
	public Map() {
		super(2);
	}
	
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar && args[1] instanceof LambdaVar) {
			return Stream.getVar((StreamVar) args[0], (LambdaVar) args[1]);
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "map";
	}
	
	public static abstract class Stream extends AbstractStreamVar{
		private LambdaVar lambda;
		private StreamVar s;
		
		private Stream(StreamVar s, LambdaVar lambda) {
			this.lambda = lambda;
			this.s = s.duplicate();
		}
		
		@Override
		public boolean hasNext() {
			return s.hasNext();
		}
		
		@Override
		public Value next() {
			return lambda.produce(s.next());
		}
		
		@Override
		public StreamVar duplicate() {
			return getVar(s.duplicate(), lambda);
		}
		
		public static StreamVar getVar(StreamVar s, LambdaVar lambda) {
			StreamVar t = s.duplicate();
			if (t.hasNext()) {
				Value v = lambda.produce(t.next());
				if (v instanceof Number)
					return new NumberStream(s, lambda);
				else if (v instanceof CharChain)
					return new StringStream(s, lambda);
				else throw new IllegalArgumentException("Return type of mapping function is not a primitive");
			}
			throw new IllegalArgumentException("Trying to map an empty stream");
		}
		
		@Override
		public String getConsoleString() {
			return "Mapped stream of "+s.getConsoleString();
		}
		
		@Override
		public long len() {
			return s.len();
		}
		
		@Override
		public JSONArray getArgs() {
			JSONArray array = new JSONArray();
			array.put(s.getJSONObject());
			array.put(lambda.getJSONObject());
			return array;
		}
		
		@Override
		public String getJSONName() {
			return Map.getJSONName();
		}
		
		public static class NumberStream extends Stream implements NumberStreamVar {
			public NumberStream(StreamVar s, LambdaVar lambda) {
				super(s, lambda);
			}
		}
		public static class StringStream extends Stream implements StringStreamVar {
			public StringStream(StreamVar s, LambdaVar lambda) {
				super(s, lambda);
			}
		}
	}
}