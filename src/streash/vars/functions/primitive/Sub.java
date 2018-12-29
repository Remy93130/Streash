package streash.vars.functions.primitive;

import org.json.JSONArray;

import streash.vars.CharChain;
import streash.vars.Number;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;
import streash.vars.stream.StringStreamVar;

public class Sub extends AbstractFunction{

	public Sub() {
		super(2);
	}
	
	/**
	 * Caculate the difference of the two values in the function
	 * 
	 * @return the difference of the two values if Number. Return the first
	 *         CharChain without the second CharChain at the end
	 */
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof Number && args[1] instanceof Number)
			return ((Number) args[0]).sub((Number) args[1]);
		if (args[0] instanceof CharChain && args[1] instanceof CharChain)
			return ((CharChain) args[0]).subSuffix((CharChain) args[1]);
		if (args[0] instanceof StreamVar && args[1] instanceof StreamVar) {
			StreamVar to = Stream.getVar((StreamVar) args[0], (StreamVar) args[1]);
			if (to == null)
				super.illegalTypesException();
			return to;
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "sub";
	}
	
	public static abstract class Stream extends AbstractStreamVar{
		private StreamVar s1;
		private StreamVar s2;
		
		private Stream(StreamVar s1, StreamVar s2) {
			this.s1 = s1.duplicate();
			this.s2 = s2.duplicate();
		}
		
		public static StreamVar getVar(StreamVar s1, StreamVar s2) {
			if (s1 instanceof NumberStreamVar && s2 instanceof NumberStreamVar)
				return new NumberStream(s1, s2);
			if (s1 instanceof StringStreamVar && s2 instanceof StringStreamVar)
				return new StringStream(s1, s2);
			return null;
		}
		
		@Override
		public StreamVar duplicate() {
			return getVar(s1, s2);
		}
		
		@Override
		public String getConsoleString() {
			return s1.getConsoleString()+" and "+s2.getConsoleString();
		}
		
		@Override
		public boolean hasNext() {
			return (s1.hasNext() || s2.hasNext());
		}
		
		@Override
		public Value next() {
			if (s1.hasNext() && s2.hasNext())
				return operate(s1.next(), s2.next());
			if (s1.hasNext())
				return s1.next();
			if (s2.hasNext())
				return operate(null, s2.next());
			throw new IllegalStateException("No such element");
		}
		
		public abstract Value operate(Value v1, Value v2);
		
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
			return Sub.getJSONName();
		}
		
		public static class NumberStream extends Stream implements NumberStreamVar {
			public NumberStream(StreamVar s1, StreamVar s2) {
				super(s1, s2);
			}
			@Override
			public String getConsoleString() {
				return "Substraction of "+super.toString();
			}
			
			@Override
			public Value operate(Value v1, Value v2) {
				if (v1 == null)
					v1 = new Number(0);
				return ((Number)v1).sub((Number) v2);
			}
		}
		public static class StringStream extends Stream implements StringStreamVar {
			public StringStream(StreamVar s1, StreamVar s2) {
				super(s1, s2);
			}
			@Override
			public String getConsoleString() {
				return "Substraction by suffix of "+super.toString();
			}
			
			@Override
			public Value operate(Value v1, Value v2) {
				if (v1 == null)
					v1 = new CharChain("");
				return ((CharChain)v1).subSuffix((CharChain) v2);
			}
		}
	}
}
