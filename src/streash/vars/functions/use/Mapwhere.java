package streash.vars.functions.use;

import org.json.JSONArray;

import streash.vars.CharChain;
import streash.vars.LambdaVar;
import streash.vars.Number;
import streash.vars.Primitive;
import streash.vars.StreamVar;
import streash.vars.Value;
import streash.vars.functions.AbstractFunction;
import streash.vars.stream.AbstractStreamVar;
import streash.vars.stream.NumberStreamVar;
import streash.vars.stream.Operator;
import streash.vars.stream.StringStreamVar;

public class Mapwhere extends AbstractFunction{
	
	public Mapwhere() {
		super(5);
	}
	
	@Override
	public Value evaluate() {
		super.evaluate();
		Value[] args = super.getArgs();
		if (args[0] instanceof StreamVar && 
			args[1] instanceof LambdaVar &&
			args[2] instanceof CharChain &&
			args[3] instanceof Primitive &&
			args[4] instanceof LambdaVar) {
			return Stream.getVar(
					(StreamVar) args[0],
					(LambdaVar) args[1],
					(CharChain) args[2],
					(Value) args[3],
					(LambdaVar) args[4]);
		}
		super.illegalTypesException();
		return null;
	}
	
	@Override
	public String getName() {
		return getJSONName();
	}
	
	public static String getJSONName() {
		return "mapwhere";
	}
	
	public static abstract class Stream extends AbstractStreamVar{
		private LambdaVar filter;
		private LambdaVar map;
		private Operator operator;
		private Value operand;
		private StreamVar s;
		
		private Stream(StreamVar s, LambdaVar filter, CharChain operator, Value operand, LambdaVar map) {
			this.filter = filter;
			this.map = map;
			this.operator = Operator.getInstance(operator.toString());
			this.operand = operand;
			this.s = s.duplicate();
		}
		
		@Override
		public boolean hasNext() {
			return s.hasNext();
		}
		
		@Override
		public Value next() {
			Value next = s.next();
			if (operator.match(filter.produce(next), operand))
				return map.produce(next);
			return next;
		}
		
		@Override
		public StreamVar duplicate() {
			return getVar(s.duplicate(), filter, new CharChain(operator.getCall()), operand, map);
		}
		
		public static StreamVar getVar(StreamVar s, LambdaVar filter, CharChain operator, Value operand, LambdaVar map) {
			StreamVar t = s.duplicate();
			if (t.hasNext()) {
				Value v = t.next();
				Value m = map.produce(v);
				if (!m.getType().equals(v.getType()))
					throw new IllegalStateException("Cannot have a mapping function which signature does not match with original stream's elements");
				if (!operand.getType().equals(filter.produce(v).getType()))
					throw new IllegalStateException("Cannot fave a filtering function which produces elements of an other type than the given operand");
				if (v instanceof Number)
					return new NumberStream(s, filter, operator, operand, map);
				else if (v instanceof CharChain)
					return new StringStream(s, filter, operator, operand, map);
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
			array.put(filter.getJSONObject());
			array.put(new CharChain(operator.getCall()).getJSONObject());
			array.put(operand.getJSONObject());
			array.put(map.getJSONObject());
			return array;
		}
		
		@Override
		public String getJSONName() {
			return Mapwhere.getJSONName();
		}
		
		public static class NumberStream extends Stream implements NumberStreamVar {
			public NumberStream(StreamVar s, LambdaVar filter, CharChain operator, Value operand, LambdaVar map) {
				super(s, filter, operator, operand, map);
			}
		}
		public static class StringStream extends Stream implements StringStreamVar {
			public StringStream(StreamVar s, LambdaVar filter, CharChain operator, Value operand, LambdaVar map) {
				super(s, filter, operator, operand, map);
			}
		}
	}
}